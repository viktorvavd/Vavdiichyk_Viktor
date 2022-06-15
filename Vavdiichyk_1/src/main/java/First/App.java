package First;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, SignatureException {
        Message message = new Message(1,8,new byte[]{1,2,3});
        byte id = 8;
        Packet packetBuilder = new Packet(id,message);
        byte[] packet = packetBuilder.encode();
        System.out.println(Arrays.toString(packet));

        Message decode = packetBuilder.decode(packet);
        System.out.println("decoded: " + decode.toString());
        //System.out.println( "Hello World!" );
    }
}
