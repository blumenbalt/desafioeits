package projeto.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Inicio 
{
	@RequestMapping("/login")
	public String index() 
	{
		return "index.jsp";
	}
	
	/**
	 * 
	 */
	@RequestMapping("/")
	public String home() 
	{
		return "dist/index.html";
	}

}
