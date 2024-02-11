package org.se.lab.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/user")
public class UserProcessing
{
	// Simulate database table
	private Map<Integer, UserDTO> table = new TreeMap<>();

	public UserProcessing()
	{
		// Simulate INSERT statement
		table.put(1, new UserDTO(1, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg"));
		table.put(2, new UserDTO(2, "marge", "xCSuPDv2U6I5jEO1wqvEQ/jPYhY="));
		table.put(3, new UserDTO(3, "bart", "Ls4jKY8G2ftFdy/bHTgIaRjID0Q="));
		table.put(4, new UserDTO(4, "lisa", "xO0U4gIN1F7bV7X7ovQN2TlSUF4="));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable("id") int id)
	{
		return ResponseEntity.ok(table.get(id));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll()
	{
		return ResponseEntity.ok(new ArrayList(table.values()));
	}
}
