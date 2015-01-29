package seguranca;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import controllers.routes;


public class PlaySeguranca extends Security.Authenticator{
 	
    public String getUsername(Context ctx) {
		return ctx.session().get("userId");
	}
    	
    public Result onUnauthorized(Context ctx) {
		ctx.flash().put("nao_logado","Para continuar precisa estar logado"); 
		return redirect(routes.LoginController.login());
    }
	
}
