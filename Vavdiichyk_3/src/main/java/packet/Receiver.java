package packet;

import genereteMessage.Message;

import javax.crypto.NoSuchPaddingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Receiver {

    private final byte bMagic = 0x13;

    private byte bSrc;
    private long bPktId;
    private int wLen;
    private short wCrc16;

    private boolean CRC16_isValid = true;
    private byte[] packet;
    private short wCrc16_final;
    private boolean CRC16_final_isValid = true;

    private Message message;

    private byte[] decryptedMessBody;

    private ByteBuffer buffer;

    public Receiver(byte[] command)  throws InterruptedException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (command[0] != bMagic) throw new IllegalArgumentException("first byte != 0x13");
        buffer = ByteBuffer.wrap(command);
        bSrc = buffer.get(1);
        bPktId = buffer.getLong(2);
        wLen = buffer.getInt(10);

        Thread crc1Thread = new Thread(() -> {
            wCrc16 = buffer.getShort(14);

            byte[] header = new byte[14];
            System.arraycopy(buffer.array(), 0, header,0,header.length);
            if (wCrc16 != CRC16.generateCrc16(header)) CRC16_isValid = false;
        });
        crc1Thread.start();

        Thread crc2Thread = new Thread(() -> {
            wCrc16_final = buffer.getShort(16 + wLen);
            byte[] bytesTo2Crc = new byte[wLen];
            System.arraycopy(buffer.array(), 16, bytesTo2Crc,0,bytesTo2Crc.length);
            if (wCrc16_final != CRC16.generateCrc16(bytesTo2Crc)) CRC16_final_isValid = false;
        });
        crc2Thread.start();

        crc1Thread.join();
        crc2Thread.join();

        if (!CRC16_isValid) throw new IllegalArgumentException("crc16_1 not valid");
        //if (!CRC16_final_isValid) throw new IllegalArgumentException("crc16_2 not valid");

        this.packet = command;
        receiveMessage();
    }

    private void receiveMessage(){
        Decriptor decriptor = new Decriptor(this.packet);
        decryptedMessBody = decriptor.getDecryptMessage().getBodyByteArr();
        message = decriptor.getDecryptMessage();
        //Processor processor = new Processor(decriptor.getDecryptMessage());
    }

    public String getDecryptedMessBody() {
        String result  = new String(decryptedMessBody, StandardCharsets.UTF_8);
        return result;
    }

    public Message getMessage() {
        return message;
    }
}
