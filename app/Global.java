import java.lang.reflect.Method;

import play.GlobalSettings;
import play.api.mvc.RequestHeader;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Results;

public class Global extends GlobalSettings {

	@Override
	public Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
		return Promise.<Result> pure(
				Results.notFound(views.html.pageErro404.render(request.uri()))
			);
	}
	
	public Promise<Result> onError(RequestHeader request, Throwable t) {
        return Promise.<Result>pure(
        		Results.internalServerError(
        					views.html.errorPage.render(t)
        		));
    }

	@Override
	public Action onRequest(Request request, Method actionMethod) {
        System.out.println("before each request..." + request.toString());
        return super.onRequest(request, actionMethod);
    }
	
}
