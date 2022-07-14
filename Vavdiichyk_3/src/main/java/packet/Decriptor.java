package packet;

import genereteMessage.Message;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;

public class Decriptor {

    private static SecretKeySpec secretKey =
            new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");

    private Message command;
    private byte[] decryptBody;
    private int cType;
    private int bUserId;
    private int wLen;

    public Message getDecryptMessage() {
        return command;
    }


    public Decriptor(byte[] packet) {
        ByteBuffer buffer = ByteBuffer.wrap(packet);
        wLen = buffer.getInt(10);
        try {
            decript(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        command = new Message(cType,bUserId,decryptBody);
    }


    public void decript(byte[] packet) throws Exception {
        Cipher decodeCipher = Cipher.getInstance("AES");
        decodeCipher.init(Cipher.DECRYPT_MODE,secretKey);
        ByteBuffer buffer = ByteBuffer.wrap(packet);

        Thread setParams = new Thread(() ->{
            cType = buffer.getInt(16);
            bUserId = buffer.getInt(20);
        });
        setParams.start();

        Thread decrypt = new Thread(() -> {
            Message message = new Message(buffer, wLen);

            byte[] decryptMessage = new byte[message.getLength() - 8];
            try {
                byte[] ff = message.getBodyByteArr();
                decryptMessage = decodeCipher.doFinal(message.getBodyByteArr());
                this.decryptBody = decryptMessage;
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            this.decryptBody = decryptMessage;
        });
        decrypt.start();

        setParams.join();
        decrypt.join();
    }
}

