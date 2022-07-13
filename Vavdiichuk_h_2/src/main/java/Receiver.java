import java.nio.ByteBuffer;

public class Receiver {
    private byte[] command;
    private Storage storage;

    public Receiver(byte[] command, Storage storage){
        this.command = command;
        this.storage = storage;
        receiveMessage();
    }

    private void receiveMessage(){
        Decriptor decriptor = new Decriptor(command,storage);
    }
}
