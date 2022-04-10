public class ServerTest {

    public static void main(String[] args) {
        Host host = new Host();
        host.createMeeting();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // host.broadcast("Hallo\n");
    }
}
