package projeto.domain.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import projeto.domain.entity.Papel;
import projeto.domain.entity.Usuario;
import projeto.domain.repository.IDepartamentoRepository;
import projeto.domain.repository.IUsuarioRepository;
import projeto.infrastructure.UsuarioFile;

@Service
public class UsuarioService 
{

	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/

	/**
	 * 
	 */
	@Autowired
	public IDepartamentoRepository departamentoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IUsuarioRepository usuarioRepository;

	/**
	 * 
	 */
	@Autowired
	private EmailService mailService;

	/**
	 * 
	 */
	@Autowired
	private UsuarioFile usuarioFile;

	/*------------------------------------------------------------------------
	 * 
	 * 							SERVIÇOS
	 * 
	 *-----------------------------------------------------------------------*/

	/**
	 * 
	 * @param usuario
	 * @return
	 */
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> insert(Usuario usuario) 
	{

		Assert.notNull(usuario.getNome(), "Não pode ser nulo");
		Assert.notNull(usuario.getEmail(), "Não pode ser nulo");
		Assert.notNull(usuario.getSenha(), "Não pode ser nulo");
		if (usuarioRepository.findByEmail(usuario.getEmail()) != null) 
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email já cadastrado");
		}
		if (!usuario.validar()) 
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Senhas não conferem");
		}
		mailService.send(usuario);
		String hash = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setSenha(hash);
		usuarioRepository.save(usuario);
		return null;

	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @return
	 */
	public Page<Usuario> listUsuarios(int page, int size, String property, String order) 
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findAll(pageable);
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	public Page<Usuario> listUsuariosByName(int page, int size, String property, String order, String filter) 
	{
		Direction asc;
		if (filter.compareToIgnoreCase("null") == 0) 
		{
			filter = "";
		}
		if (order.equals("ASC")) 
		{
			asc = Direction.ASC;
		} else 
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByNomeContainingIgnoreCase(filter.toLowerCase(), pageable);
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @return
	 */
	public Page<Usuario> listUsuariosAtivos(int page, int size, String property, String order) 
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByAtivoTrue(pageable);
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	public Page<Usuario> listUsuariosAtivosByName(int page, int size, String property, String order, String filter) 
	{

		Direction asc;
		if (filter.compareToIgnoreCase("null") == 0) 
		{
			filter = "";
		}
		if (order.equals("ASC")) 
		{
			asc = Direction.ASC;
		} else 
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		String nomelower = "%" + filter.toLowerCase() + "%";
		return usuarioRepository.findByAtivoAndByNome(nomelower, pageable);
	}

	/**
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @return
	 */
	public Page<Usuario> listUsuariosByDepartamento(Long id, int page, int size, String property, String order) 
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByDepartamentoIdAtivo(id, pageable);
	}

	/**
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	public Page<Usuario> listUsuariosByDepartamentoAndByName(Long id, int page, int size, String property, String order,
			String filter) 
	{
		Direction asc;
		if (filter.compareToIgnoreCase("null") == 0) 
		{
			filter = "";
		}
		if (order.equals("ASC")) 
		{
			asc = Direction.ASC;
		} else 
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		String nomelower = "%" + filter.toLowerCase() + "%";
		return usuarioRepository.findByDepartamentoIdAndByNome(id, nomelower, pageable);
	}

	/**
	 *                      Usuários para vinculação
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @return
	 */
	public Page<Usuario> listUsuariosForLink(int page, int size, String property, String order) 
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return usuarioRepository.findByDepartamentoNull(pageable);
	}

	/**
	 *                     Usuários para vinculação com filtro nome
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @param filter
	 * @return
	 */
	public Page<Usuario> listUsuariosForLinkByName(int page, int size, String property, String order, String filter) 
	{
		Direction asc;
		if (filter.compareToIgnoreCase("null") == 0) 
		{
			filter = "";
		}
		if (order.equals("ASC")) 
		{
			asc = Direction.ASC;
		} else 
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		String nomelower = "%" + filter.toLowerCase() + "%";
		return usuarioRepository.findByDepartamentoNullAndByNome(nomelower, pageable);
	}
	
	/**
	 *                       Vincular usuário ao departamento
	 * @param id
	 * @param departamentoId
	 */
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void link(Long id, Long departamentoId) 
	{
		Usuario usuario = usuarioRepository.findOne(id);
		Assert.notNull(departamentoRepository.findOne(departamentoId), "Não pode ser nulo");
		if (usuario != null) 
		{
			usuario.setDepartamento(departamentoRepository.findOne(departamentoId));
			this.usuarioRepository.saveAndFlush(usuario);
		}
	}

	/**
	 *                        Desvincular usuário ao departamento
	 * @param id
	 */
	@Transactional
	 @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
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
	 * @param id
	 */
	@Transactional
	 @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
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
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	 @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> deactivate(Long id) 
	{
		Usuario usuario = usuarioRepository.findOne(id);
		Usuario usuarioAtual = getCurrent();

		if (usuario != usuarioAtual) // usuário não pode se desativar
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
	 @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> update(Usuario usuario) 
	{
		Assert.notNull(usuario.getNome(), "Não pode ser nulo");
		Usuario user = new Usuario();
		user = usuarioRepository.findOne(usuario.getId());
		
		if (!user.getEmail().equals(usuario.getEmail())) //Email unique
		{

			if (usuarioRepository.findByEmail(usuario.getEmail()) != null) 
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email já cadastrado");
			}
		} else 
		{

			if (!(usuarioRepository.findFilesEquals(usuario.getFilePath(), usuario.getId()).isEmpty()))
			{

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Arquivo  já cadastrado");

			}
		}
		usuario.setSenha(user.getSenha());
		usuarioRepository.saveAndFlush(usuario);
		return null;
	}

	/**
	 * 
	 * @param usuario
	 * @return
	 */
	@Transactional
	 @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public ResponseEntity<String> updatePassword(Usuario usuario) 
	{

		if (!usuario.validar()) //confere senha e confirmar senha
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Senha e confirmar senha não iguais");
		}
		String hash = new BCryptPasswordEncoder().encode(usuario.getPassword());
		usuario.setSenha(hash);
		usuarioRepository.saveAndFlush(usuario);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Senha atualizada");
	}

	/**
	 * 
	 * @param id
	 * @param papel
	 */
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void setRole(Long id, Papel papel) 
	{
		Usuario usuario = usuarioRepository.findOne(id);
		usuario.setPapel(papel);
	}

	/**
	 * 
	 * @param file
	 * @param id
	 * @return
	 */
	public ResponseEntity<String> uploadFile(MultipartFile file, Long id) 
	{
		String path = file.getOriginalFilename();
		Usuario usuario = usuarioRepository.findOne(id);
		if (usuario == null) 
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("usuario nulo");
		}
		if (!(usuarioRepository.findFilesEquals(path, id).isEmpty())) 
		{
			usuario.setFilePath(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("O contrato pertence a outro usuário");

		} else 
		{

			usuarioFile.write(file, id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Arquivo salvo!");
		}

	}

	/**
	 * 
	 * @param response
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<String> downloadFile(HttpServletResponse response, Long id) throws IOException 
	{
		Usuario usuario = usuarioRepository.findOne(id);
		if (usuario == null)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("usuario nulo");
		}
		String path = usuarioRepository.findOne(id).getFilePath();
		usuarioFile.read(response, id, path);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Download!");
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<String> clearFile(Long id) 
	{
		Usuario usuario = usuarioRepository.findOne(id);
		if (usuario == null) 
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("usuario nulo");
		}
		String filePath = "/home/eits02/Área de Trabalho/Desafio eits/usuario-files/";
		String path = filePath + usuario.getFilePath();
		File file = new File(path);
		file.delete();
		usuario.setFilePath(null);
		usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Arquivo removido!");

	}

	/**
	 *             Retorna o usuário logado
	 * @return
	 */
	public Usuario getCurrent() 
	{
		 Usuario usuarioCurrent = (Usuario)

		 SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioCurrent;
	}

}
