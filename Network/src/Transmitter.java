import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Transmitter extends Thread{

    private boolean isRunning;
    private BufferedWriter writer;

    public Transmitter(boolean isRunning, BufferedWriter writer){
        this.isRunning = isRunning;
        this.writer = writer;
    }

    @Override
    public void run(){
            try (Scanner sc = new Scanner(System.in);){
                while (isRunning) {
                    String msg = sc.nextLine() + "\n";
                    writer.write(msg);
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

}
