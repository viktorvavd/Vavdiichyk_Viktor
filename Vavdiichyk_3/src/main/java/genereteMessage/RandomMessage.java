package genereteMessage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static packet.CRC16.generateCrc16;

public class RandomMessage {
    //private Random random;
    private SecretKeySpec secretKey =
            new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");

    private byte bMagic = 0x13;
    private byte bSrc;
    private long bPktId;
    private int wLen;
    private short wCrc16;
    private Message bMsg;
    private short wCrc16_end;

    public RandomMessage(int Type, int Src){
        //random = new Math.random();
        this.bSrc = (byte) Src;
        this.bPktId = (long) (Math.random()*(99)+1);

        if (Type == 1 || Type == 2) {
            this.wLen = 12;
        } else if (Type == 4) {
            int numberOfProducts = (int) (Math.random() * (49) + 1);
            this.wLen = numberOfProducts * 4 + 8;
        } else if (Type == 3) {
            this.wLen = 20;
        }else{
            throw new RuntimeException("Wrong genereteMessage.Message type");
        }

        ByteBuffer header = ByteBuffer.allocate(14)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId)
                .putInt(wLen);
        this.wCrc16 = generateCrc16(header.array());
        byte[] genMess = new byte[wLen-8];
        for(int i = 0; i < genMess.length; i++){
            genMess[i] = (byte) (Math.random()*(9)+1);
        }
        this.wCrc16_end = generateCrc16(genMess);
        try {
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] message = cipher.doFinal(genMess);
            //
            //System.out.println(Arrays.toString(genMess));
            //
            System.out.println(Arrays.toString(message));
            this.bMsg = new Message(Type, Src, message);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytesArr(){
       return ByteBuffer.allocate(18+bMsg.getLength())
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId)
                .putInt(wLen)
                .putShort(wCrc16)
                .put(bMsg.getMessByteArr())
                .putShort(wCrc16_end)
                .array();
    }
}
