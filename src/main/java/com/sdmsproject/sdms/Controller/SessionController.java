package com.sdmsproject.sdms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {

	@GetMapping("/logout")
	private String logout(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(">>> Logout GET controller called");
		
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		Cookie[] cookie = request.getCookies();
		
		if(cookie != null) {
			for(Cookie cookies : cookie) {
				 cookies.setMaxAge(0);
				 cookies.setPath("/");
				 
				 response.addCookie(cookies);
			}
		}
		
		return "redirect:/login?logout";
	}
}
