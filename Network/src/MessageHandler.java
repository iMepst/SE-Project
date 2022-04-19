import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;

public class MessageHandler extends Thread{

    private Boolean isRunning;
    private ServerSocket ssocket;
    private BlockingQueue<String> messages;

    public MessageHandler (Boolean isRunning, ServerSocket ssocket, BlockingQueue<String> messages){
        this.isRunning = isRunning;
        this.ssocket = ssocket;
        this.messages = messages;
    }

    @Override
    public void run(){

    }
}
