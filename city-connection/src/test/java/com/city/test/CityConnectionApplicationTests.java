package com.city.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.city.test.controller.CityController;

@SpringBootTest
class CityConnectionApplicationTests {

	@Autowired
	CityController cityController;
	
	@Test
	void contextLoads() {
		assertThat(cityController).isNotNull();
	}
	
	@Test
	void validPathTest()
	{
		String origin = "Boston";
		String destination = "Newark";
		if(cityController.getCityMap == null || cityController.getCityMap.size() == 0)
		{
			String error = this.cityController.isConnected(origin, destination);
			System.out.println("Error Message :"+error);
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
			System.out.println("Error Message :"+error);
			assertThat(error).isEqualTo("City.txt file either doesnot exist or file is not valid");			
		}
		else
		{
			assertThat(this.cityController.isConnected(origin, destination)).isEqualTo("No");
		}
	}
}
