package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.SafeHtml;

import play.db.ebean.Model.Finder;

@Entity
public class Comentario extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7154270335691256908L;

	@SafeHtml
	public String descricao;
	
	public static Finder<Long, Comentario> find = new Finder<Long, Comentario>(Long.class, Comentario.class);	

	@ManyToOne
	public Operacao operacao;
	
}
