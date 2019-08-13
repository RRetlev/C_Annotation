package firstcustom;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            Class<Routes> routesClass = Routes.class;
            String response = "This is the response";

            for (Method m: Routes.class.getMethods()){
                if(m.isAnnotationPresent(Webroute.class)){
                    Annotation annotation = m.getAnnotation(Webroute.class);
                    Webroute webroute = (Webroute) annotation;
                    String path = t.getRequestURI().getPath();
                    System.out.println(path);
                    System.out.println(webroute.path());
                    if (webroute.path().equals(getFirstSegmentOfURI(path))){
                        try{
                            System.out.println("before invoke");
                             response = (String) m.invoke(null);
                            System.out.println("alma");
                        }catch (IllegalAccessException | InvocationTargetException e){
                            e.printStackTrace();
                        }
                    }
                }
            }





            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }


    private String getFirstSegmentOfURI(String path) {
        String[] segments = path.split("/");
        return segments.length == 0 ? "/" : "/" + segments[1];
    }}
}