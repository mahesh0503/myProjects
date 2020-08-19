package com.city.test.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Loads the file city.txt from classpath and populate collection object with all possible routes
 * if the file doesn't exist the system logs error message.
 * 
 * Place for improvement: 
 * As per current implementation, the application is reading the file from classpath(resource folder), 
 * it can be refactored to read file from external folder and populate collection object.
 * so that while changing the city.txt, application doesn't need to be deployed again.
 * 
 * As per current implementation if the file contains even single invalid entry the file will be 
 * discarded completely, but can be changed as per below suggestion
 *    1) log the invalid entries and ignore them and continue loading other routes 
 *    2) if the files contains all invalid entries  or if the files doesn't exist then probably log 
 *    	  the entries /error message and don't let the server to start
 *    
 * 
 */
@Configuration
public class CollectionConfig implements CommandLineRunner {
	
	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Bean
	public  HashMap<String, ArrayList<String>> getCityMap()
	{
		return cityMap ;
	}
	
	HashMap<String, ArrayList<String>> cityMap = new HashMap<String, ArrayList<String>>();
	
	@Override
	public void run(String... args) throws Exception {
		
			BufferedReader br = null;
			FileReader fr = null;
			try
			{
				Resource resource = new ClassPathResource("city.txt");

				File file = resource.getFile();
				
				if(file.exists())
				{
					br = new BufferedReader(fr = new FileReader(file));
					String temp = null;
					while((temp = br.readLine()) != null)
					{
						String [] tArr = temp.split(",");
						if(tArr.length < 2)
						{
							cityMap = new HashMap<String, ArrayList<String>>();
							LOGGER.error("city.txt file contains invalid data");
							break;
						}
						ArrayList<String> tempList = null;
						String first =   tArr[0] != null ? tArr[0].toLowerCase().trim():"";
						String sec =   tArr[1] != null ? tArr[1].toLowerCase().trim():"";
						if(!first.equals("") && !sec.equals(""))
						{
							if(cityMap.containsKey(first))
							{
								tempList = cityMap.get(first);
							}
							else
							{
								tempList  = new ArrayList<String>();
							}
							tempList.add(sec);
							cityMap.put(first, tempList);
							
							// Reverse route
							if(cityMap.containsKey(sec))
							{
								tempList = cityMap.get(sec);
							}
							else
							{
								tempList  = new ArrayList<String>();
							}
							tempList.add(first);
							cityMap.put(sec , tempList);
						}
					}
					fr.close();
					br.close();
				}
				else
				{
					LOGGER.error("City.txt doesn't exist in Resource folder. Create a file city.txt in resource folder ");
				}
			}
			catch(FileNotFoundException fe)
			{
				LOGGER.error("City.txt doesn't exist in Resource folder. Create a file city.txt in resource folder ");
			}
			catch(IOException fe)
			{
				LOGGER.error("Error reading file City.txt Create a file city.txt in resource folder ");
			}
			catch(Exception fe)
			{
				LOGGER.error("Error reading file City.txt Create a file city.txt in resource folder ");
			}
			finally
			{
				if(fr != null)
				{
					try
					{
						fr.close();
					}
					catch(Exception e) {}
				}
				
				if(br != null)
				{
					try
					{
						br.close();
					}
					catch(Exception e) {}
				}
			}
		}

}
