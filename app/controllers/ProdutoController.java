package controllers;

import java.util.Date;
import java.util.List;

import models.Produto;
import play.Logger;
import play.Routes;
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
public class ProdutoController extends Controller {

	private static final Form<Produto> produtoForm = Form.form(Produto.class);

	public static Result autocompletarProduto() {
		List<Produto> produtos = Produto.find.findList();
		return ok(Json.toJson(produtos));
	}

	public static Result index() {		
		Html html = views.html.produto.index.render(produtoForm, Produto.find.findList());
		return ok(index.render("Lista de produtos", html));
	}

	@Transactional
	public static Result gravar() {
		Form<Produto> form = produtoForm.bindFromRequest();
		if (form.hasErrors()) {
			Html html = views.html.produto.index.render(form, Produto.find.findList());
			return badRequest(index.render(Messages.get("dev.lista"), html));
		}
		Produto dev = form.get();
		dev.save();
		flash("success", Messages.get("msg.success"));
		return redirect(routes.ProdutoController.index());
	}
	
	@Transactional
	public static Result deletar(Long id) {
		try {
			Produto produto = Produto.find.byId(id);
			produto.delete();
			return ok(Messages.get("msg.success"));
		} catch (Exception e) {
			Logger.error("Controller: ProdutoController; Metodo: deletar; Erro ocorrido as "
					+ new Date());
			Logger.error("Detalhamento do erro: " + e.getMessage());
			return badRequest("Ocorreu um erro ao tentar remover este registro!");
		}
	}


	public static Result jsRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("js_produto",
				routes.javascript.ProdutoController.deletar(),
				routes.javascript.ProdutoController.autocompletarProduto()));
	}

}
