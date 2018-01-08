package euroexam;

import euroexam.spec.MessageLooper;
import euroexam.spec.MessageReader;
import euroexam.spec.MessageSender;
import java.util.Timer;
import java.util.TimerTask;

public class MyMessageLooper implements MessageLooper {

    MessageReader reader = null;
    Timer timer = null;
    MessageSender[] senders;
    //String msg = "középfokon"; //így lehet tesztelni, hogy működik-e
    String msg = null;
    long delay;

    public MyMessageLooper(long delay) {
        this.delay = delay;
    }
    
    @Override
    public void setReader(MessageReader reader) {
        this.reader = reader;
    }

    @Override
    public void setSender(MessageSender... senders) {
        //Ez egy tömb amibe akárhány paramétert meg lehet adni
        //pl: senders[1];
        this.senders = senders;
    }

    @Override
    public void setRunning(boolean running) {
        //csak akkor indítsam a timer-t ha minden be van állítva

        if (running && reader != null && senders != null) {
            //timer null akkor még egyszerse indult el)
            //running metódus a tryIcon StartStop checkbox állapotától függ

            TimerTask newtask = new TimerTask() {

                @Override
                public void run() {
                    String message, prevMsg;
                    
                    message = reader.readMessage();

                    prevMsg = msg;
                    if (message != null) {
                        msg = message;
                    }

                    if (msg != null && prevMsg != null && !prevMsg.equals(msg)) {

                        for (MessageSender messageSender : senders) {

                            messageSender.sendMessage("\nhttp://eredmeny.itk.hu/default.asp?id=20170100120001XQRU");

                        }
                    }
                }
            };
            timer = new Timer();

            timer.schedule(newtask, 0, delay);
        } else {
            timer.cancel();
        }
    }

}
