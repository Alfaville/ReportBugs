package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Constraints.Required;

@Entity
public class Operacao extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2169871975826723202L;

	@Required(message = "Código é obrigatório")
	public String codigo;

	@Required(message = "Descrição é obrigatório")
	public String descricao;

	@Required(message = "Produo é obrigatório")
	@OneToOne(cascade = CascadeType.MERGE)
	public Produto produto;

	@Required(message = "Desenvolvedor é obrigatório")
	@OneToOne(cascade = CascadeType.MERGE)
	public Desenvolvedor desenvolvedor;

	@Required(message = "Status é obrigatório")
	@OneToOne(cascade = CascadeType.MERGE)
	public StatusOp status;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	public List<Comentario> comentarios;

	public static Finder<Long, Operacao> find = new Finder<Long, Operacao>(
			Long.class, Operacao.class);

}
