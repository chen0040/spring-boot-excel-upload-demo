package com.github.chen0040.bootslingshot.controllers;


import com.github.chen0040.bootslingshot.services.ProductApi;
import com.github.chen0040.bootslingshot.viewmodels.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 9/11/2017.
 */
@Controller
public class ProductController {

   @Autowired
   private ProductApi productApi;

   @RequestMapping(value="/erp/get-products", method = RequestMethod.GET)
   public @ResponseBody List<Product> getProducts() {
      return productApi.findAll();
   }

   @RequestMapping(value="/erp/count-products", method = RequestMethod.GET)
   public @ResponseBody Map<String, Integer> countProducts() {
      Map<String, Integer> result = new HashMap<>();
      result.put("count", productApi.findAll().size());
      return result;
   }
}
