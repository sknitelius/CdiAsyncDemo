package science.raketen.cdiasyncdemo.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;

public class JaxRsTest {
  @Rule
  public JAXRSClientProvider provider = JAXRSClientProvider
      .buildWithURI("http://cdiasyncdemo-dev.eu-gb.mybluemix.net/rest/hello");

  @Test
  public void testHello() {
    WebTarget target = provider.target();
    Response response = target.queryParam("name", "World").request().get();
    assertThat(response.getStatus(), is(200));
    assertThat(response.readEntity(String.class), containsString("Hello World!"));
  }

}
