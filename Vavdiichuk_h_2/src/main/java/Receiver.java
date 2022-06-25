import java.nio.ByteBuffer;

public class Receiver {
    private byte[] command;

    public Receiver(byte[] command){
        this.command = command;
        receiveMessage();
    }

    private void receiveMessage(){
        Decriptor decriptor = new Decriptor(command);
    }
}
