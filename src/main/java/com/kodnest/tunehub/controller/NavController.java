package com.kodnest.tunehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController 
{
	@GetMapping("/login")
	public String Login()
	{
		return "login";
	}
	@GetMapping("/register")
	public String register()
	{
		return "registration";
	}
	@GetMapping("/newsong")
	public String newsong()
	{
		return "newsong";
	}
}
