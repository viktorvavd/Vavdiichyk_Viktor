package First;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Packet {
    private byte bMagic = 0x13;
    private byte bSrc;
    private long bPktId;
    private int wLen;
    private short wCrc16;
    private Message bMsg;
    private short wCrc16_end;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public byte getbMagic() {
        return bMagic;
    }

    public byte getbSrc() {
        return bSrc;
    }

    public long getbPktId() {
        return bPktId;
    }

    public int getwLen() {
        return wLen;
    }

    public short getwCrc16() {
        return wCrc16;
    }

    public Message getbMsg() {
        return bMsg;
    }

    public short getwCrc16_end() {
        return wCrc16_end;
    }

    public Packet(byte[] bytes) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        byte[] arr =  new byte[buffer.getInt(10)];
        System.arraycopy(buffer.array(),16,arr,0,arr.length);
        ByteBuffer messBuffer = ByteBuffer.wrap(arr);
        Message mess = new Message(messBuffer, arr.length);
        byte[] header = ByteBuffer.allocate(1+1+8+4)
                .put(buffer.get(0))
                .put(buffer.get(1))
                .putLong(buffer.getLong(2))
                .putInt(buffer.getInt(10))
                .array();
        if ((crc16(header) != buffer.getShort(14)) ||
        (crc16(mess.getMessage()) !=  buffer.getShort(16+buffer.getInt(10)))){
                Exception e = new Exception("Wrong CRC16");
                throw e;
        }
        this.bSrc = buffer.get(1);
        this.bPktId = buffer.getLong(2);
        this.wLen = buffer.getInt();
        this.wCrc16 = buffer.getShort();
        this.bMsg = mess;
        this.wCrc16_end = buffer.getShort();
    }

    public Packet(byte srcID, Message message){
        this.bSrc = srcID;
        this.bPktId = (long) message.hashCode();
        this.wLen = message.getLength();
        byte[] header = ByteBuffer.allocate(1+1+8+4)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId)
                .putInt(wLen)
                .array();
        this.wCrc16 = crc16(header);
        this.bMsg = message;
        this.wCrc16_end = crc16(message.getMessage());
    }

    public byte[] encode() throws JsonProcessingException {
        //byte[] message = objectMapper.writeValueAsBytes(bMsg);
        //return message;
       return ByteBuffer.allocate(16+bMsg.getLength()+8+2)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId)
                .putInt(wLen)
                .putShort(wCrc16)
                .put(bMsg.encodeMessage())
                .putShort(wCrc16_end)
                .array();
    }
    public Message decode(byte[] packet){
            ByteBuffer wrap = ByteBuffer.wrap(packet);

            //wrap.get();
        byte[] header = ByteBuffer.allocate(1+1+8+4)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId)
                .putInt(wLen)
                .array();
        if ((crc16(header) != wrap.getShort(14)) ||
                (crc16(bMsg.getMessage()) !=  wrap.getShort(16+wrap.getInt(10)))){
            Exception e = new Exception("Wrong CRC16");
            try {
                throw e;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
            int cType = wrap.getInt(16);
            int bUserId = wrap.getInt(20);
            byte[] messageBuffer = new byte[wLen-8];
            System.arraycopy(packet,24,messageBuffer,0,wLen-8);
            ByteBuffer buffer = ByteBuffer.allocate(bMsg.getmLength()+8);
        try {
            Cipher decodeCipher = Cipher.getInstance("AES");
            decodeCipher.init(Cipher.DECRYPT_MODE,bMsg.secretKey);
            byte[] decryptMessage = decodeCipher.doFinal(messageBuffer);
            buffer.putInt(cType).putInt(bUserId).put(decryptMessage);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        System.out.println("Type: " + cType + ", UserId: " + bUserId);
// validate crc16
        System.out.println(Arrays.toString(buffer.array()));
            return new Message(buffer,bMsg.getmLength()+8);
    }
    private static short crc16(byte[] bytes) {
        int[] table = {
                0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
                0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
                0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
                0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
                0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
                0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
                0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
                0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
                0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
                0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
                0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
                0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
                0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
                0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
                0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
                0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
                0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
                0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
                0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
                0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
                0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
                0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
                0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
                0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
                0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
                0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
                0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
                0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
                0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
                0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
                0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
                0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,
        };

        int crc = 0x0000;
        for (byte b : bytes) {
            crc = (crc >>> 8) ^ table[(crc ^ b) & 0xff];
        }

        return (short) crc;

    }

}
