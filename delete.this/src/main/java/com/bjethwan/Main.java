package com.bjethwan;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class Main {
	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();
		List<String> response = client
				.target("http://localhost:8181")
				.path("backend1")
				.request(MediaType.APPLICATION_JSON)
				.get(List.class);
		
		System.out.println(response);
	}
}
