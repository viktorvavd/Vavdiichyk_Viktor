package First;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class Message {
    private int cType;
    private int bUserId;
    private byte[] message;

    public Message(ByteBuffer buffer, int wLen){
        this.cType = buffer.getInt(0);
        this.bUserId = buffer.getInt(4);
        this.message = new byte[wLen - Integer.BYTES * 2];
        buffer.get(this.message, Integer.BYTES*2, wLen);
    }

    public Message(int cType, int bUserId, byte[] message){
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public Message(){}
    public int getLength(){
        return message.length+8;
    }

    public byte[] getMessage() {
        return message;
    }

    public byte[] encodeMessage(){
        return ByteBuffer.allocate(Integer.BYTES*2+message.length)
                .putInt(cType)
                .putInt(bUserId)
                .put(message)
                .array();
    }

    @Override
    public String toString() {
        return "Message{" +
                "cType=" + cType +
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
}
