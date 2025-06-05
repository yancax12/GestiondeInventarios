package com.utp.inventarios.repository;

import com.utp.inventarios.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Encuentra productos cuyo stock es menor que el valor dado
    List<Product> findByStockLessThan(int stock);  // Productos con stock bajo

    // Encuentra productos cuyo stock es mayor que el valor dado
    List<Product> findByStockGreaterThan(int stock);  // Productos con exceso de stock

    // Si tienes un campo para el stock mínimo y deseas encontrar productos con stock bajo respecto a ese umbral
    List<Product> findByStockLessThanAndStockMinimo(int stock, int stockMinimo); // Para encontrar productos cuyo stock es menor que un valor específico y también comparar con el mínimo definido.
}
