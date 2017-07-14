package projeto.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projeto.domain.entity.Usuario;
import projeto.domain.repository.IUsuarioRepository;

@Service
public class AppUserDetailsService	implements UserDetailsService
{
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	
	public Usuario loadUserByUsername(String email)
	{
		try
		{
			return  usuarioRepository.findByEmailAndAtivo("admin@admin.com");
		} 
		catch (Exception e)
		{
			throw new UsernameNotFoundException("Usuário não encontrado.");
}
	}

	
}
