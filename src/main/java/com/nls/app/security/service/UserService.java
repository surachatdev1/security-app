package com.nls.app.security.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nls.app.security.model.User;

@Service
public class UserService {
	private static final String BASE_URL = "http://localhost:8080/security-service/users";

	public List<User> getUsers() {
		String url = BASE_URL;
		RestTemplate restTemplate = new RestTemplate();
		User[] userArray = restTemplate.getForObject(url, User[].class);
		return Arrays.asList(userArray);
	}

	public User addUser(User user) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = BASE_URL;
		URI uri = new URI(url);
		User newUser = restTemplate.postForObject(uri, user, User.class);
		return newUser;
	}

	public void updateUser(User user) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = BASE_URL + "/update";
		URI uri = new URI(url);
		restTemplate.put(uri, user);

	}

	public void deleteUser(User user) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = BASE_URL + "/"+user.getId();
		URI uri = new URI(url);
		restTemplate.delete(url);
	}

}
