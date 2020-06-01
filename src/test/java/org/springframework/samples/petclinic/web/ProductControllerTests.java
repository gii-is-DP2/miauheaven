
package org.springframework.samples.petclinic.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProductController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ProductControllerTests {

	private static final int	TEST_PRODUCT_ID	= 1;

	@MockBean
	private ProductService		productService;

	@Autowired
	private MockMvc				mockMvc;

	private Product				product;


	@BeforeEach
	void setup() {
		this.product = new Product();
		this.product.setId(ProductControllerTests.TEST_PRODUCT_ID);
		this.product.setName("Prueba");
		this.product.setDescription("Prueba");
		this.product.setImage("https://www.petpremium.com/wp-content/uploads/2012/10/5-healthy-dog-foods-430x226.jpg");
		this.product.setPrice(20.0);
		this.product.setStock(true);

		BDDMockito.given(this.productService.findProductById(ProductControllerTests.TEST_PRODUCT_ID)).willReturn(this.product);

	}

	@WithMockUser(value = "spring")
	@Test
	void testProductList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/product/List")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("products"))
			.andExpect(MockMvcResultMatchers.view().name("product/productList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProductShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}", ProductControllerTests.TEST_PRODUCT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("product"))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("id", Matchers.is(ProductControllerTests.TEST_PRODUCT_ID))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("name", Matchers.is("Prueba")))).andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("description", Matchers.is("Prueba"))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("stock", Matchers.is(true)))).andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("price", Matchers.is(20.0))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("image", Matchers.is("https://www.petpremium.com/wp-content/uploads/2012/10/5-healthy-dog-foods-430x226.jpg"))))
			.andExpect(MockMvcResultMatchers.view().name("product/productShow"));
	}
}
