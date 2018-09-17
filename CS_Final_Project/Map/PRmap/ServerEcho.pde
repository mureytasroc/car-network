import java.awt.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerEcho {
    
    private ServerSocket ss; 
    private Socket x;
    private PrintWriter output;
    private Scanner input;
    private String msg1;
    
    ServerEcho(int port) throws UnknownHostException, IOException {
        
        ss = new ServerSocket(port);
        x = ss.accept();
        System.out.println("Connection ok \n");
        input = new Scanner(x.getInputStream());
        output = new PrintWriter(x.getOutputStream(), true);
    }
    
    String getIncoming() throws UnknownHostException, IOException {
          
        /*do {

            innie= input.nextLine(); 
            //System.out.println(innie);
            if(!innie.equals("end")){
            msg1+=innie;}
            //System.out.println("Sending back \n");
            //output.println(msg1);

        } while (!innie.equals("end"));*/
        
        //msg1.replace(end,"");
        if(this.available()){
        
        return input.nextLine();}
        else{
            return null;
        }
        
        /*
        if(input.hasNextLine()){
         innie=input.nextLine();}
        
        return innie;*/
    }
    
    void sendOutput(String out) throws UnknownHostException, IOException {
            //System.out.println("Sending "+out+"...");
            output.println(out);
    }
    
    void closeConnection() throws UnknownHostException, IOException {
        x.close();
        ss.close();
    }
    
    public boolean available(){
        return input.hasNextLine();
    }
        
    
}