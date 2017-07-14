package projeto.domain.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Audited
@Entity
@Table
public class Usuario implements UserDetails  {
	
	
	/**
	 *
	 * 
	 * ATRUBUTOS 
	 * 
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	// id do usuario
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	/**
	 * 
	 */
	//nome do usuário
	@NotBlank
	@Column(nullable = false)
	private String nome;


	/**
	 * 
	 */
	@Email
	@NotBlank
	@Column(unique=true, nullable = false)
	// email do usuário
	private String email;
	
	/**
	 * 
	 */

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank
	// senha para acesso do usuário
	private String senha;
	


	@Transient
	// confirmação de senha
	private String confirmarSenha;

	
	// verifica se o usuário esta ativo
	@Column( nullable = false)
	private boolean ativo = true;
	
	// perfil do usuário no sistema
	@Enumerated(EnumType.STRING)
	private Perfil perfil;
	
	//departamento do usuário
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="departamento")
	private Departamento departamento;
	
	//papel dele no departamento
	@Enumerated(EnumType.STRING)
	private Papel papel;

	

	public boolean validar()
	{
		if (this.senha.equals(this.confirmarSenha))
		{
			return true;
		}
		return false;
	}
	
	public Usuario()
	{
		
	}
	
	public Usuario (Long id, String confirmarSenha, String nome, String email, String senha, boolean ativo, Perfil perfil, Departamento departamento, Papel papel)
	{
		super();
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.email  = email;
		this.confirmarSenha = confirmarSenha;
		this.ativo = ativo;
		this.perfil = perfil;
		this.departamento = departamento;
		this.papel = papel;
	}
	

	/**
	 * 
	 * 
	 *  GETTERS AND SETTERS
	 * 
	 */
	
	public Departamento getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public void unsetDepartamento ()
	{
		this.departamento= null;
	}
	
	
	public Papel getPapel() {
		return papel;
	}
	
	public void setPapel(Papel papel) {
		this.papel = papel;
	}
	public String getConfirmarSenha() 
	{
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) 
	{
		this.confirmarSenha = confirmarSenha;
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
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getSenha() 
	{
		return senha;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public void setSenha(String senha) 
	{
		this.senha = senha;
	}
	
	public boolean isAtivo() 
	{
		return ativo;
	}
	
	public void setAtivo() 
	{
		this.ativo = true;
	}
	public void setDesativo() 
	{
		this.ativo = false;
	}
	
	public Perfil getPerfil() 
	{
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) 
	{
		this.perfil = perfil;
	}
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<Perfil> usuario = new ArrayList<>();
		usuario.add(perfil);
		return usuario;
	}
	
	@Override
	@JsonIgnore
	public String getPassword() 
	{
		return this.senha;
	}
	
	@Override
	@JsonIgnore
	public String getUsername() 
	{
		return this.email;
	}
	
	
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() 
	{
	
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() 
	{
		
		return true;
	}
	
	
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() 
	{
		
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isEnabled() 
	{
		
		return this.ativo;
	}



	
	/**
	 * 
	 * 
	 *      HASH AND EQUALS  
	 * 
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);

		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (perfil != other.perfil)
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}
}