package controllers;

import java.util.Date;
import java.util.List;

import models.Desenvolvedor;
import play.Logger;
import play.Routes;
import play.cache.Cached;
import play.data.Form;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Html;
import seguranca.PlaySeguranca;
import views.html.index;

@Security.Authenticated(PlaySeguranca.class)
public class DesenvolvedorController extends Controller {

	private static final Form<Desenvolvedor> devForm = Form
			.form(Desenvolvedor.class);

	public static Result index() {
		return ok(index.render(Messages.get("dev.lista"), getIndex()));
	}

	@Cached(key = "autocomplete", duration = 30)
	public static Result autocompletar() {
		List<Desenvolvedor> desenvolvedores = Desenvolvedor.find.findList();
		return ok(Json.toJson(desenvolvedores));
	}

	@Transactional
	public static Result gravar() {
		Form<Desenvolvedor> form = devForm.bindFromRequest();
		if (form.hasErrors()) {
			Html html = views.html.desenvolvedor.index.render(form,
					Desenvolvedor.find.findList());
			return badRequest(index.render(Messages.get("dev.lista"), html));
		}
		Desenvolvedor dev = form.get();
		dev.save();
		flash("success", Messages.get("msg.success"));
		return redirect(routes.DesenvolvedorController.index());
	}

	@Transactional
	public static Result deletar(Long id) {
		try {
			Desenvolvedor dev = Desenvolvedor.find.byId(id);
			dev.delete();
			return ok(Messages.get("msg.success"));
		} catch (Exception e) {
			Logger.error("Controller: DesenvolvedorController; Metodo: deletar; Erro ocorrido as "
					+ new Date());
			Logger.error("Detalhamento do erro: " + e.getMessage());
			return badRequest("Ocorreu um erro ao tentar remover este registro!");
		}
	}

	public static Result jsRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("js_dev",
				routes.javascript.DesenvolvedorController.autocompletar(),
				routes.javascript.DesenvolvedorController.deletar()));
	}

	private static Html getIndex() {
		Html html = views.html.desenvolvedor.index.render(devForm,
				Desenvolvedor.find.findList());
		return html;
	}

}
