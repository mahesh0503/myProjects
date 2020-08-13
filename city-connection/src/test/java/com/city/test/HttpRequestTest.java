package com.city.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void routeCheckforSuccess() throws Exception {
		
		String origin = "Boston";
		String destination = "Newark";

		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/connected?origin="+origin+"&destination="+destination, String.class).equals("Yes"));
	}
	
	@Test
	public void routeCheckforFailure() throws Exception {
		
		String origin = "Albany";
		String destination = "Newark";
		
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/connected?origin="+origin+"&destination="+destination, String.class).equals("No"));
	}
}
