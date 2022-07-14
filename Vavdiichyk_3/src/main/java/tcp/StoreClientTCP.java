package tcp;

import genereteMessage.PacketGenerator;
import packet.Receiver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class StoreClientTCP {
    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;

    public void startConnection(int port) throws IOException {
        clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }

    public String send(int cType, int bUserId, String messBody) throws InterruptedException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {

        PacketGenerator pc = new PacketGenerator(messBody, cType, bUserId);

        byte[] outBytes = pc.getPacket();
        String returnValue = null;

        while (true) {
            try {
                out.write(outBytes);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                baos.write(buffer, 0 , in.read(buffer));
                byte result[] = baos.toByteArray();
                Receiver receiver = new Receiver(result);
                returnValue =  receiver.getDecryptedMessBody();
                return returnValue;
            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                    startConnection(3000);
                    return send( cType, bUserId, messBody);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}