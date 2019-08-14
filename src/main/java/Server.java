import com.google.gson.Gson;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;

import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class Server {

    static MockServerClient mock = startClientAndServer(8081);

    public static void consulta(String method, String path, int statusCode, String content, String body, long delay){
        mock.when(
                request()
                        .withMethod(method)
                        .withPath(path)
        ).respond(
                response()
                        .withStatusCode(statusCode)
                        .withHeader(new Header("Content-Type", content))
                        .withBody(body)
                        .withDelay(new Delay(TimeUnit.MILLISECONDS, delay))
        );
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        Site site = new Site("MLA","Argentina");
        Site site1 = new Site("MLC","Chile");
        Site site2 = new Site("MLU","Uruguay");

        Category cat = new Category("MLA5725","Accesorios para Vehículos");
        Category cat1 = new Category("MLA1403","Alimentos y Bebidas");
        Category cat2 = new Category("MLA1071","Animales y Mascotas");

        Site[] sites = new Site[]{site, site1, site2};
        Category[] categories = new Category[]{cat,cat1,cat2};

        consulta("GET", "/sites", 200, "application/json", gson.toJson(sites),1000);
        consulta("GET", "/sites/(.*)/categories", 200, "application/json", gson.toJson(categories),1000);

    }
}
