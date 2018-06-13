import java.net.*;
import java.io.*;

public class Client
{

String hostName = args[0];
int portNumber = Integer.parseInt(args[1]);

public Client(String address, int port)
try {
    Socket echoSocket = new Socket(address, port);
    PrintWriter out =
        new PrintWriter(echoSocket.getOutputStream(), true);
    BufferedReader in =
        new BufferedReader(
            new InputStreamReader(echoSocket.getInputStream()));
    BufferedReader stdIn =
        new BufferedReader(
            new InputStreamReader(System.in))
}
    catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    
    String userInput;
while ((userInput = stdIn.readLine()) != null) {
    out.println(userInput);
    System.out.println("echo: " + in.readLine());
}
    
}
    
}
    