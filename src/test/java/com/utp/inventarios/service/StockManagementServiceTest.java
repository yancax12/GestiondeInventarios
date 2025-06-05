package com.utp.inventarios.service;

import com.utp.inventarios.model.Product;
import com.utp.inventarios.repository.ProductRepository;
import com.utp.inventarios.exception.OverStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockManagementServiceTest {

    @Mock
    private ProductRepository productRepository;  // Repositorio simulado (mock)

    @InjectMocks
    private StockManagementService stockManagementService;  // El servicio que estamos probando

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        product = new Product("Smartphone", 500, 50, 1000.00);  // Datos de prueba para un producto
    }

    @Test
    public void testGetLowStockProducts() {
        // Simula la búsqueda de productos con stock bajo
        when(productRepository.findByStockLessThan(10)).thenReturn(List.of(product));

        List<Product> result = stockManagementService.getLowStockProducts();

        // Verifica que el resultado no sea nulo
        assertNotNull(result);

        // Verifica que el tamaño de la lista sea el esperado
        assertEquals(1, result.size());

        // Verifica que el nombre del producto sea el esperado
        assertEquals("Smartphone", result.get(0).getName());
    }

    @Test
    public void testManageOverstock() {
        // Simula la búsqueda del producto con un stock superior al límite
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Verifica que se lance una excepción si el stock es mayor a 1000
        assertThrows(OverStockException.class, () -> stockManagementService.manageOverstock(1L));
    }

    @Test
    public void testManageOverstock_NoException() {
        product.setStock(900);  // Cambia el stock para que no se lance la excepción

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Verifica que no se lance una excepción cuando el stock no es excesivo
        assertDoesNotThrow(() -> stockManagementService.manageOverstock(1L));
    }

    @Test
    public void testCheckStockLevels() {
        // Simula productos con stock bajo
        when(productRepository.findByStockLessThan(10)).thenReturn(List.of(product));

        // Ejecuta la verificación de niveles de stock
        stockManagementService.checkStockLevels();

        // Verifica que el método findByStockLessThan haya sido llamado
        verify(productRepository, times(1)).findByStockLessThan(10);
    }
}
