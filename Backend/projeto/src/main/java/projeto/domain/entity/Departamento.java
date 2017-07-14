package projeto.domain.entity;

import javax.persistence.Column;
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
@Table
public class Departamento
{
	
	/**
	 * 
	 * ATRIBUTOS
	 * 
	 */
	//id do departamento
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	// nome do departamento
	private String nome;
	
	//descrição do departamento
	private String descricao;
	
	//departamento vinculado a esse departamento
	@ManyToOne(fetch=FetchType.EAGER)
	private Departamento departamentoPai;
	
	@Column(columnDefinition = "integer default 0")
	//quantidade de usuários do departamento
	private Long qtdUsuarios;
	
	
	public Long getQtdUsuarios()
	{
		return qtdUsuarios;
	}

	/**
	 * GETTERS E SETTERS
	 * 
	 */
	public void setQtdUsuarios(Long qtdUsuarios) 
	{
		this.qtdUsuarios = qtdUsuarios;
	}

	
	public Departamento getDepartamento() 
	{
		return departamentoPai;
	}

	public void setDepartamento(Departamento departamento) 
	{
		if (this.departamentoPai != departamento)
		{
		this.departamentoPai = departamento;
		}
	}
	public void unsetDepartamento()
	{
		this.departamentoPai=null;
	}
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getNome() 
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
	
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	/**
	 * 
	 * HASH E EQUALS
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departamentoPai == null) ? 0 : departamentoPai.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((qtdUsuarios == null) ? 0 : qtdUsuarios.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (departamentoPai == null) {
			if (other.departamentoPai != null)
				return false;
		} else if (!departamentoPai.equals(other.departamentoPai))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (qtdUsuarios == null) {
			if (other.qtdUsuarios != null)
				return false;
		} else if (!qtdUsuarios.equals(other.qtdUsuarios))
			return false;
		return true;
	}
	
}
