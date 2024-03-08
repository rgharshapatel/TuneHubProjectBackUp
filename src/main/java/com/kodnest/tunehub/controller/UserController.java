package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.service.SongService;
import com.kodnest.tunehub.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController 
{
	@Autowired
	UserService userService; 
	
	@Autowired
	SongService songService;
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute User user)
	{
		//email taken from registration form
		String email=user.getEmail();
		
		//checking if the email as entered in registration form
		//is present in DB or Not
		boolean status = userService.emailExists(email);
		
		if (status==false)
		{
			userService.addUser(user);
			System.out.println("User Added");
			
		} 
		else
		{
				System.out.println("User Already Exists");
		}
		
		return "index";
	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,@RequestParam("password") String password, HttpSession session,Model model)
	{
		if(userService.validateUser(email, password)==true)
		{
			String role = userService.getRole(email);
			session.setAttribute("email", email);
			if(role.equals("admin"))
			{
				return "admin";
			}
			else 
			{
				User user = userService.getUser(email);
				boolean userStatus = user.isIspremium();
				
				List<Song> songList = songService.fetchAllSongs();
				model.addAttribute("songs",songList);
				
				model.addAttribute("ispremium", userStatus);
				return "customer";
			}
		}
		else
		{
			return "login";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/exploresongs")
	public String exploresongs(String email) {
		return email;
	}
	
}
//@PostMapping("/validate")
//public String validate(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("role") String role){
//	if(userServiceImpl.validateUser(email, password,role) == true){
//		return role;
//	}
//	else {
//		return "login";
//	}
//	
//}