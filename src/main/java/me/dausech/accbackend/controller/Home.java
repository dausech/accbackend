package me.dausech.accbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {

	@GetMapping("/")
	public String index() {
		return "home";
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/home")
	public String home() {
		return "home sweet home";
	}
}