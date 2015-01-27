package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model.Finder;

@Entity
public class Produto extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1823150234402709993L;

	@Required(message = "Descrição é obrigatório")
	public String descricao;
	
	public static Finder<Long, Produto> find = new Finder<Long, Produto>(Long.class, Produto.class);
	
	public static Map<String,String> options() {
		List<Produto> produtos = find.where().orderBy("descricao").findList();
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		for (Produto p : produtos) {
			options.put(p.id.toString(),p.descricao);
		}
		return options;
	}			
	
}
