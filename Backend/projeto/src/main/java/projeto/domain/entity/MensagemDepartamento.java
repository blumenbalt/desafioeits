package projeto.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name = "mensagem_departamento")
public class MensagemDepartamento {
	
	/*------------------------------------------------------------------------
	 * 
	 * 							ATRIBUTOS
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Usuario usuario;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Departamento departamento;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Mensagem mensagem;
	
	
	/*------------------------------------------------------------------------
	 * 
	 * 							GETTERS E SETTERS
	 * 
	 *-----------------------------------------------------------------------*/
	
	/**
	 * 
	 * @return
	 */
	public Usuario getUsuario() 
	{
		return usuario;
	}
	
	/**
	 * 
	 * @param usuario
	 */
	public void setUsuario(Usuario usuario) 
	{
		this.usuario = usuario;
	}

	/**
	 * 
	 * @return
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public Departamento getDepartamento() 
	{
		return departamento;
	}

	/**
	 * 
	 * @param departamento
	 */
	public void setDepartamento(Departamento departamento) 
	{
		this.departamento = departamento;
	}

	/**
	 * 
	 * @return
	 */
	public Mensagem getMensagem()
	{
		return mensagem;
	}

	/**
	 * 
	 * @param mensagem
	 */
	public void setMensagem(Mensagem mensagem) 
	{
		this.mensagem = mensagem;
	}

	
	/*------------------------------------------------------------------------
	 * 
	 * 							HASH E EQUALS
	 * 
	 *-----------------------------------------------------------------------*/
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MensagemDepartamento other = (MensagemDepartamento) obj;
		if (departamento == null) 
		{
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mensagem == null) 
		{
			if (other.mensagem != null)
				return false;
		} else if (!mensagem.equals(other.mensagem))
			return false;
		if (usuario == null) 
		{
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}
