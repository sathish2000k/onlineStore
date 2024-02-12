package com.onlinestore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinestore.entity.Product;
import com.onlinestore.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
				
	}
	
	@Override
	public Product getProduct(long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			return product.get();
		} else {
			return null;
		}
	}
	
	@Override
	public Product updateProduct(long productId, Product product) {
		Product updateProduct = getProduct(productId);
	    if (updateProduct == null) {
	        return null;
	    }
	    
	    updateProduct.setName(product.getName());
	    updateProduct.setDescription(product.getDescription());
	    updateProduct.setPrice(product.getPrice());
	    updateProduct.setQuantityAvailable(product.getQuantityAvailable());

	    return productRepository.save(updateProduct);
	}
	
	@Override
	public Product deleteProduct(long productId) {
		Product product = getProduct(productId);
		productRepository.deleteById(productId);
		return product;
	}
	
	@Override
	public boolean applyDiscount(long productId, double discountPercentage) {
        Product product = getProduct(productId);
        if (product != null) {
            double discountedPrice = product.getPrice() * (1 - discountPercentage / 100);
            product.setPrice(discountedPrice);
            productRepository.save(product);
            return true;
        }
        return false;
    }

	@Override
    public boolean applyTax(long productId, double taxRate) {
        Product product = getProduct(productId);
        if (product != null) {
            double newPrice = product.getPrice() * (1 + taxRate / 100);
            product.setPrice(newPrice);
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
