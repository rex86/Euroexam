package euroexam;

import config.ConfigMap;
import config.Folders;
import euroexam.spec.Config;
import euroexam.spec.UserInterface;
import euroexam.spec.MessageLooper;
import euroexam.spec.MessageReader;
import euroexam.spec.MessageSender;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


//Az euroexam oldalát bizonyos időköznként lekéri és ha változás van egy bizonyos tegben akkor e-mailt küld és/vagy tooltipben jelzi.
//Vizsgaeredmény kihírdetés értesítő.
public class Main {

    private static final File CFG_FILE = Folders.getFile("euroexam.cfg");
    
    public static void main(String[] args) {
            
            Config c = new FileConfig(CFG_FILE);
            if (!c.isValid()) {
                System.err.println(CFG_FILE.getAbsolutePath() + " file is invalid!");
                System.exit(1);
            }
            
            //Timer ami bizonyos időként lekéri a html oldalt.
            //Ha változott akkor jelzi a küldőnek
            MessageLooper looper = new MyMessageLooper(c.getDelay());
            UserInterface ui = new SystemTrayUserInterface(looper);
            
            MessageReader reader = new EuroexamMessageReader(c.getDateMatch());
            
            MessageSender tooltipSender = new TooltipMessageSender(ui, c.getSubject());
            MessageSender emailSender = new EmailMessageSender(c.getSmtpHost(), c.getSmtpPort(), c.isSmtpSsl(), c.getSmtpUser(), c.getSmtpPassword(), c.getEmailFrom(), c.getEmailTo(), c.getSubject());
            
            looper.setReader(reader);
            looper.setSender(emailSender,tooltipSender);
            
            
    }
    
}
