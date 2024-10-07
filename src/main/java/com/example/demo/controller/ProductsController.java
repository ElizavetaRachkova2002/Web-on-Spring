package com.example.demo.controller;

import com.example.demo.controller.payload.NewProductPayload;
import com.example.demo.entity.Product;
import com.example.demo.service.InterfaceProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

    private final InterfaceProductService productService;

    @GetMapping("/list")
    public String GetProductsList(Model model) {
        model.addAttribute("products", this.productService.findAllProducts());
        return "catalogue/products/list";
    }

    @GetMapping("/create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("/create")
    public String createProduct(NewProductPayload newProductPayload) {
        Product product = this.productService.createProduct(newProductPayload.title(),
                newProductPayload.details());
        return "redirect:/catalogue/products/list";
    }

    @GetMapping("{Id:\\d+}") //переменная пути
    public String getProduct(@PathVariable("Id") int Id, Model model) {
        Optional<Product> optionalProduct = this.productService.findProduct(Id);
        Product product = optionalProduct.get(); // Получаем продукт из Optional
            model.addAttribute("product", product);
            return "catalogue/products/product";
        }



   @GetMapping("/{Id}/edit")
   public String getProductEditPage(@PathVariable("Id") int id, Model model){
      Optional<Product> optionalProduct = this.productService.findProduct(id);
       if (optionalProduct.isPresent()) {
           model.addAttribute("product", optionalProduct.get()); // Получаем объект Product из Optional
       } else {
           // Обработка случая, когда продукт не найден
           return "redirect:/catalogue/products/list"; // или другая страница с ошибкой
       }
       return "catalogue/products/edit";
   }
   @PostMapping("/{Id}/edit")
   public String updateProduct(@ModelAttribute("product") Product updateProduct){
        this.productService.updateProduct(updateProduct.getId(),
                updateProduct.getTitle(),
                updateProduct.getDetails());
        return "redirect:/catalogue/products/list";
   }



   @PostMapping("/{Id}/delete")
    public String deleteProduct (@ModelAttribute("product") Product deleteProduct)
   {
  this.productService.deleteProduct(deleteProduct.getId());
       return "redirect:/catalogue/products/list";
   }

   @ExceptionHandler(NoSuchElementException.class)
    public String NoSuchElementException(NoSuchElementException exception, Model model, HttpServletResponse response){
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", exception.getMessage());
        return "catalogue/products/errors/404";
   }

}