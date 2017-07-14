package projeto.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Inicio 
{
	
	//tela de login
	@RequestMapping("/login")
	public String index()
	{
		return "index";	
	}
	//retorna para a pagina inicial do sistema
	@RequestMapping("/")
	public String home()
	{
		return "dist/index";	
	}
	
}
