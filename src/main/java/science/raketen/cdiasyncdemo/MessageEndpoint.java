package science.raketen.cdiasyncdemo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Singleton;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Singleton
@ServerEndpoint("/messageEndpoint")
public class MessageEndpoint implements Serializable {
  private final Set<Session> sessions = new HashSet<>();

  public void pushMessage(String msg) {
    for (Session session : sessions) {
      session.getAsyncRemote().sendText(msg);
    }
  }

  @OnOpen
  public void onOpen(Session session) {
    this.sessions.add(session);
  }

}
