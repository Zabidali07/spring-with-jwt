package com.zabid.threadhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zabid.threadhouse.models.Product;
import com.zabid.threadhouse.repositories.ProductRepository;



@Service
public class ProductServices {

	@Autowired
	private ProductRepository pr;
	
	public Product addProduct(Product p) {
		Product pNew = new Product();
		
		pNew.setProductName(p.getProductName());
		pNew.setProductCode(p.getProductCode());
		pr.saveAndFlush(pNew);
		return pNew;
	}
	
	public List<Product> getAllProducts(){
		return pr.findAll();
	}
}
