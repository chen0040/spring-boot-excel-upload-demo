package com.github.chen0040.bootslingshot.viewmodels;


import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by xschen on 24/6/2017.
 */
@Getter
@Setter
public class Product {
   private static Random random = new Random();

   private String sku;

   private String name;

   private double price = 10;
   private int status  = 0;
   private int visibility = 0;


   private Date createdAt;

   private Date updatedAt; // format 2017-05-03 03:46:13

   private double weight;

   private String type = "";

   private long position = 0;

   private List<String> links = new ArrayList<>();

   private List<Double> prices = new ArrayList<>();

   private String vendor;
   private Map<String, String> attributes = new HashMap<>();

   private String error;

   private double rating;

   private String tags = "";

   private String token;

   public Product() {

   }

   public Product(String vendor, String productName, String sku, double price, double weight, Map<String, String> properties, List<Double> pricings, String tags) {
      this.name = productName;
      this.vendor = vendor;
      this.sku = sku;
      this.price = price;
      this.weight = weight;
      this.attributes = properties;
      this.prices = pricings;
      this.rating = random.nextInt(3) + 2;
      this.tags = tags;
   }

   public static Product createAlert(String errorMessage) {
      Product product = new Product();
      return product.alert(errorMessage);
   }

   public Product alert(String errorMessage) {
      error = errorMessage;
      return this;
   }
}
