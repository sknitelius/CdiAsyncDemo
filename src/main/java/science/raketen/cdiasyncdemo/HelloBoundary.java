package science.raketen.cdiasyncdemo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("hello")
@RequestScoped
public class HelloBoundary {

	@Inject
	private SlowRunningProcessor asyncCDIBean;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@QueryParam("name") String name) throws InterruptedException, ExecutionException {

		Future<String> helloFuture = asyncCDIBean.hello(name);
		
		for (int i = 0; i < 100; i++) {
			asyncCDIBean.asyncTask();
		}
		return "Response by: " + Thread.currentThread().getName() + " Msg: " + helloFuture.get();
	}
}
