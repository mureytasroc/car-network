package test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class MyTest {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(1201), 0);
        server.createContext("/VarghaHokmran", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            
            OutputStream os = t.getResponseBody();
            // extracting barcode from url
            String myVar = t.getRequestURI().toString().substring(14, 18);
            //String myVar = t.getRequestURI().toString();
            System.out.println("My Variable: " + myVar);
            String response = "{myData: \"myText\":\"Like\",\"otherText\":\"Subscribe\"}";
            t.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            os.close();
        }
    }

}