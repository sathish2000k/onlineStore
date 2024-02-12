package com.onlinestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinestore.entity.Product;
import com.onlinestore.service.ProductServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@PostMapping("/")
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		Product createdProduct = productServiceImpl.createProduct(product);
		if (createdProduct != null) {
			return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Failed to create product", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Object> getProduct(@PathVariable long productId) {
		Product product = productServiceImpl.getProduct(productId);
		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable long productId, @RequestBody Product product) {
		Product updatedProduct = productServiceImpl.updateProduct(productId, product);
		if (updatedProduct != null) {
			return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Failed to update product", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable long productId) {
		Product deletedProduct =  productServiceImpl.deleteProduct(productId);
		if (deletedProduct != null) {
			return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Failed to delete product", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{productId}/apply-discount")
    public ResponseEntity<Object> applyDiscount(@PathVariable long productId, @RequestParam double discountPercentage) {
        if (productServiceImpl.applyDiscount(productId, discountPercentage)) {
            return new ResponseEntity<>("Discount applied successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to apply discount", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{productId}/apply-tax")
    public ResponseEntity<Object> applyTax(@PathVariable long productId, @RequestParam double taxRate) {
        if (productServiceImpl.applyTax(productId, taxRate)) {
            return new ResponseEntity<>("Tax applied successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to apply tax", HttpStatus.BAD_REQUEST);
        }
    }

}
