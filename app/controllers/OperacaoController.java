package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Comentario;
import models.Desenvolvedor;
import models.Operacao;
import models.Produto;
import models.StatusOp;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;

import com.avaje.ebean.Expr;

public class OperacaoController extends Controller {

	private static final Form<Operacao> operacaoForm = Form
			.form(Operacao.class);

	public static Result novo() {
		Html html = views.html.operacao.novo.render(operacaoForm);
		return ok(views.html.index.render(Messages.get("operacao.app"), html));
	}

	public static Result operacao(String id) {
		Long idOp = Long.parseLong(id);
		Operacao op = Operacao.find.byId(idOp);
		List<Comentario> comentarios = listarComentarios(id);
		Form<Operacao> form = operacaoForm.fill(op);
		Html html = views.html.operacao.operacaox.render(form, idOp, comentarios);
		return ok(views.html.index.render(
				Messages.get("op.operacao", op.codigo), html));
	}

	private static List<Comentario> listarComentarios(String id){
		List<Comentario> comentarios = Comentario.find.select("descricao").where().eq("operacao.id", Long.parseLong( id ) ).findList();
		return comentarios;
	}
	
	public static Result editar(Long id) {
		Form<Operacao> form = operacaoForm.bindFromRequest();
		if (form.hasErrors()) {
			List<Comentario> comentarios = listarComentarios( String.valueOf( id ) );
			Html conteudo = views.html.operacao.operacaox.render(form, id, comentarios);
			Html index_home = views.html.index.render(
					Messages.get("operacao.app"), conteudo);
			return badRequest(index_home);
		}
		Operacao operacao = formOp( form.get() );	
		if (operacao == null)
			flash("erro", Messages.get("msg.error.form.op"));			
		else {
			Comentario commentario = new Comentario();
			commentario.descricao = form.data().get("observacoes");
			List<Comentario> comentarios = new ArrayList<Comentario>();
			comentarios.addAll( listarComentarios( String.valueOf( id ) ) );
			comentarios.add(commentario);
			operacao.comentarios = comentarios;			
			operacao.update(id);
			flash("success", Messages.get("msg.success"));			
		}
		return redirect(routes.Application.index());
	}

	private static Operacao formOp( Operacao op ){
		op.status = StatusOp.find.where(Expr.ilike("descricao", "%" + op.status.descricao + "%")).findUnique();
		op.desenvolvedor = Desenvolvedor.find.where(Expr.ilike("nome", "%" + op.desenvolvedor.nome + "%")).findUnique();
		op.produto = Produto.find.where(Expr.ilike("descricao", "%" + op.produto.descricao + "%")).findUnique();		
		return (op.status == null || op.desenvolvedor == null || op.produto == null) ? null : op;
	}

	public static Result gravar() {
		Form<Operacao> formOp = operacaoForm.bindFromRequest();
		if (formOp.hasErrors()) {
			Html conteudo = views.html.operacao.novo.render(formOp);
			Html index_home = views.html.index.render(
					Messages.get("operacao.app"), conteudo);
			return badRequest(index_home);
		}		
		Operacao operacao = formOp( formOp.get() );	
		if (operacao == null) {
			flash("erro", Messages.get("msg.error.form.op"));
			return redirect(routes.OperacaoController.novo());
		} else {
			Comentario commentario = new Comentario();
			commentario.descricao = formOp.data().get("observacoes");
			List<Comentario> comentarios = new ArrayList<Comentario>();
			comentarios.add(commentario);
			operacao.comentarios = comentarios;
			operacao.save();
			flash("success", Messages.get("msg.success"));
			return redirect(routes.Application.index());
		}						
	}
	
	public static Result jsRoutes() {
		return TODO;
	}

}
