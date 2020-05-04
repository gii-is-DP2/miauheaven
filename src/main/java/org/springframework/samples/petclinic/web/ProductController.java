package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
	

	private static final String			PRODUCT_LIST		= "product/productList";
	private static final String			PRODUCT_SHOW		= "product/productShow";
	
	ProductService productService;
	
	
	public ProductController(final ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value = "/List")
	public String productList(final Map<String, Object> model) {
		Collection<Product> products = this.productService.findAll();
		model.put("products", products);
		return PRODUCT_LIST;
	}

	@GetMapping(value = "/{productId}")
	public String productShow(final Map<String, Object> model, @PathVariable("productId") final int productId) {
		Product product = this.productService.findProductById(productId);
		model.put("product", product);
		return PRODUCT_SHOW;
	}
}
