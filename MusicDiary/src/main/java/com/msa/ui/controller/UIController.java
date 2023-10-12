package com.msa.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class UIController {

	@RequestMapping({"", "/"})
	public String getIndex(@CookieValue(value = "accesstoken", required = false) String token, Model model) {
		return "index";
	}
	
	@RequestMapping("/login")
	public String getLogin(@CookieValue(value = "accesstoken", required = false) String token, Model model) {
		return "login";
	}
	
	@RequestMapping("/signup")
	public String getSignUp(@CookieValue(value = "accesstoken", required = false) String token, Model model) {
		return "signup";
	}

}
