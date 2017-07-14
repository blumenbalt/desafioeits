package projeto.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

@Audited
@Entity
@Table(name = "mensagem")
public class Mensagem {
	
	/**
	 *
	 * ATRIBUTOS
	 * 
	 */
	/**
	 * 
	 * id da mensagem
	 * 
	 */
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * texto da mensagem
	 */
	@NotBlank
	@Size(min = 1, max = 144)
	@Column(nullable = false)
	private String texto;
	
	/**
	 * dia em que a mensagem foi publicada
	 */
	private LocalDate data;
	
	/**
	 * 
	 * GETTERS E SETTERS
	 */
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDate getData() {
		return data;
	}
	
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	/**
	 *  HASH E EQUALS
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
		Mensagem other = (Mensagem) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}
}
