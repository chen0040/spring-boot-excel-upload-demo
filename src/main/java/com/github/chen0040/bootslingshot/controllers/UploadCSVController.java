package com.github.chen0040.bootslingshot.controllers;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.bootslingshot.services.ProductApi;
import com.github.chen0040.bootslingshot.viewmodels.Product;
import com.github.chen0040.bootslingshot.viewmodels.UploadEvent;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;


/**
 * Created by xschen on 8/11/2017.
 */
@Controller
public class UploadCsvController {

   private ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

   private static final Logger logger = LoggerFactory.getLogger(UploadCsvController.class);

   @Autowired
   private ProductApi productApi;

   @Autowired
   private SimpMessagingTemplate brokerMessagingTemplate;

   private String getVendor(String token) {
      return "vendor";
   }

   @RequestMapping(value = "/erp/upload-csv", method = RequestMethod.POST)
   public @ResponseBody Map<String, Object> uploadProductCsv(
           @RequestParam("file") MultipartFile file,
           @RequestParam("token") String token)
           throws ServletException, IOException {
      logger.info("upload-csv invoked.");

      Map<String, Object> result = new HashMap<>();

      final String label = UUID.randomUUID().toString() + ".csv";
      final String filepath = "/tmp/" + label;
      byte[] bytes = file.getBytes();
      File fh = new File("/tmp/");
      if(!fh.exists()){
         fh.mkdir();
      }



      try {

         FileOutputStream writer = new FileOutputStream(filepath);
         writer.write(bytes);
         writer.close();

         logger.info("image bytes received: {}", bytes.length);



         executor.submit(() -> {
            try {
               UploadEvent event = new UploadEvent();
               event.setState("Uploaded filed received on server");
               event.setEventType("start");
               brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(event, SerializerFeature.BrowserCompatible));

               BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
               String line;
               boolean firstLine = true;
               while((line = reader.readLine()) != null) {
                  if(firstLine) {
                     firstLine = false;
                     continue;
                  }
                  String[] parts = line.split(",");
                  String name = parts[0];
                  String price = parts[1];
                  String weight = parts[2];
                  String width = parts[3];
                  String height = parts[4];
                  String description = parts[5];

                  Product product = new Product();
                  product.setName(name);
                  product.setSku(UUID.randomUUID().toString());
                  product.setPrice(Double.parseDouble(price));
                  product.setWeight(Double.parseDouble(weight));
                  product.getAttributes().put("width", width);
                  product.getAttributes().put("height", height);
                  product.getAttributes().put("description", description);
                  product.setVendor(getVendor(token));

                  logger.info("Saving product: {}", product.getName());
                  productApi.saveProduct(product);

                  event = new UploadEvent();
                  event.setState(product);
                  event.setEventType("progress");
                  brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(event, SerializerFeature.BrowserCompatible));

                  Thread.sleep(5000L);

               }

               event = new UploadEvent();
               event.setState("Uploaded filed deleted on server");
               fh.delete();
               event.setEventType("end");
               brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(event, SerializerFeature.BrowserCompatible));

            }catch(Exception ex) {
               logger.error("Failed on saving product", ex);
            }
         });

         result.put("success", true);
         result.put("id", label);
         result.put("error", "");

         return result;
      }catch(IOException ex) {
         logger.error("Failed to process the uploaded image", ex);
         result.put("success", false);
         result.put("id", "");
         result.put("error", ex.getMessage());
         return result;
      }

   }
}
