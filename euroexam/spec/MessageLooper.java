package euroexam.spec;

public interface MessageLooper {
    
    public void setReader(MessageReader reader);
    
    public void setSender(MessageSender... senders);
    
    public void setRunning(boolean running);
    
}
