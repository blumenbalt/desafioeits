package projeto.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.domain.entity.Papel;
import projeto.domain.entity.Usuario;
import projeto.domain.repository.IDepartamentoRepository;
import projeto.domain.repository.IUsuarioRepository;


@Service
public class UsuarioService 
{

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService mailService;
	/**
	 * 
	 * @param usuario
	 */
	@Transactional
//	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> insert(Usuario usuario) 
	{
		if (usuarioRepository.findByEmail(usuario.getEmail()) != null)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email já cadastrado");
		}
		if ( !usuario.validar() )
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Senhas não conferem");
		}
		
		
		mailService.send(usuario);
		String hash = new BCryptPasswordEncoder().encode(usuario.getPassword()); // criptografando a senha para o banco
		usuario.setSenha(hash);// set senha criptografada
		usuarioRepository.save(usuario); // inserindo o usuario
	    return null;
		
	}

	/**
	 * 
	 * 
	 */
	public Page<Usuario> listUsuarios(int page, int size, String property, String order) {
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findAll(pageable); 
	}
	
	
	/**
	 * 
	 * 
	 */
	public Page<Usuario> listUsuariosByName(int page, int size, String property, String order, String filter) {
		Direction asc;
		if ( filter.compareToIgnoreCase("null") == 0 )
		{
			filter = "";
		}
		if (order.equals("ASC"))
		{
			asc = Direction.ASC;
		}
		else
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByNomeContainingIgnoreCase(filter.toLowerCase(), pageable);
	}
	
	/**
	 * 
	 * 
	 */
	public Page<Usuario> listUsuariosAtivos(int page, int size, String property, String order)
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByAtivoTrue( pageable);
	}
	
	
	public Page<Usuario> listUsuariosAtivosByName(int page, int size, String property, String order, String filter)
	{
		

		Direction asc;
		if ( filter.compareToIgnoreCase("null") == 0 )
		{
			filter = "";
		}
		if (order.equals("ASC"))
		{
			asc = Direction.ASC;
		}
		else
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		String nomelower = "%" + filter.toLowerCase() + "%";
		return usuarioRepository.findByAtivoAndByNome(nomelower,pageable);
	}
	
	public Page<Usuario> listUsuariosByDepartamento(Long id, int page, int size, String property, String order)
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByDepartamentoIdAtivo(id, pageable);
	}
	
	public Page<Usuario> listUsuariosByDepartamentoAndByName(Long id, int page, int size, String property, String order, String filter)
	{
		Direction asc;
		if ( filter.compareToIgnoreCase("null") == 0 )
		{
			filter = "";
		}
		if (order.equals("ASC"))
		{
			asc = Direction.ASC;
		}
		else
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		String nomelower = "%" + filter.toLowerCase() + "%";
		return usuarioRepository.findByDepartamentoIdAndByNome(id,nomelower, pageable);
	}
	
	public Page<Usuario> listUsuariosForLink(int page, int size, String property, String order)
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByDepartamentoNull(pageable);
	}
	
	
	public Page<Usuario> listUsuariosForLinkByName(int page, int size, String property, String order, String filter)
	{
		Direction asc;
		if ( filter.compareToIgnoreCase("null") == 0 )
		{
			filter = "";
		}
		if (order.equals("ASC"))
		{
			asc = Direction.ASC;
		}
		else
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		String nomelower = "%" + filter.toLowerCase() + "%";
		return usuarioRepository.findByDepartamentoNullAndByNome(nomelower, pageable);
	}
	
	@Autowired
	public IDepartamentoRepository departamentoRepository;
	
	@Transactional
	  //  @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
		public void link(Long id, Long departamentoId) 
		{
			Usuario usuario = usuarioRepository.findOne(id);

			if (usuario != null)
			{
				usuario.setDepartamento(departamentoRepository.findOne(departamentoId));
				this.usuarioRepository.saveAndFlush(usuario);
			}
		}
	
	@Transactional
	  //  @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
		public void unlink(Long id) 
		{
			Usuario usuario = usuarioRepository.findOne(id);

			if (usuario != null)
			{
				usuario.setPapel(null);
				usuario.unsetDepartamento();	
				this.usuarioRepository.saveAndFlush(usuario);
			}
		}
	/**
	 * 
	 * 
	 */
	@Transactional
  //  @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void activate(Long id) 
	{
		Usuario usuario = usuarioRepository.findOne(id);

		if (usuario != null)
		{
			usuario.setAtivo();	
			this.usuarioRepository.saveAndFlush(usuario);
		}
	}
	
	/**
	 * @return 
	 * 
	 */
	@Transactional
   // @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> deactivate(Long id) 
	{
		Usuario usuario = usuarioRepository.findOne(id);
		Usuario usuarioAtual = getCurrent();

		if (usuario != usuarioAtual)
		{
			usuario.setDesativo();	
			this.usuarioRepository.saveAndFlush(usuario);
		} else
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Você não pode se desativar");
		}
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Usuario detail(Long id)
	{
		return this.usuarioRepository.findOne(id);
	}
	
	/**
	 * 
	 * @param usuario
	 * @return 
	 */
	@Transactional
  //  @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> update (Usuario usuario)
	{
		
		Usuario user = new Usuario();
		user = usuarioRepository.findOne(usuario.getId());
		if (!user.getEmail().equals(usuario.getEmail()))
		{
		
			if (usuarioRepository.findByEmail(usuario.getEmail()) != null)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email já cadastrado");
			}
		}else {	
			usuario.setSenha(user.getSenha());
			usuarioRepository.saveAndFlush(usuario);
			return null;
		}
		usuario.setSenha(user.getSenha());
		usuarioRepository.saveAndFlush(usuario);
		return null;
	}
	
	/**
	 * 
	 * 
	 */
	@Transactional
//  @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> updatePassword(Usuario usuario)
	{
		
		if (!usuario.validar())
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Senha e confirmar senha não iguais");
		}
		String hash = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setSenha(hash);
		usuarioRepository.saveAndFlush(usuario);	 
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Senha atualizada");
	}
	
	@Transactional
	 //   @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
		public void setRole(Long id, Papel papel) 
		{
			Usuario usuario = usuarioRepository.findOne(id);
			usuario.setPapel(papel);
		}
	
	
	public Usuario getCurrent()
	{
		//Usuario usuarioCurrent = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario usuarioCurrent = usuarioRepository.findOne((long) 1);
		return usuarioCurrent;
	}
	
	

	
}
