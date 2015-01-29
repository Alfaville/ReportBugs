package controllers;

import java.util.List;

import models.Operacao;
import play.cache.Cached;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Html;
import seguranca.PlaySeguranca;
import views.html.index;

@Security.Authenticated(PlaySeguranca.class)
public class Application extends Controller {

	@Cached(duration = 15, key = "home_index")
	public static Result index() {
		List<Operacao> operacoes = Operacao.find.findList();
		Html home = views.html.list_home.render(operacoes);
		return ok(index.render(Messages.get("operacoes"), home));
	}

}
