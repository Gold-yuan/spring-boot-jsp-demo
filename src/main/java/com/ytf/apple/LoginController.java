package com.ytf.apple;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ytf.apple.entity.City;
import com.ytf.apple.respositroy.CityRepository;

@Controller
public class LoginController {

	@Autowired
	CityRepository cityRepository;

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/citys")
	public String testWebGL(Map<String, Object> model) {

		List<City> citys = cityRepository.findAll();
		City city = cityRepository.findCityById(1L);
		System.out.println(city.getName());
		
		model.put("citys", citys);
		
		int count = cityRepository.getCountForName("上海");
		System.out.println(count);
		return "citys";
	}
}