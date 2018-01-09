package com.ytf.apple.respositroy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ytf.apple.AppleApplication;
import com.ytf.apple.entity.City;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings({"rawtypes","unchecked"})
public class CityRepositoryTest {

	@Autowired
	CityRepository cityRepository;

	@Test
	public void getAll() {

		List<City> citys = cityRepository.findAll();
		City city = cityRepository.findCityById(1L);

		int count = cityRepository.getCountForName("上海");
		System.out.println(citys);
		System.out.println(count);
		System.out.println(city.getName());

	}

	@Autowired
	TestRestTemplate testRestTemplate;

	/**
	 * GET请求测试
	 */
	@Test
	public void get() throws Exception {
		Map<String, String> multiValueMap = new HashMap<>();
		multiValueMap.put("num", "get two");// 传值，但要在url上配置相应的参数
		String one = testRestTemplate.getForObject("/getOne?num={num}", String.class, multiValueMap);
		System.out.println(one);
	}

	/**
	 * POST请求测试
	 */
	@Test
	public void post() throws Exception {
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		multiValueMap.add("num", "post three");
		String one = testRestTemplate.postForObject("/getOne", multiValueMap, String.class);
		System.out.println(one);
	}

	/**
	 * 文件上传测试
	 */
	@Test
	public void upload() throws Exception {
		Resource resource = new FileSystemResource("C:\\Users\\IBM_ADMIN\\Desktop\\temp\\aaaaaaaaaaa.java");
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		multiValueMap.add("username", "admin");
		multiValueMap.add("files", resource);
		String one = testRestTemplate.postForObject("/upload", multiValueMap, String.class);
		System.out.println(one);
	}
	/**
	 * 文件下载测试
	 */
	@Test
	public void download() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("token", "xxxxxx");
		HttpEntity<?> formEntity = new HttpEntity(headers);
		String[] urlVariables = new String[] { "admin" };
		ResponseEntity<byte[]> response = testRestTemplate.exchange("/download?name={1}", HttpMethod.GET,
				formEntity, byte[].class, urlVariables);
		System.out.println(response);
		System.out.println(response.getStatusCode());
		if (response.getStatusCode() == HttpStatus.OK) {
			File f = new File("C:\\Users\\IBM_ADMIN\\Desktop\\temp\\aaaaaaaaaaa.java");
			byte[] file = response.getBody();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(file);
		}
	}

	@Test
	public void getHeader() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("token", "xxxxxx");
		HttpEntity formEntity = new HttpEntity(headers);
		String[] urlVariables = new String[] { "admin" };
		ResponseEntity<String> result = testRestTemplate.exchange("/download", HttpMethod.GET,
				formEntity, String.class, urlVariables);
		System.out.println(result.getBody());
	}
	
    @Test
    public void putHeader() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token","xxxxxx");
        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("username","lake");
        HttpEntity formEntity = new HttpEntity(multiValueMap,headers);
        ResponseEntity<String> result = testRestTemplate.exchange("/test/putHeader", HttpMethod.PUT,formEntity,String.class);
        System.out.println(result.getBody());
    }
    
	@Test
    public void delete() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token","xxxxx");
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("username","lake");
        HttpEntity formEntity = new HttpEntity(multiValueMap,headers);
        String[] urlVariables = new String[]{"admin"};
        ResponseEntity<String> result = testRestTemplate.exchange("/test/delete?username={username}", HttpMethod.DELETE,formEntity,String.class,urlVariables);
        System.out.println(result.getBody());
    }
}
