package science.raketen.cdiasyncdemo;

import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SlowRunningProcessor {

	@CDIAsynchronous
	public void asyncTask() {
		try {
			long sleep = 500 + (long) (Math.random() * 5000l);
			Thread.sleep(sleep);
			System.out.println(this + " " + Thread.currentThread().getName() + " sleept: " + sleep);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@CDIAsynchronous
	public CompletableFuture<String> hello(String name) {
		return CompletableFuture.completedFuture("Hello " + name + "! produced by thread: " + Thread.currentThread().getName());
	}

}
