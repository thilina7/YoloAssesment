import com.mock.common.JsonConfReader;
import com.mock.pojo.BackendConf;
import com.mock.services.*;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.handler.BodyHandler;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        io.vertx.ext.web.Router router = io.vertx.ext.web.Router.router(vertx);
        router.route().handler(BodyHandler.create());
        JsonConfReader confReader = new JsonConfReader();
        // you can change the port here
        httpServer.requestHandler(router::accept).listen(confReader.getJsonConfObject().getServicePort());
         // you can put your constructor here
        CreateUserService ms = new CreateUserService(router, confReader);
        DepositUserService ds = new DepositUserService(router, confReader);
        WithdrawUserService ws = new WithdrawUserService(router, confReader);
        SendTransaction st = new SendTransaction(router, confReader);
        GetBalance gb = new GetBalance(router);

    }


}
