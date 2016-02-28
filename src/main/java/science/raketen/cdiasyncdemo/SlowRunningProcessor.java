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
    return CompletableFuture
        .completedFuture("Hello " + name + "! produced by thread: " + Thread.currentThread().getName());
  }

}
