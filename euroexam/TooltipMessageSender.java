package euroexam;

import euroexam.spec.UserInterface;
import euroexam.spec.MessageSender;

public class TooltipMessageSender implements MessageSender {

    private final UserInterface UI;
    private final String TITLE;
    
    public TooltipMessageSender(UserInterface ui, String title) {
        this.UI = ui;
        this.TITLE = title;
    }

    @Override
    public void sendMessage(String message) {
        UI.showMessage(TITLE, message);
    }
    
}
