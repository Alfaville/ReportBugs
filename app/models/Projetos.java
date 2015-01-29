package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import play.db.ebean.Model.Finder;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Projetos extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9045645315136113697L;

	public String nome;
	
	@ManyToMany
	public List<Usuario> usuario;
	
	public static Finder<Long, Projetos> find = new Finder<Long, Projetos>(Long.class, Projetos.class);
		
	public static Map<String,String> options() {
		List<Projetos> prodList = find.where().orderBy("nome").findList();
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		for (Projetos p : prodList) {
			options.put(p.id.toString(), p.nome);
		}
		return options;
	}	
		
}
