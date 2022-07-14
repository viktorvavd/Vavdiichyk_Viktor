import org.junit.Before;
import org.junit.Test;
import udp.StoreClientUDP;
import udp.StoreServerUDP;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;

public class UDPTest {

    @Before
    public void setup() throws IOException {
        new StoreServerUDP().start();
    }

    @Test
    public void correctRespondFromServer() throws IOException, InterruptedException {

        Thread cl = new Thread(() -> {
            String answer = null;
            try {
                StoreClientUDP client = new StoreClientUDP();
                String testStr = "product + 1";
                int cType = 1;
                answer = client.send(testStr ,cType, 23);
                System.out.println(answer);
                boolean bool = answer.contains("OK");
                assertTrue(answer.contains("OK"));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            assertTrue(answer.contains("OK"));
        });
        cl.start();

        Thread cl2 = new Thread(() -> {
            String answer = null;
            try {
                StoreClientUDP client = new StoreClientUDP();
                String testStr = "product - 1";
                int cType = 2;
                answer = client.send(testStr ,cType, 23);
                System.out.println(answer);
                boolean bool = answer.contains("OK");
                assertTrue(answer.contains("OK"));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            assertTrue(answer.contains("OK"));
        });
        cl2.start();


        Thread cl3 = new Thread(() -> {
            String answer = null;
            try {
                StoreClientUDP client = new StoreClientUDP();
                String testStr = "product + 3";
                int cType = 3;
                answer = client.send(testStr ,cType, 23);
                System.out.println(answer);
                boolean bool = answer.contains("OK");
                assertTrue(answer.contains("OK"));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            assertTrue(answer.contains("OK"));
        });
        cl3.start();

        Thread cl4 = new Thread(() -> {
            String answer = null;
            try {
                StoreClientUDP client = new StoreClientUDP();
                String testStr = "product + 5";
                int cType = 4;
                answer = client.send(testStr ,cType, 23);
                System.out.println(answer);
                boolean bool = answer.contains("OK");
                assertTrue(answer.contains("OK"));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            assertTrue(answer.contains("OK"));
        });
        cl4.start();

        cl.join();
        cl2.join();
        cl3.join();
        cl4.join();

    }

}