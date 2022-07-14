package genereteMessage;

import packet.CRC16;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PacketGenerator {

    private static final SecretKey key =  new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");

    private static long id;

    private byte[] encryptMessage;
    private int wLen;
    private ByteBuffer byteBuffer;

    private int cType;
    private int bUserId;

    private byte[] packet;

    public PacketGenerator(String str, int cType, int bUserId) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InterruptedException {
        generatePacket(str, cType, bUserId);
    }

    public static SecretKey getKey() {
        return key;
    }



    private void generatePacket(String str, int cType, int bUserId) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InterruptedException {

        this.cType = cType;
        this.bUserId = bUserId;

        byte[] messageBytes = str.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        encryptMessage = cipher.doFinal(messageBytes);
        wLen = encryptMessage.length + 8;
        byteBuffer = ByteBuffer.allocate(18 + wLen);

        Thread threadFirst = new Thread(() ->{
            byte bMagic = 0x13;
            byteBuffer.put(0, bMagic);
            byte bSrc = 0x07;
            byteBuffer.put(1, bSrc);
            byteBuffer.putLong(2, id++);
            byteBuffer.putInt(10, wLen);

            byte[] firstPart = new byte[14];
            byteBuffer.get(0, firstPart);
            short wCrc16_1 = CRC16.generateCrc16(firstPart);

            byteBuffer.putShort(14, wCrc16_1);
        });
        threadFirst.start();

        Thread threadSecond= new Thread(() ->{
            byteBuffer.putInt(16, cType);
            byteBuffer.putInt(20, bUserId);
            byteBuffer.put(24, encryptMessage);
            byte[] secondPart = new byte[wLen];
            byteBuffer.get(16, secondPart);
            short wCrc16_2 = CRC16.generateCrc16(secondPart);
            byteBuffer.putShort(16 + wLen, wCrc16_2);
        });
        threadSecond.start();

        threadFirst.join();
        threadSecond.join();

        packet = byteBuffer.array();
    }

    public byte[] getPacket() {
        return packet;
    }

}