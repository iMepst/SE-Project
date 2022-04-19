import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Server {

    public static int id = 1;

    private String host = "localhost";
    private int port = 50005;

    //private List<Guest> guests;
    private ServerSocket ssocket;
    private BlockingQueue<String> messages;
    private LinkedList<Socket> csockets;

    private ConnectionHandler ch;
    private MessageHandler mh;

    private Boolean isRunning;

    public void start(){

        isRunning = true;
       // guests = new LinkedList<>();
        messages = new LinkedBlockingQueue<>();

        try (ServerSocket ssocket = new ServerSocket(port)) {
            this.ssocket = ssocket;
            ch = new ConnectionHandler(isRunning, ssocket, csockets);
            mh = new MessageHandler(isRunning, ssocket, messages);

            ch.start();
            mh.start();

            ch.join();
            mh.join();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        isRunning = false;
    }

}
