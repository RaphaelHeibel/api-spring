package com.crud.controllers;

import com.crud.dtos.ProductDto;
import com.crud.model.Product;
import com.crud.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.util.BeanUtil;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/list")
    public ResponseEntity listProducts() {
        List<Product> productList = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id) {
        Optional product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(product.get());
    }

    @PostMapping("/{id}")
    public ResponseEntity addProduct(@RequestBody ProductDto dto) {
        var product = new Product();
        BeanUtils.copyProperties(dto, product);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody ProductDto dto) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        BeanUtils.copyProperties(dto, product.get());
        productRepository.save(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productRepository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }
}
