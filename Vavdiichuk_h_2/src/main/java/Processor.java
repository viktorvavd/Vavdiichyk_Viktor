import java.nio.ByteBuffer;

public class Processor extends Thread {

     private Message message;

    private Storage storage = new Storage();
    private int commandID;

    public Processor(Message message ,Storage storage){
        this.commandID = message.getcType();
        this.storage = storage;
        this.message = message;
        this.start();
    }
    public Processor(){}

    @Override
    public void run() {
        super.run();
        synchronized (storage) {
            try {
                while (commandID != storage.getState()) {
                    storage.wait(100L);
                    if(commandID != storage.getState()){
                        if(commandID == 1 || commandID == 2 || commandID == 3){
                            commandID++;
                        }else{
                            commandID = 1;
                        }
                    }
                }
                if (commandID == 1) {
                    storage.command1();
                } else if (commandID == 2) {
                   storage.command2();
                } else if (commandID == 3) {
                    storage.command3();
                } else if (commandID == 4)  {
                    storage.command4();
                }
                storage.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void process(Message message, Storage storage){
        int id = message.getcType();
        Processor processor = new Processor(message,storage);
        byte one = 'O';
        byte two = 'K';
        ByteBuffer messBuff = ByteBuffer.wrap(message.getMessage());
        ByteBuffer feedbackBuffer = ByteBuffer.allocate(2).put(one).put(two);
        Message feedback = new Message(messBuff.getInt(0), messBuff.getInt(4), feedbackBuffer.array());
        Encriptor encriptor = new Encriptor(feedback, storage);
    }
}
