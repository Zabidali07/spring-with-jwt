package com.zabid.threadhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zabid.threadhouse.models.Product;
import com.zabid.threadhouse.services.ProductServices;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductServices ps;
	
	@PostMapping("/add")
	public Product addProduct(@RequestBody Product p) {
		return ps.addProduct(p);
	}
	
	@GetMapping("/list")
	public List<Product> getProducts(){
		return ps.getAllProducts();
	}
}
