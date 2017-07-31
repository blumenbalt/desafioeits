package projeto.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import projeto.domain.entity.Departamento;
import projeto.domain.entity.Mensagem;
import projeto.domain.entity.MensagemDepartamento;
import projeto.domain.entity.Usuario;
import projeto.domain.repository.IDepartamentoRepository;
import projeto.domain.repository.IMensagemDepartamentoRepository;
import projeto.domain.repository.IMensagemRepository;
import projeto.domain.repository.IUsuarioRepository;

@Service
public class DepartamentoService 
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
	private IMensagemDepartamentoRepository mensagemDepartamentoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IMensagemRepository mensagemRepository;

	/**
	 * 
	 */
	@Autowired
	private IDepartamentoRepository departamentoRepository;

	/**
	 * 
	 */
	@Autowired
	public IUsuarioRepository usuarioRepository;

	/**
	 * 
	 */
	@Autowired
	public UsuarioService usuarioService;

	/*------------------------------------------------------------------------
	 * 
	 * 							SERVIÇOS DEPARTAMENTO
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param departamento
	 * @return
	 */
	@Transactional
	public ResponseEntity<String> insert(Departamento departamento) 
	{
		Assert.isTrue(!(departamentoRepository.findByNome(departamento.getNome()) != null), "Esse nome ja existe");

		if (this.departamentoRepository.findByNome(departamento.getNome()) != null) 
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nome do departamento já cadastrado");
		}
		Assert.notNull(departamento.getNome(), "Não pode ser nulo");
		Assert.notNull(departamento.getDescricao(), "Não pode ser nulo");

		this.departamentoRepository.save(departamento);
		return null;
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
	@Transactional(readOnly = true)
	public Page<Departamento> listDepartamentosByName(int page, int size, String property, String order,
			String filter) 
	{
		Direction asc;
		if (filter.compareToIgnoreCase("null") == 0) 
		{
			filter = "";
		}
		String filtro = ("%" + filter.toLowerCase() + "%");
		if (order.equals("ASC")) 
		{
			asc = Direction.ASC;
		} else 
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		Page<Departamento> lista = departamentoRepository.findByNomeOrDescricaoContainingIgnoreCase(filtro, pageable);
		for (Departamento departamento : lista) 
		{
			departamento.setQtdUsuarios(this.usuarioRepository.countByDepartamentoId(departamento.getId()));
		}
		return lista;
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
	@Transactional(readOnly = true)
	public Page<Departamento> listDepartamentosByNotId(Long id, int page, int size, String property, String order) 
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);

		Page<Departamento> lista = departamentoRepository.findByIdNot(id, pageable);
		return lista;
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
	@Transactional(readOnly = true)
	public Page<Departamento> listDepartamentosByNameNotId(Long id, int page, int size, String property, String order,
			String filter) 
	{
		Direction asc;
		if (filter.compareToIgnoreCase("null") == 0) 
		{
			filter = "";
		}
		String filtro = ("%" + filter.toLowerCase() + "%");
		if (order.equals("ASC"))
		{
			asc = Direction.ASC;
		} else 
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		Page<Departamento> lista = departamentoRepository.findByNomeContainingIdNot(id, filtro, pageable);
		return lista;
	}

	/**
	 * 
	 * @param id
	 * @param idPai
	 */
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void link(Long id, Long idPai) 
	{
		Assert.notNull(id, "Não pode ser nulo");
		Assert.notNull(idPai, "Não pode ser nulo");
		Assert.isTrue(departamentoRepository.findOne(id) != null, "Não existe");
		Assert.isTrue(departamentoRepository.findOne(idPai) != null, "Não existe");
		Departamento departamento = departamentoRepository.findOne(id);
		Departamento departamentoPai = departamentoRepository.findOne(idPai);
		departamento.setDepartamento(departamentoPai);
		this.departamentoRepository.saveAndFlush(departamento);

	}

	/**
	 * 
	 * @param id
	 */
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void unlink(Long id) 
	{
		Assert.isTrue(departamentoRepository.findOne(id) != null, "Não existe");
		Departamento departamento = departamentoRepository.findOne(id);
		departamento.unsetDepartamento();
		this.departamentoRepository.saveAndFlush(departamento);

	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param property
	 * @param order
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Departamento> listAll(int page, int size, String property, String order) 
	{
		Direction asc;
		asc = Direction.ASC;
		PageRequest pageable = new PageRequest(page, size, asc, property);
		Page<Departamento> lista = departamentoRepository.findAll(pageable);
		for (Departamento departamento : lista) 
		{
			departamento.setQtdUsuarios(this.usuarioRepository.countByDepartamentoId(departamento.getId()));
		}
		return lista;
	}

	
	@Transactional(readOnly = true)
	public Departamento detail(Long id) 
	{
		Assert.notNull(departamentoRepository.findOne(id), "nulo");
		return this.departamentoRepository.findOne(id);
	}


	/**
	 * 
	 * @param departamento
	 * @return
	 */
	@Transactional
	public ResponseEntity<String> update(Departamento departamento) 
	{
		Departamento dpto = departamentoRepository.findOne(departamento.getId());

		Assert.isTrue(!(departamentoRepository.findByNome(departamento.getNome()) != null), "Esse nome ja existe");
		Assert.notNull(departamento.getNome(), "Não pode ser nulo");
		Assert.notNull(departamento.getDescricao(), "Não pode ser nulo");
		if (!dpto.getNome().equals(departamento.getNome())) 
		{

			if (departamentoRepository.findByNome(departamento.getNome()) != null)
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nome já cadastrado");
			}
		} else 
		{
			if (dpto.getDepartamento() != null) 
			{
				departamento.setDepartamento(dpto.getDepartamento());
				this.departamentoRepository.save(departamento);
				return null;
			}

		}

		if (dpto.getDepartamento() != null) 
		{
			departamento.setDepartamento(dpto.getDepartamento());
			this.departamentoRepository.save(departamento);
			return null;
		}
		departamento.setDepartamento(dpto.getDepartamento());
		this.departamentoRepository.save(departamento);
		return null;
	}


	/**
	 * 
	 * @param id
	 */
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public void delete(Long id) 
	{
		Assert.notNull(departamentoRepository.findOne(id), "nulo");
		List<MensagemDepartamento> lista = mensagemDepartamentoRepository.findByDepartamentoId(id);
		for (int i = 0; i < lista.size(); i++) 
		{
			MensagemDepartamento mensagem = lista.get(i);
			this.mensagemRepository.delete(mensagem.getMensagem().getId());
			this.mensagemDepartamentoRepository.delete(mensagem.getId());

		}

		List<Usuario> listaUsuario = usuarioRepository.findByDepartamentoId(id);
		for (int i = 0; i < listaUsuario.size(); i++)
		{
			Usuario usuario = listaUsuario.get(i);
			usuario.unsetDepartamento();
			usuario.setPapel(null);
		}

		this.departamentoRepository.delete(id);
	}

	/*------------------------------------------------------------------------
	 * 
	 * 							SERVIÇOS DE MENSAGEM
	 * 
	 *-----------------------------------------------------------------------*/

	
	/**
	 * 
	 * @param mensagemDepartamento
	 */
	@Transactional
	public void insertMessageDepartament(MensagemDepartamento mensagemDepartamento) 
	{
		mensagemDepartamento.setUsuario(usuarioService.getCurrent());
		mensagemDepartamento.setDepartamento(usuarioService.getCurrent().getDepartamento());
		Mensagem mensagem = mensagemDepartamento.getMensagem();
		mensagem.setData(LocalDate.now());
		Assert.notNull(departamentoRepository.findOne(mensagemDepartamento.getDepartamento().getId()),
				"Departamento nulo ou nao existe");
		Assert.notNull(usuarioRepository.findOne(mensagemDepartamento.getUsuario().getId()),
				"Usuario nulo ou nao existe");
		this.mensagemRepository.saveAndFlush(mensagem);
		this.mensagemDepartamentoRepository.save(mensagemDepartamento);
	}

	/**
	 * 
	 * @return
	 */
	public MensagemDepartamento showMessage() 
	{
		return this.mensagemDepartamentoRepository.findMensagemByUsuarioId(this.usuarioService.getCurrent().getId());
	}


	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public ResponseEntity<String> deleteMessageDepartament(Long id) 
	{
		Assert.notNull(mensagemDepartamentoRepository.findOne(id), "nulo ou não existe");
		MensagemDepartamento mensagem = this.mensagemDepartamentoRepository.findOne(id);
		mensagem.getUsuario().getId();
		if (mensagem.getUsuario().getId() == usuarioService.getCurrent().getId()) 
		{
			this.mensagemRepository.delete(mensagem.getMensagem().getId());
			this.mensagemDepartamentoRepository.delete(id);

		} else 
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Não pode deletar mensagens que você não enviou");
		}
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
	public Page<MensagemDepartamento> listAllByUserId(int page, int size, String property, String order) 
	{
		Direction asc;
		if (order.equals("ASC")) 
		{
			asc = Direction.ASC;
		} else 
		{
			asc = Direction.DESC;
		}
		PageRequest pageable = new PageRequest(page, size, asc, property);
		return this.mensagemDepartamentoRepository.findByUsuarioId(this.usuarioService.getCurrent().getId(), pageable);
	}

}
