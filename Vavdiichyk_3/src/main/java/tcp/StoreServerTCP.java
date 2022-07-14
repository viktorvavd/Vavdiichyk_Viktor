package tcp;


import java.io.*;
import java.net.ServerSocket;

public class StoreServerTCP extends Thread{

    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(3000);
        while (true)
            new EchoClientHandler(serverSocket.accept()).start();
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    @Override
    public void run() {
        StoreServerTCP server = new StoreServerTCP();
        try {
            server.start(3000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/*
    private static class EchoClientHandler extends Thread {
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
                out.write(pc.getPacketBytes());

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

 */
}