
package org.springframework.samples.petclinic.e2e;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext
//@TestPropertySource(locations = "classpath:application-mysql.properties")
public class ProductControllerE2ETest {

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_PRODUCT_ID	= 1;


	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProductList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/product/List")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("products"))
			.andExpect(MockMvcResultMatchers.view().name("product/productList"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})

	@Test
	void testProductShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}", ProductControllerE2ETest.TEST_PRODUCT_ID)).andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("name", Matchers.is("Comida para perros"))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("description", Matchers.is("La mejor comida para alimentar a nuestros compañeros caninos"))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("price", Matchers.is(12.2)))).andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("stock", Matchers.is(true))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("image", Matchers.is("https://www.petpremium.com/wp-content/uploads/2012/10/5-healthy-dog-foods-430x226.jpg"))))
			.andExpect(MockMvcResultMatchers.view().name("product/productShow"));
	}

}
