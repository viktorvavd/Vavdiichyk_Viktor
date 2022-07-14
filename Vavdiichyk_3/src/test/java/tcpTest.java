import org.junit.Before;
import org.junit.Test;
import tcp.StoreClientTCP;
import tcp.StoreServerTCP;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;

public class tcpTest {
    @Before
    public void setup() {
        new StoreServerTCP().start();
    }

    @Test
    public void correctRespondFromServer() throws IOException, InterruptedException {
        Thread client1 = new Thread(() -> {
            try {
                StoreClientTCP client = new StoreClientTCP();
                String testStr = "product + 1";
                int cType = 1;
                client.startConnection(3000);
                String answer = client.send( cType, 23, testStr);
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
        });
        client1.start();

        Thread client2 = new Thread(() -> {
            try {
                StoreClientTCP client = new StoreClientTCP();
                String testStr = "product - 1";
                int cType = 2;
                client.startConnection(3000);
                String answer = client.send(cType, 23, testStr);
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
        });
        client2.start();

        Thread client3 = new Thread(() -> {
            try {
                StoreClientTCP client = new StoreClientTCP();
                String testStr = "product + 2";
                int cType = 3;
                client.startConnection(3000);
                String answer = client.send(cType, 23, testStr);
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
        });
        client3.start();

        Thread client4 = new Thread(() -> {
            try {
                StoreClientTCP client = new StoreClientTCP();
                String testStr = "product + 3";
                int cType = 4;
                client.startConnection(3000);
                String answer = client.send(cType, 23, testStr);
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
        });
        client4.start();

        client1.join();
        client2.join();
        client3.join();
        client4.join();

    }
}
