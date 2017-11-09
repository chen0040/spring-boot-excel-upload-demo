package com.github.chen0040.bootslingshot.services;


import com.github.chen0040.bootslingshot.viewmodels.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 9/11/2017.
 */
@Service
public class ProductApiImpl implements ProductApi {
   private List<Product> products = new ArrayList<>();

   @Override public Product saveProduct(Product newProduct) {
      products.add(newProduct);
      return newProduct;
   }


   @Override public List<Product> findAll() {
      return products;
   }
}
