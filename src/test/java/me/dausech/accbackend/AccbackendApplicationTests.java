package me.dausech.accbackend;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccbackendApplicationTests {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private AuthenticationManager authenticationManager;

//	@Autowired
//	private Filter springSecurityFilterChain;

	@Autowired
	private JpaUserDetailsService jpaUserDetailsService;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
			//	.addFilters(springSecurityFilterChain)
				.build();
	}

	@Test
	public void contextLoads() {
	}

	@Test	
	public void shouldRedirectWithAnonymousUser() throws Exception {
		mvc.perform(get("/hello")).andExpect(status().is3xxRedirection());
	}
	
//	@Test	
//	public void shouldGetUnauthorizedWithInvalidUser() throws Exception {
//		mvc.perform(get("/hello").with(anonymous())).andExpect(status().isForbidden());
//	}

	@Test	
	public void shouldGetOKWithValidCredentials() throws Exception {
		mvc.perform(get("/hello").with(user("douglas").password("secret"))).andExpect(status().isOk());
	}
	
	@Test
	public void successfulAuthentication() throws Exception {
		mvc.perform(formLogin("/login").user("douglas").password("secret"))
				.andExpect(authenticated());
	}

}
