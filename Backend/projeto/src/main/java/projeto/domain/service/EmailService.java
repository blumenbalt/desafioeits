package projeto.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import projeto.domain.entity.Usuario;

@Component
@PropertySource({"classpath:env/mail.properties"})
public class EmailService 
{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment env;
	
	public void send(Usuario usuario)
	{
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom(env.getProperty("Email"));
		mensagem.setTo(usuario.getEmail());
		mensagem.setSubject("Cadastro realizado com sucesso!!");
		String email =
				  "Usuario: " + usuario.getEmail() + "\n" + 
				  "Senha: " + usuario.getPassword() + "\n";
		mensagem.setText(email);
		
		mailSender.send(mensagem);
		
	}

}