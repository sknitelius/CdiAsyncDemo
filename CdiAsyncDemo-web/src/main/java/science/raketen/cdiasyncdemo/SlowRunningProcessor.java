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

import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class SlowRunningProcessor {

  @Inject
  private MessageEndpoint messageEndpoint;

  @CDIAsynchronous
  public void asyncTask(int i) {
    try {
      long sleep = 500 + (long) (Math.random() * 1000l);
      Thread.sleep(sleep);
      String msg = "Task: " + i + " completed by: " + Thread.currentThread().getName() + " slept: " + sleep;
      messageEndpoint.pushMessage(msg);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @CDIAsynchronous
  public CompletableFuture<String> hello(String name) {
    String msg = "Hello " + name + "! produced by thread: " + Thread.currentThread().getName();
    return CompletableFuture.completedFuture(msg);
  }

}
