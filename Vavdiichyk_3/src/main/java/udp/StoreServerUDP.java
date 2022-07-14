package udp;

import genereteMessage.PacketGenerator;
import packet.Processor;
import packet.Receiver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class StoreServerUDP extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[1024];

    public StoreServerUDP() throws SocketException {
        socket = new DatagramSocket(3555);
    }

    public void run() {
        running = true;
        try {
            while (true) {
                buf = new byte[256];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                Receiver receiver = new Receiver(packet.getData());

                Processor processor = new Processor(receiver.getMessage());
                String response = Processor.getFeedback();

                PacketGenerator pc = new PacketGenerator(response, 0, 3555);
                packet = new DatagramPacket(pc.getPacket(), pc.getPacket().length, address, port);
                socket.send(packet);
                //socket.close();
            }

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
