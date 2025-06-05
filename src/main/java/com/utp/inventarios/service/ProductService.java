package com.utp.inventarios.service;

import com.utp.inventarios.model.Product;
import com.utp.inventarios.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;  // Si decides usar Optional

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Método para agregar un producto
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Método para obtener un producto por su ID
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);  // Si el producto no existe, devuelve null
    }

    // Método para actualizar el stock de un producto
    public void updateStock(Long id, int quantity) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
        }
    }

    // Método para eliminar un producto por su ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Método para obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Método para obtener productos con stock bajo
    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findByStockLessThan(threshold);
    }

    // Método para obtener productos con exceso de stock
    public List<Product> getOverStockProducts(int threshold) {
        return productRepository.findByStockGreaterThan(threshold);
    }
}
