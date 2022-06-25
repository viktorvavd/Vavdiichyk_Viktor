import java.nio.ByteBuffer;

public class Processor extends Thread {

    Message message;
    public Processor(Message message){
        this.message = message;
        this.run();
    }

    @Override
    public void run() {
        super.run();
        process(this.message);
    }

    private void process(Message message){
        byte one = 'O';
        byte two = 'K';
        ByteBuffer messBuff = ByteBuffer.wrap(message.getMessage());
        ByteBuffer feedbackBuffer = ByteBuffer.allocate(2).put(one).put(two);
        Message feedback = new Message(messBuff.getInt(0), messBuff.getInt(4), feedbackBuffer.array());
        Encriptor encriptor = new Encriptor(feedback);
    }
}
