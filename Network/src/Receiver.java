import java.io.BufferedReader;
import java.io.IOException;

public class Receiver extends Thread {

    private boolean isRunning;
    private BufferedReader reader;

    public Receiver(boolean isRunning, BufferedReader reader){
        this.isRunning = isRunning;
        this.reader = reader;
    }

    @Override
    public void run(){

        while (isRunning){
            try {
                String msg = reader.readLine();
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
