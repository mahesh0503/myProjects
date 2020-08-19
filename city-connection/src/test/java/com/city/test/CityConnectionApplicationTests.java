package com.city.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.city.test.controller.CityController;

@SpringBootTest
class CityConnectionApplicationTests {

	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	CityController cityController;
	
	@Test
	void contextLoads() {
		assertThat(cityController).isNotNull();
	}
	
	@Test
	void loadFileTest() {
		if(cityController.getCityMap == null || cityController.getCityMap.size() == 0)
		{
			LOGGER.error("Error Message : City.txt file either doesnot exist or file is not valid");
			
		}
	}
	
	
	@Test
	void validPathTest()
	{
		String origin = "Boston";
		String destination = "Newark";
		if(cityController.getCityMap == null || cityController.getCityMap.size() == 0)
		{
			String error = this.cityController.isConnected(origin, destination);
			LOGGER.error("Error Message :"+error);
			assertThat(error).isEqualTo("City.txt file either doesnot exist or file is not valid");			
		}
		else
		{
			assertThat(this.cityController.isConnected(origin, destination)).isEqualTo("Yes");
		}		
	}
	
	@Test
	void inValidPathTest()
	{
		String origin = "Newark";
		String destination = "Albany";
		if(cityController.getCityMap == null || cityController.getCityMap.size() == 0)
		{
			String error = this.cityController.isConnected(origin, destination);
			LOGGER.error("Error Message :"+error);
			assertThat(error).isEqualTo("City.txt file either doesnot exist or file is not valid");			
		}
		else
		{
			assertThat(this.cityController.isConnected(origin, destination)).isEqualTo("No");
		}
	}
}
