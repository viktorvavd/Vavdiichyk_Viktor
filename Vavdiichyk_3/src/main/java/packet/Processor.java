package packet;

import genereteMessage.Message;
import repository.Storage;

import java.nio.ByteBuffer;

public class Processor{

    public static String getFeedback() {
        String str = new String(feedback);
        return str;
    }

    private static byte[] feedback;

    private static Storage storage = new Storage();

    public Processor(Message message){
        try {
            process(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Sender sender = new Sender(feedback);
    }
    public Processor(){}

    public static void process(Message message) throws InterruptedException {
        Thread doCommand = new Thread(() -> {
            int id = message.getcType();
            switch (id) {
                case 1:
                    storage.command1();
                    break;
                case 2:
                    storage.command2();
                    break;
                case 3:
                    storage.command3();
                    break;
                case 4:
                    storage.command4();
                    break;
            }
        });
        doCommand.start();

        Thread create_and_send_Answer = new Thread(() -> {
            byte one = 'O';
            byte two = 'K';
            ByteBuffer feedbackBuffer = ByteBuffer.allocate(2).put(one).put(two);
            Message answer = new Message(message.getcType(), message.getbUserId(), feedbackBuffer.array());
            feedback = answer.getBodyByteArr();
           // feedback = Encriptor.encrypt(answer);
        });
        create_and_send_Answer.start();

        doCommand.join();
        create_and_send_Answer.join();
    }
}
