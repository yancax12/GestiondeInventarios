package com.utp.inventarios.service;

import com.utp.inventarios.model.Product;
import com.utp.inventarios.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;  // Repositorio simulado (mock)

    @InjectMocks
    private ProductService productService;  // El servicio que estamos probando

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        product = new Product("Laptop", 50, 10, 1500.00);  // Datos de prueba para un producto
    }

    @Test
    public void testAddProduct() {
        // Simula el comportamiento del repositorio para cuando se llame a save(product)
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Llama al método addProduct
        Product result = productService.addProduct(product);

        // Verifica que el resultado no sea nulo
        assertNotNull(result);

        // Verifica que el nombre del producto sea correcto
        assertEquals("Laptop", result.getName());

        // Verifica que el método save fue llamado una vez
        verify(productRepository, times(1)).save(product);
    }

    // Otros tests...
}
