import java.nio.ByteBuffer;

public class Receiver {
    private byte[] command;
    private Storage storage;

    public Receiver(byte[] command, Storage storage){
        this.storage = storage;
        this.command = command;
        receiveMessage();
    }

    private void receiveMessage(){
        Decriptor decriptor = new Decriptor(command,storage);
    }
}
