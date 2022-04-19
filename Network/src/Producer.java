
import java.util.concurrent.BlockingQueue;

public class Producer<T> extends Thread {

    private Boolean isRunning;
    private BlockingQueue<T> queue;
    private T item;

    public Producer (BlockingQueue<T> queue, T item){
        this.queue = queue;
        this.item = item;
    }

    public void run(){
        try  {
            this.queue.put(item);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
