package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model.Finder;

@Entity
public class StatusOp extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -446094052735711896L;

	@Required( message =  "Informe a descrição")
	public String descricao;

	public static Finder<Long, StatusOp> find = new Finder<Long, StatusOp>(Long.class, StatusOp.class);
	
	public static Map<String,String> options() {
		List<StatusOp> status = find.where().orderBy("descricao").findList();
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		for (StatusOp s : status) {
			options.put(s.id.toString(),s.descricao);
		}
		return options;
	}		
	
}
