package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.libs.Crypto;
import validacao.GrupoValidaLogin;
import play.db.ebean.Model.Finder;

@Entity
public class Usuario extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7727800564509088515L;

	@Required(groups = GrupoValidaLogin.validaLogin.class, message = "Login é obrigatório")
	public String login;
	
	@Required(groups = GrupoValidaLogin.validaPerfil.class, message = "Nome é obrigatório")
	public String nome;

	@Required(groups = { GrupoValidaLogin.validaLogin.class, GrupoValidaLogin.validaPerfil.class }, message = "Senha é obrigatório")
	public String senha;
	
//	@Transient
//	@Required(groups = GrupoValidaLogin.validaLogin.class, message = "Projeto é obrigatório")
//	public String projeto;
	
//	@ManyToMany(mappedBy = "usuario")
//	public List<Projetos> projetos;
	
	public static Finder<Long, Usuario> find = new Finder<Long, Usuario>(Long.class, Usuario.class);
	
	public List<ValidationError> validate() {		
		List<ValidationError> errors = new ArrayList<ValidationError>();		
		Usuario usuario = Usuario.find.where().eq("login", login).eq("senha", Crypto.sign(senha)).findUnique();
		if ( usuario == null )
			errors.add(new ValidationError("usuario", Messages.get("loginView.incorreto")));
//		else if ( this.projeto == null )
//			errors.add(new ValidationError("projeto", Messages.get("loginView.projeto.erro")));
//		else {
//			List<Projetos> listProjetos = usuario.projetos;
//			for (Projetos p : listProjetos) {
//				if ( p.nome.equals(this.projeto) )
//					return null;
//			}
//			errors.add(new ValidationError("projeto.nao.inexiste", Messages.get("loginView.projeto.erro.inexistente")));
//		}
			
		return errors.isEmpty() ? null : errors;
	}
	
}
