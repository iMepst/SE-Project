import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionHandler extends Thread {

    private Boolean isRunning;
    private ServerSocket ssocket;
    private LinkedList<Socket> csockets;
    private BlockingQueue<Socket> connections;
    private Thread consumer;
    private Thread producer;

    public ConnectionHandler(Boolean isRunning, ServerSocket ssocket, LinkedList<Socket> csockets){
        this.isRunning = isRunning;
        this.ssocket = ssocket;
        this.csockets = csockets;

        this.connections = new LinkedBlockingQueue<>();
        this.producer = new Thread(){
            @Override
            public void run(){
                while (isRunning){
                    try {
                        Socket csocket = ssocket.accept();
                        csockets.add(csocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.consumer = new Thread(){
            @Override
            public void run(){
                while (isRunning){
                    try {
                        Socket csocket = connections.take();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(csocket.getOutputStream()));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };



    }

    @Override
    public void run(){

    }

}
