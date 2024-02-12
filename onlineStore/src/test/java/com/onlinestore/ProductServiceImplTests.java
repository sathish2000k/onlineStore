package com.onlinestore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.onlinestore.entity.Product;
import com.onlinestore.repository.ProductRepository;
import com.onlinestore.service.ProductServiceImpl;

public class ProductServiceImplTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImplTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(1L, "Test Product", "Test Description", 100.0, 50);

        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productServiceImplTest.createProduct(product);

        assertEquals("Test Product", createdProduct.getName());
        assertEquals("Test Description", createdProduct.getDescription());
        assertEquals(100.0, createdProduct.getPrice(), 0.001);
        assertEquals(50, createdProduct.getQuantityAvailable());
    }
    
    @Test
    public void testGetProduct() {
    	Product product = new Product(1L, "Test Product", "Test Description", 100.0, 50);

        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        Product retrievedProduct = productServiceImplTest.getProduct(product.getProductId());
        
        assertThat(retrievedProduct).isNotNull();
        assertEquals("Test Product", retrievedProduct.getName());
        assertEquals("Test Description", retrievedProduct.getDescription());
        assertEquals(100.0, retrievedProduct.getPrice(), 0.001);
        assertEquals(50, retrievedProduct.getQuantityAvailable());
        
    }
    
    @Test
    public void testUpdateProduct() {
    	Product productToUpdate = new Product(1L, "Test Product", "Test Description", 100.0, 50);	
    	Product updateProduct = new Product(1L, "Updated Product", "Updated Description", 150.0, 75);

    	when(productRepository.save(productToUpdate)).thenReturn(productToUpdate);
    	
    	productToUpdate = productServiceImplTest.createProduct(productToUpdate);
    	Product updatedProduct = productServiceImplTest.updateProduct(1L, updateProduct);
    	
        
    	assertNotNull(updatedProduct);
    }
    
    @Test
    public void testDeleteProduct() {
    	long productId = 1L;
        Product productToDelete = new Product();
        productToDelete.setProductId(productId);
        productToDelete.setName("Product to Delete");
        productToDelete.setDescription("Description to Delete");
        productToDelete.setPrice(200.0);
        productToDelete.setQuantityAvailable(100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productToDelete));

        Product deletedProduct = productServiceImplTest.deleteProduct(productId);

        assertNotNull(deletedProduct);
        assertEquals(productId, deletedProduct.getProductId());
        assertEquals("Product to Delete", deletedProduct.getName());
        assertEquals("Description to Delete", deletedProduct.getDescription());
        assertEquals(200.0, deletedProduct.getPrice());
        assertEquals(100, deletedProduct.getQuantityAvailable());

        verify(productRepository, times(1)).deleteById(productId);
    }
}
