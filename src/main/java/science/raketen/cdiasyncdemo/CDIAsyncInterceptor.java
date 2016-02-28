package science.raketen.cdiasyncdemo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@CDIAsynchronous
@Interceptor
public class CDIAsyncInterceptor {

  @Resource
  private ExecutorService executerService;

  @AroundInvoke
  public Object intercept(final InvocationContext ic) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        // Unpack the "fake" future from the business method.
        Future<?> proceed = (Future<?>) ic.proceed();
        return proceed.get();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    } , executerService);
  }

}
