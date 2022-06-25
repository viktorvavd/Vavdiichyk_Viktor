import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Sender {
    private SecretKeySpec secretKey =
            new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");

    public Sender(byte[] mess){
        System.out.println("Encrypted: " + Arrays.toString(mess));
        System.out.println("/////////////////////////");

        byte[] encryptMess = new byte[mess.length-8];
        System.arraycopy(mess,8,encryptMess,0,encryptMess.length);
        ByteBuffer buffer = ByteBuffer.wrap(mess);
        try {
            Cipher decodeCipher = Cipher.getInstance("AES");
            decodeCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptMessage = decodeCipher.doFinal(encryptMess);
            System.out.println("Decrypted: " + "Type: " + buffer.getInt(0) +
                    ", Id:" + buffer.getInt(4)
                    + " " + Arrays.toString(decryptMessage));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
