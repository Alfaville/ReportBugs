package controllers;

import java.util.List;

import models.Operacao;
import models.Usuario;
import play.cache.Cached;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Html;
import seguranca.PlaySeguranca;
import validacao.GrupoValidaLogin;
import views.html.index;

@Security.Authenticated(PlaySeguranca.class)
public class Application extends Controller {
	
	private static final Form<Usuario> userForm = Form.form(Usuario.class);
	
	@Cached(duration = 15, key = "home_index")
	public static Result index() {
		List<Operacao> operacoes = Operacao.find.findList();
		Html home = views.html.list_home.render(operacoes);
		return ok(index.render(Messages.get("operacoes"), home));
	}

	public static Result perfil(Long id){
		Form<Usuario> form = userForm.fill(Usuario.find.byId(id));
		Html perfilHtml = views.html.perfil.perfil.render(form);
		return ok(index.render(Messages.get("perfil"), perfilHtml));
	}

	public static Result gravar(){
		Form<Usuario> form = userForm.bindFromRequest();
		Html perfilHtml = views.html.perfil.perfil.render(form);
		
		String nome = form.data().get("nome");
		if (nome == null || nome.isEmpty()) {		
			flash("erro", "Informe o nome do usuário");
			return badRequest(index.render(Messages.get("perfil"), perfilHtml));
		}
		
		String senha = form.data().get("senha");
		String newPassword = form.data().get("newPassword");
		String repeatNewPassword = form.data().get("repeatNewPassword");
		
		Usuario usuario = Usuario.find.where().eq("senha", Crypto.sign(senha)).findUnique();
		if ( usuario == null ) {			
			flash("erro", "Senha não existe");
			return badRequest(index.render(Messages.get("perfil"), perfilHtml));
		}	
		
		if(!Crypto.sign(senha).equals(usuario.senha)) {
			flash("erro", "Senha do usuário está incorreta");
			return badRequest(index.render(Messages.get("perfil"), perfilHtml));
		}
	
		if ( !newPassword.equals(repeatNewPassword) ) {
			flash("erro", "Senhas novas não conferem.");
			return badRequest(index.render(Messages.get("perfil"), perfilHtml));
		}		
		
		Usuario u = form.get();
		u.senha = Crypto.sign(newPassword);
		u.update(usuario.id);
		
		flash("success", Messages.get("msg.success"));
		return redirect(routes.Application.index());
	}	
	
}
