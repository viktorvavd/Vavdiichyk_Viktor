package genereteMessage;

import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class Message {
    private int cType = -1;
    private int bUserId;
    private byte[] message;
    private int mLength;
    private int encryptLen;

    private SecretKeySpec secretKey =
            new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");

    public Message(ByteBuffer buffer, int wLen) {
        this.mLength = wLen;
        this.cType = buffer.getInt(16);
        this.bUserId = buffer.getInt(20);
        this.message = new byte[wLen - 8];
        System.arraycopy(buffer.array(), 24, this.message, 0, wLen - 8);
    }

    public Message(int cType, int bUserId, byte[] message) {
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
        this.mLength = this.message.length + 8;
    }

    public Message(byte[] message) {
        ByteBuffer buffer = ByteBuffer.wrap(message);
        this.cType = buffer.getInt(0);
        this.bUserId = buffer.getInt(4);
        System.arraycopy(message, 8, this.message, 0, message.length-8);
        this.mLength = this.message.length + 8;
    }


    public Message() {
    }

    public int getLength() {
        return mLength;
    }

    public void setcType(int cType) {
        this.cType = cType;
    }

    public void setbUserId(int bUserId) {
        this.bUserId = bUserId;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public void setmLength(int mLength) {
        this.mLength = mLength;
    }

    public void setSecretKey(SecretKeySpec secretKey) {
        this.secretKey = secretKey;
    }

    public byte[] getMessByteArr() {
        return ByteBuffer.allocate(message.length + 8)
                .putInt(cType)
                .putInt(bUserId)
                .put(message)
                .array();
    }

    public byte[] getBodyByteArr() {
        return this.message;
    }


    @Override
    public String toString() {
        return "genereteMessage.Message{" +
                "Length=" + mLength +
                ", cType=" + cType +
                ", bUserId=" + bUserId +
                ", message=" + Arrays.toString(message) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return cType == message1.cType && bUserId == message1.bUserId && Arrays.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(cType, bUserId);
        result = 31 * result + Arrays.hashCode(message);
        return result;
    }

    public int getbUserId() {
        return bUserId;
    }

    public int getcType() {
        return cType;
    }

    public int getEncryptLen() {
        return encryptLen;
    }
}
