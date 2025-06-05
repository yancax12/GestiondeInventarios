package com.utp.inventarios.service;

import com.utp.inventarios.exception.NotEnoughStockException;
import com.utp.inventarios.exception.OverStockException;
import com.utp.inventarios.model.Product;
import com.utp.inventarios.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockManagementService {

    @Autowired
    private ProductRepository productRepository;

    // Método para obtener productos con stock bajo
    public List<Product> getLowStockProducts() {
        return productRepository.findByStockLessThan(5);  // Devuelve productos con stock menor a 5
    }

    // Método programado para verificar niveles de stock (cada 1 minuto, por ejemplo)
    @Scheduled(fixedRate = 60000)  // Se ejecuta cada 60,000 ms (1 minuto)
    public void checkStockLevels() {
        List<Product> lowStockProducts = getLowStockProducts();
        for (Product product : lowStockProducts) {
            // Si el producto no tiene el método getName(), causará el error.
            System.out.println("ALERTA: El producto " + product.getName() + " tiene bajo stock.");
        }
    }

    // Método para gestionar exceso de stock
    public void manageOverstock(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && product.getStock() > 1000) {
            throw new OverStockException("El producto " + product.getName() + " tiene exceso de stock.");
        }
    }
}
