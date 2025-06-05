package com.utp.inventarios.controller;

import com.utp.inventarios.model.Product;
import com.utp.inventarios.service.StockManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")  // Ruta base para los endpoints de stock
public class StockController {

    @Autowired
    private StockManagementService stockManagementService;  // Servicio que maneja la lógica del stock

    // Obtener productos con stock bajo
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return stockManagementService.getLowStockProducts();  // Llama al servicio para obtener productos con stock bajo
    }

    // Forzar una comprobación de stock
    @GetMapping("/check-stock")
    public ResponseEntity<Void> checkStockLevels() {
        stockManagementService.checkStockLevels();  // Llama al servicio para comprobar los niveles de stock
        return ResponseEntity.ok().build();  // Retorna un 200 OK
    }

    // Optimización del inventario (gestión de exceso de stock)
    @GetMapping("/manage-overstock/{productId}")
    public ResponseEntity<Void> manageOverstock(@PathVariable Long productId) {
        try {
            stockManagementService.manageOverstock(productId);  // Llama al servicio para gestionar exceso de stock
            return ResponseEntity.ok().build();  // Retorna un 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);  // Retorna un 400 si hay un error
        }
    }
}
