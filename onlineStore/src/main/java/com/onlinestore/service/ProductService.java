package com.onlinestore.service;

import com.onlinestore.entity.Product;

public interface ProductService {

	Product createProduct(Product product);
	Product getProduct(long productId);
	Product updateProduct(long productId, Product product);
	Product deleteProduct(long productId);
	boolean applyDiscount(long productId, double discountPercentage);
	boolean applyTax(long productId, double taxRate);
	
}
