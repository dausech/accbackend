package me.dausech.accbackend;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.dausech.accbackend.security.AccountCredentials;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={AccbackendApplication.class})
public class AccbackendApplicationTests {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private AuthenticationManager authenticationManager;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldAuthenticate() throws Exception {
		AccountCredentials credentials = new AccountCredentials("douglas", "secret");

		mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(credentials))).andExpect(status().is2xxSuccessful());
	}

	@Test	
	public void shouldGetUnauthorizedWithAnonymousUser() throws Exception {
		mvc.perform(get("/")).andExpect(status().is4xxClientError());
	}
	

    @Test    
    public void getAccountsSuccessfullyWithUserRole() throws Exception{
    	    	
    	String token = obtainAccessToken("douglas","secret");    	
        this.mvc.perform(get("/accounts").
        		contentType(MediaType.APPLICATION_JSON)
        		.header("Authorization", token))				
                .andExpect(status().isOk());
    }
    
    @Test    
    public void shouldFailWithIncorrectRole() throws Exception{
    	    	
    	String token = obtainAccessToken("user","secret");    	
        this.mvc.perform(get("/accounts").
        		contentType(MediaType.APPLICATION_JSON)
        		.header("Authorization", token))				
                .andExpect(status().isForbidden());
    }
    
    private String obtainAccessToken(String user, String passwd) throws Exception {
  	  
    	AccountCredentials credentials = new AccountCredentials(user,passwd);
     
        ResultActions result 
          = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
  				.content(new ObjectMapper().writeValueAsString(credentials)));
     
        String token = result.andReturn().getResponse().getHeader("Authorization");      
        return token; 
    }

	// @Test
	// public void shouldRedirectWithAnonymousUser() throws Exception {
	// mvc.perform(get("/hello")).andExpect(status().is4xxClientError());
	// }

	// @Test
	// public void shouldGetUnauthorizedWithInvalidUser() throws Exception {
	// mvc.perform(get("/hello").with(anonymous())).andExpect(status().isForbidden());
	// }
	//
	// @Test
	// public void shouldGetOKWithValidCredentials() throws Exception {
	// mvc.perform(get("/hello").with(user("douglas").password("secret"))).andExpect(status().isOk());
	// }
	//
	// @Test
	// public void successfulAuthentication() throws Exception {
	// mvc.perform(formLogin("/login").user("douglas").password("secret"))
	// .andExpect(authenticated());
	// }

   
}

