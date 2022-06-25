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

    private Message message;

    private SecretKeySpec secretKey =
            new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");

    public Encriptor(Message message){
        this.message = message;
        this.run();
    }

    @Override
    public void run() {
        super.run();
        Sender sender = new Sender(encrypt(this.message));
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
