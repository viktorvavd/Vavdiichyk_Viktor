package udp;


import genereteMessage.PacketGenerator;
import packet.Receiver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class StoreClientUDP {

    private static int count = 1;

    private int id;

    private int lostPackets = 0;
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf = new byte[1024];

    public StoreClientUDP() throws SocketException, UnknownHostException {
        id = count++;
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String send(String mess, int cType, int bUserId) throws IOException, InterruptedException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {

        PacketGenerator pc = new PacketGenerator(mess, cType, bUserId);

        byte[] msgArr = pc.getPacket();
        DatagramPacket packet
                = new DatagramPacket(msgArr, msgArr.length, address, 3555);
        socket.send(packet);

        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = null;

        Receiver packetReceiver = new Receiver(packet.getData());
        received = packetReceiver.getDecryptedMessBody();
        if (received.equals("")) {
            if (lostPackets == 5) return "Connection lost";
            lostPackets++;
            return send(mess, cType, bUserId);
        }

        return received;
}

    public void close() {
        socket.close();
    }

}
