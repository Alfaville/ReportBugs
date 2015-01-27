package controllers;

import java.util.Date;
import java.util.List;

import models.StatusOp;
import play.Logger;
import play.Routes;
import play.cache.Cached;
import play.data.Form;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import views.html.index;

public class StatusController extends Controller {

	private static final Form<StatusOp> statusForm = Form.form(StatusOp.class);

	public static Result index() {
		return ok(index.render("Status", getIndex()));
	}

	public static Result gravar() {
		Form<StatusOp> form = statusForm.bindFromRequest();
		if (form.hasErrors()) {
			Html html = views.html.status.index.render(form,
					StatusOp.find.findList());
			return badRequest(index.render("Status", html));
		}
		StatusOp status = form.get();
		status.save();
		flash("success", Messages.get("msg.success"));
		return redirect(routes.StatusController.index());
	}

	@Transactional
	public static Result deletar(Long id) {
		try {
			StatusOp status = StatusOp.find.byId(id);
			status.delete();
			return ok(Messages.get("msg.success"));
		} catch (Exception e) {
			Logger.error("Controller: StatusController; Metodo: deletar; Erro ocorrido as "
					+ new Date());
			Logger.error("Detalhamento do erro: " + e.getMessage());
			return badRequest("Ocorreu um erro ao tentar remover este registro!");
		}
	}

	@Cached(key = "autocompleteStatus", duration = 30)
	public static Result autocompletarStatus() {
		List<StatusOp> statusList = StatusOp.find.findList();
		return ok(Json.toJson(statusList));
	}	
	
	public static Result jsRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("js_status",
				routes.javascript.StatusController.autocompletarStatus(),
				routes.javascript.StatusController.deletar()));
	}

	private static Html getIndex() {
		List<StatusOp> listStatus = StatusOp.find.findList();
		Html html = views.html.status.index.render(statusForm, listStatus);
		return html;
	}

}
