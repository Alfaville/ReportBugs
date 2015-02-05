import java.util.List;

import models.Usuario;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.RequestHeader;
import play.libs.Crypto;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

	@Override
	public Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
		return Promise.<Result> pure(Results.notFound(views.html.pageErro404
				.render(request.uri())));
	}

	public Promise<Result> onError(RequestHeader request, Throwable t) {
		return Promise.<Result> pure(Results
				.internalServerError(views.html.errorPage.render(t)));
	}

	@Override
	public void onStart(Application app) {
		List<Usuario> u = Usuario.find.all();
		if (u.isEmpty()) {
			Usuario usuario = new Usuario();
			usuario.login = "admin";
			usuario.nome = "Administrador";
			usuario.senha = Crypto.sign("admin123");			
//			
//			Projetos p = new Projetos();
//			p.nome = "SNOA";
//			Projetos p2 = new Projetos();
//			p2.nome = "SOIA";			
//			Ebean.save(p);
//			Ebean.save(p2);
//			
//			usuario.projetos.add(p);
			Ebean.save(usuario);
		}
	}

}
