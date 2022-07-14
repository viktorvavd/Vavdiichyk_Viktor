package tcp;

import genereteMessage.PacketGenerator;
import packet.Processor;
import packet.Receiver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EchoClientHandler extends Thread {
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;

    private Receiver receiver;

    public EchoClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            baos.write(buffer, 0 , in.read(buffer));
            byte result[] = baos.toByteArray();
            receiver = new Receiver(result);
            Processor processor = new Processor(receiver.getMessage());
            String response = Processor.getFeedback();
            PacketGenerator pc = new PacketGenerator(response, 0, 3000);
            out.write(pc.getPacket());

        } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }
}
