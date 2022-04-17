import java.io.*;
import java.net.Socket;

public class Client {

    String host = "localhost";
    int port = 50005;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Thread receiver;
    private Thread transmitter;

    private boolean isRunning;

    public void start(){
        isRunning = true;
        try (Socket socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(socket.getOutputStream())));
            ){
            this.socket = socket;
            this.reader = reader;
            this.writer = writer;

            receiver = new Receiver(isRunning, reader);
            transmitter  = new Transmitter(isRunning, writer);

            receiver.start();
            transmitter.start();

            receiver.join();
            transmitter.join();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void stop(){
        transmitter.interrupt();
        receiver.interrupt();

        try {
            writer.close();
            reader.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
