package com.city.test.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * City controller which checks if there is a bus route between the origin and destination.
 * and if the route exists returns yes else no
 * 
 */
@RestController
public class CityController {

	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	public  HashMap<String, ArrayList<String>> getCityMap;
	private HashMap<String, Boolean> isVisited  = null;
	
	@GetMapping("/connected")
	public String  isConnected(@RequestParam(required = true) String origin, @RequestParam(required = true) String destination)
	{
		if(getCityMap == null || getCityMap.size() == 0)
				return "City.txt file either doesnot exist or file is not valid";
		
		if(origin == null || destination == null || origin.trim().equals("") || destination.trim().equals("") )
			return "No";
	
		isVisited = new HashMap<String, Boolean>();
		
		boolean pathExists =  printAllPathsUtil(origin.trim().toLowerCase(), destination.trim().toLowerCase(),isVisited);
		return pathExists ? "Yes" : "No";
		
	}

	private boolean printAllPathsUtil(String source, String dest,
			HashMap<String, Boolean> isVisited) {

		isVisited.put(source, true);
		
		if (source.equals(dest))
		{
			return true;
		}
		if(getCityMap.get(source) != null)
		{
			for (String loc : getCityMap.get(source))
			{
				if (isVisited.get(loc) == null)
				{
					boolean isPathExist = printAllPathsUtil(loc, dest, isVisited);
					if (isPathExist)
					{
						return isPathExist;
					}
				}
			}
		}
		return false;
	}
}
