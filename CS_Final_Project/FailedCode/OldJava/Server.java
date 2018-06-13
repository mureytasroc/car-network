import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args){
        ServerSocket server = new ServerSocket(5000);
        
        

        System.out.println("Server has started on 192.168.1.148:5000.\r\nWaiting for a connection...");

        Socket client = server.accept();
        
        InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream();
        
        System.out.println("A client connected.");
        
        
        BufferedReader inStream = new BufferedReader(new 
InputStreamReader(in));
String line;
while(inStream.ready() && (line = inStream.readLine()) != null) {
    System.out.println(line);
}
        
        
        
    }
}