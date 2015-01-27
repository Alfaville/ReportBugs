package models;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;

@MappedSuperclass
public class EntidadeBase extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3981465871793630960L;

	@Id
	@GeneratedValue
	public Long id;
	
    @CreatedTimestamp
    public Timestamp dataCriacao;

	@Version
    public Timestamp dataAtualizacao;	
	
	public Boolean ativo = true;
	
}
