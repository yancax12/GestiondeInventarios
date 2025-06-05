package com.utp.inventarios.controller;

import com.utp.inventarios.model.Product;
import com.utp.inventarios.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")  // Ruta base para los productos
public class ProductController {

    @Autowired
    private ProductService productService;  // Servicio que maneja la lógica de productos

    // Ruta para la página de inicio (index.html)
    @GetMapping("/")
    public String index() {
        return "index";  // Retorna la vista index.html
    }

    // Ruta para mostrar el inventario de productos (inventory.html)
    @GetMapping("/list")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();  // Obtiene todos los productos
        model.addAttribute("products", products);  // Pasa los productos al modelo
        return "inventory";  // Retorna la vista inventory.html
    }

    // Ruta para ver los detalles de un producto específico (viewProduct.html)
    @GetMapping("/view/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);  // Obtiene el producto por ID
        if (product == null) {
            return "error";  // Si el producto no existe, muestra la vista error.html
        }
        model.addAttribute("product", product);  // Pasa el producto al modelo
        return "viewProduct";  // Retorna la vista viewProduct.html
    }

    // Ruta para mostrar el formulario de agregar un nuevo producto (addProduct.html)
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());  // Pasa un objeto vacío de producto al modelo
        return "addProduct";  // Retorna la vista addProduct.html
    }

    // Ruta para agregar un nuevo producto (POST)
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, Model model) {
        productService.addProduct(product);  // Llama al servicio para agregar el producto
        model.addAttribute("message", "Producto agregado exitosamente");
        return "redirect:/product/list";  // Redirige a la lista de productos
    }

    // Ruta para mostrar el formulario de edición de un producto (editProduct.html)
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);  // Obtiene el producto por ID
        if (product == null) {
            return "error";  // Si el producto no existe, muestra la vista error.html
        }
        model.addAttribute("product", product);  // Pasa el producto al modelo
        return "editProduct";  // Retorna la vista editProduct.html
    }

    // Ruta para editar un producto (POST)
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute Product product, Model model) {
        product.setId(id);  // Establece el ID del producto
        productService.addProduct(product);  // Llama al servicio para actualizar el producto
        model.addAttribute("message", "Producto actualizado exitosamente");
        return "redirect:/product/list";  // Redirige a la lista de productos
    }

    // Ruta para actualizar el stock de un producto (PUT)
    @PutMapping("/{id}/updateStock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam int quantity) {
        Product product = productService.getProductById(id);  // Obtiene el producto por ID
        if (product == null) {
            return ResponseEntity.notFound().build();  // Retorna 404 si no se encuentra el producto
        }

        productService.updateStock(id, quantity);  // Llama al servicio para actualizar el stock
        return ResponseEntity.ok().build();  // Retorna 200 OK si todo va bien
    }

    // Ruta para eliminar un producto (POST con simulación de DELETE)
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);  // Obtiene el producto por ID
        if (product != null) {
            productService.deleteProduct(id);  // Llama al servicio para eliminar el producto
        }
        return "redirect:/product/list";  // Redirige a la lista de productos después de la eliminación
    }

    // Ruta para mostrar la alerta de productos con stock bajo
    @GetMapping("/low-stock")
    public String showLowStockAlert(Model model) {
        List<Product> lowStockProducts = productService.getLowStockProducts(10);  // 10 es el umbral de stock bajo
        model.addAttribute("lowStockProducts", lowStockProducts);  // Pasa los productos con stock bajo al modelo
        return "lowStockAlert";  // Retorna la vista lowStockAlert.html
    }

    // Ruta para mostrar la alerta de productos con exceso de stock
    @GetMapping("/over-stock")
    public String showOverStockAlert(Model model) {
        List<Product> overStockProducts = productService.getOverStockProducts(100);  // 100 es el umbral de exceso de stock
        model.addAttribute("overStockProducts", overStockProducts);  // Pasa los productos con exceso de stock al modelo
        return "overstockAlert";  // Retorna la vista overstockAlert.html
    }




}
