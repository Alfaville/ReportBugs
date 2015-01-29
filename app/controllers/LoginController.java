package controllers;

import models.Usuario;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Html;
import seguranca.PlaySeguranca;
import validacao.GrupoValidaLogin;

public class LoginController extends Controller {

	private static final Form<Usuario> formLogin = Form.form(Usuario.class, GrupoValidaLogin.validaLogin.class);
	
	public static Result login() {
		return ok(views.html.login.login.render(formLogin));
	}

	public static Result logar() {
		Form<Usuario> form = formLogin.bindFromRequest();
		if (form.hasErrors()) {
			Html login = views.html.login.login.render(form);
			return badRequest(login);
		}			
		session().clear();		
		Usuario u = form.get();		
		Usuario user = Usuario.find.where().eq("login", u.login).findUnique();
		session().put("userId", user.id.toString());
		session().put("name", user.nome);
		return redirect(routes.Application.index());
	}
	
	@Security.Authenticated(PlaySeguranca.class)
	public static Result logout(){
		session().clear();
		flash("success", Messages.get("login.msg.sucesso"));
		return redirect(routes.LoginController.login());
	}

}
