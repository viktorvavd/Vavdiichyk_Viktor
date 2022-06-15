package First;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

class AppTest {
    @Test
    void shouldHandlePackage() throws Exception {
        Message message = new Message(1,8,new byte[]{1,2,3});
        byte id = 8;
        Packet packetBuilder = new Packet(id,message);
        byte[] packet = packetBuilder.encode();
        Packet packetTest = new Packet(packet);

        Message decode = packetBuilder.decode(packet);
        System.out.println("decoded: " + decode.toString());
    }

    @Test
    void shouldHandleCryptography() throws Exception{
        byte[] byteArr = {1,2,3};
        Message message = new Message(1,8,byteArr);
        byte id = 8;
        Packet packetBuilder = new Packet(id,message);
        byte[] packet = packetBuilder.encode();
        Packet packetTest = new Packet(packet);

        Message decode = packetBuilder.decode(packet);

        byte[] sentMessage  = ByteBuffer.allocate(byteArr.length+8).
                putInt(1)
                .putInt(8)
                .put(byteArr).array();
        byte[] decodedMessage = decode.getMessage();
         for(int i = 0; i< decodedMessage.length ; i++){
             if(decodedMessage[i] != sentMessage[i]){
                 throw new Exception("Wrong decryption");
             }
         }

    }
}