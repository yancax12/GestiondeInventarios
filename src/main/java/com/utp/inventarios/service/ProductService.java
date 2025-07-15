package com.utp.inventarios.service;

import com.utp.inventarios.model.Product;
import com.utp.inventarios.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    // Método para agregar un producto
    public Product addProduct(Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            // Log de éxito al agregar el producto
            logger.info("Producto agregado exitosamente: " + savedProduct.getName());
            return savedProduct;
        } catch (Exception e) {
            // Log de error si ocurre un problema al agregar el producto
            logger.error("Error al agregar producto: " + e.getMessage());
            throw new RuntimeException("Error al agregar el producto", e);
        }
    }

    // Método para obtener un producto por su ID
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            // Log de éxito al obtener el producto
            logger.info("Producto encontrado: " + product.get().getName());
            return product.get();
        } else {
            // Log si el producto no se encuentra
            logger.warn("Producto con ID " + id + " no encontrado.");
            return null;
        }
    }

    // Método para actualizar el stock de un producto
    public void updateStock(Long id, int quantity) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
            // Log de éxito al actualizar el stock
            logger.info("Stock del producto " + product.getName() + " actualizado. Nuevo stock: " + product.getStock());
        } else {
            // Log de error si el producto no existe
            logger.error("Producto con ID " + id + " no encontrado para actualizar el stock.");
        }
    }

    // Método para eliminar un producto por su ID
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            // Log de éxito al eliminar el producto
            logger.info("Producto con ID " + id + " eliminado exitosamente.");
        } catch (Exception e) {
            // Log de error si ocurre un problema al eliminar el producto
            logger.error("Error al eliminar producto con ID " + id + ": " + e.getMessage());
        }
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
