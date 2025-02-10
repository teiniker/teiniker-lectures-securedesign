package org.se.lab.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/user")
public class UserProcessing
{
	// Simulate database table
	private Map<Integer, User> table = new ConcurrentHashMap<>();

	public UserProcessing()
	{
		// Simulate INSERT statement
		table.put(1, new User(1, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg"));
		table.put(2, new User(2, "marge", "xCSuPDv2U6I5jEO1wqvEQ/jPYhY="));
		table.put(3, new User(3, "bart", "Ls4jKY8G2ftFdy/bHTgIaRjID0Q="));
		table.put(4, new User(4, "lisa", "xO0U4gIN1F7bV7X7ovQN2TlSUF4="));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") int id)
	{
		User user = table.get(id);
		if(user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(table.get(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> findAll()
	{
		return ResponseEntity.ok(new ArrayList(table.values()));
	}
}
