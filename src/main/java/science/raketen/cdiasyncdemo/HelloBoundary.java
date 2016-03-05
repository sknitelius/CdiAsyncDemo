/*
 * Copyright 2016 Stephan Knitelius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package science.raketen.cdiasyncdemo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
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

  public void listen(@Observes String sysout) {
    int i = 1 + 1;
    String x = (i / 2) + sysout;
  }
}
