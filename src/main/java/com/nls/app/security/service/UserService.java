package com.nls.app.security.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nls.app.security.model.User;

@Service
public class UserService {
	public List<User> getUsers(){
		String url="http://localhost:8080/security-service/users";
		RestTemplate restTemplate = new RestTemplate();
		User[] userArray = restTemplate.getForObject(url, User[].class);
		return Arrays.asList(userArray);
	}
}
