package com.ytf.apple;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

	@ResponseBody
	@RequestMapping(value = "/getOne")
	public String getOne(String num) {
		if (num == null || num.length() == 0)
			return "ONE";
		return num;
	}

	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload(String username, MultipartFile files) {
		System.out.println("username:" + username);
		System.out.println("files:" + files.getOriginalFilename());
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/download")
	public byte[] download(String name) {
		File file = new File("C:\\Users\\IBM_ADMIN\\Desktop\\temp\\old.java");
		try {
			byte[] by = new byte[(int) file.length()];
			try {
				InputStream is = new FileInputStream(file);
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				byte[] bb = new byte[2048];
				int ch;
				ch = is.read(bb);
				while (ch != -1) {
					bytestream.write(bb, 0, ch);
					ch = is.read(bb);
				}
				by = bytestream.toByteArray();
				is.close();
				System.out.println(by.length);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return by;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}