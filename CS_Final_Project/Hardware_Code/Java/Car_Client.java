import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Car_Client extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;

    private static final int serverPort = 37899;

    private ServerSocket server;
    private Socket connection;
    private BufferedWriter output;
    private BufferedReader input;

    private String message = "";
    
    public static void main(String[] args){
        new Car_Client();
    }

    public Car_Client() {

        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(400, 300);
        setVisible(true);

    }

    public void startRunning() {
        try {

            server = new ServerSocket(serverPort, 100);
            while (true) {
                try {
                    waitForConnection();
                    setupStreams();
                    whileConnected();
                } catch (EOFException eofException) {
                    showMessage("Client terminated connection");
                } catch (IOException ioException) {
                    showMessage("Could not connect...");
                } finally {
                    closeStreams();
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void waitForConnection() throws IOException {

        showMessage("Waiting for someone to connect...");
        connection = server.accept(); //once someone asks to connect, it accepts the connection to the socket this gets repeated fast
        showMessage("Now connected to " + connection.getInetAddress().getHostName()); //shows IP adress of client

    }

    private void setupStreams() throws IOException {

        showMessage("creating streams...");
        output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        output.flush();
        input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        showMessage("Streams are setup!");

    }

    private void whileConnected() throws IOException {

        ableToType(true); //makes the user able to type

        do {

            char x = (char) input.read();
            while (x != '\n') {
                message += x;
                x = (char) input.read();
            }
            showMessage(message);
            message = "";

        } while (!message.equals("END")); //if the user has not disconnected, by sending "END"

    }

    private void closeStreams() {

        ableToType(false);

        showMessage("Closing streams...");
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            output.write(message + '\n');
            output.flush();
            showMessage("Sent: " + message);
        } catch (IOException ex) {
            chatWindow.append("\nSomething messed up whilst sending messages...");
        }

    }

    private void showMessage(final String message) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatWindow.append('\n' + message);
                    }
                }
        );

    }

    private void ableToType(final boolean tof) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }

}