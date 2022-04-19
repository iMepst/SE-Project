import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Host extends User {

    public static int id = 1;

    private List<Guest> guests;
    private ServerSocket server;

    private BlockingQueue<String> messages;
    private BlockingQueue<Socket> connections;

    private boolean isRunning;

    Host () {
        super("Host", "localhost", 50005);
        guests = new LinkedList<>();
        messages = new LinkedBlockingQueue<>();
        connections = new LinkedBlockingQueue<>();
        isRunning = true;
    }

    public void createMeeting() {

        try (ServerSocket server = new ServerSocket(this.port)){
            System.out.println("Server listening on port " + this.port);
            this.server = server;
            Thread connectionConsumer = new Thread(createConnectionConsumer());
            Thread connectionProducer = new Thread(createConnectionProducer());
            Thread messageConsumer = new Thread(createMessageConsumer());

            connectionProducer.start();
            connectionConsumer.start();
            messageConsumer.start();

            connectionConsumer.join();
            connectionProducer.join();
            messageConsumer.join();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Runnable createConnectionConsumer(){
        return new Runnable(){
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        Guest guest = new Guest(connections.take(), "Gast" + id++);
                        new Thread(createMessageProducer(guest)).start();
                        guests.add(guest);
                        System.out.println(guest.getName() + " has joined");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public Runnable createConnectionProducer(){
        return new Runnable(){
            @Override
            public void run(){
                while (isRunning) {
                    try {
                        Socket client = server.accept();
                        System.out.println("Incomming connection from " + client.getRemoteSocketAddress());
                        connections.put(client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }


    public Runnable createMessageProducer (Guest guest) {
        return new Runnable(){
            @Override
            public void run(){
                while (isRunning) {
                    try {
                        BufferedReader reader = guest.getReader();
                        String msg = reader.readLine();
                        msg = "[" + guest.getName() + "] " + msg;
                        messages.put(msg);
                        System.out.println(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    public Runnable createMessageConsumer () {
        return new Runnable(){
            @Override
            public void run(){
                while (isRunning){
                    try {
                        String msg = messages.take();
                        for (Guest guest : guests) {
                            guest.getWriter().println(msg);
                            guest.getWriter().flush();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    

}
