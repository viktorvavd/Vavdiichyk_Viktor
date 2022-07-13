import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Encriptor extends Thread {

    private int commandID;
    private Storage storage;
    private Message message;

    private SecretKeySpec secretKey =
            new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");

    public Encriptor(Message message, Storage storage){
        this.storage = storage;
        this.message = message;
        this.commandID = message.getcType();
        this.run();
    }

    @Override
    public void run() {
        super.run();
        Sender sender;
        synchronized (storage) {
            try {
                while (commandID != storage.getState()) {
                    storage.wait(100L);
                    if(commandID != storage.getState()){
                        if(commandID == 1 || commandID == 2 || commandID == 3){
                            commandID++;
                        }else{
                            commandID = 1;
                        }
                    }
                }

                if (commandID == 1) {
                    sender = new Sender(encrypt(this.message));
                } else if (commandID == 2) {
                    sender = new Sender(encrypt(this.message));
                } else if (commandID == 3) {
                    sender = new Sender(encrypt(this.message));
                } else if(commandID == 4){
                    sender = new Sender(encrypt(this.message));
                }
                storage.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] encrypt(Message message) {
        try {
            byte[] answer = new byte[message.getLength() - 8];
            System.arraycopy(message.getMessage(), 8, answer, 0, answer.length);
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(answer);
            return ByteBuffer.allocate(result.length+8)
                    .putInt(message.getcType())
                    .putInt(message.getbUserId())
                    .put(result)
                    .array();
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
