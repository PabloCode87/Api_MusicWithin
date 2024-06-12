package com.ejemplos.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController implements ErrorController{

	@GetMapping("/error")
	public String index() {
		return "forward:/index.html";
	}
}
