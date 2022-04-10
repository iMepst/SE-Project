import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Guest extends User {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public Guest (String name) {
        super(name, "localhost", 50005);
    }

    public Guest (Socket socket, String name) {
        this(name);
        this.socket = socket;
        try {
            this.writer = new PrintWriter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinMeeting() {
        try (Socket socket = new Socket(ip, port)) {
            this.socket = socket;
            System.out.println("Connected to " + this.ip);

            Thread receiver = new Thread(createReceiver(this.socket));
            Thread transmitter = new Thread(createTransmitter(this.socket));

            receiver.start();
            System.out.println("Receiver started");

            transmitter.start();
            System.out.println("Transmitter started");

            receiver.join();
            transmitter.join();

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {}

    public Runnable createReceiver(Socket socket) {

        return new Runnable() {
            @Override
            public void run() {
                try(BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                    String msg = "";
                    do {
                        if (input.ready()) {
                            msg = input.readLine();
                            System.out.println(msg);
                        }
                    }
                    while(! msg.endsWith(":quit"));
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Runnable createTransmitter(Socket socket) {
        return new Runnable() {
            @Override
            public void run() {
                try(PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    Scanner sc = new Scanner(System.in)
                ) {
                    String msg;
                    do{
                        msg = sc.nextLine();
                        output.println("[" + name + "] " + msg );
                    }
                    while(! msg.equals(":quit"));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getWriter(){
        return this.writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
