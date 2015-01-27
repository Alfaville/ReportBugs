package models;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;

@Entity
public class Usuario extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7727800564509088515L;
	
	@Required
	public String nome;
	
}
