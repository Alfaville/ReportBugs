package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model.Finder;


@Entity
public class Desenvolvedor extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716122761852792208L;

	@Required(message = "Informe o nome" )
	public String nome;
	
	public static Finder<Long, Desenvolvedor> find = new Finder<Long, Desenvolvedor>(Long.class, Desenvolvedor.class);
	
	public static Map<String,String> options() {
		List<Desenvolvedor> dev = find.where().orderBy("nome").findList();
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		for (Desenvolvedor d : dev) {
			options.put(d.id.toString(),d.nome);
		}
		return options;
	}			
	
}
