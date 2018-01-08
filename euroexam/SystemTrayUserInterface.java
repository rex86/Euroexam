package euroexam;

import euroexam.spec.MessageLooper;
import euroexam.spec.UserInterface;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SystemTrayUserInterface implements UserInterface {

    private final MessageLooper LOOPER;
    TrayIcon trayIcon;

    public SystemTrayUserInterface(MessageLooper looper) {
        this.LOOPER = looper;
        initSystemTray();
    }

    @Override
    public void onStartStop(boolean start) {
        LOOPER.setRunning(start);
    }

    @Override
    public void showMessage(String title, String msg) {
        trayIcon.displayMessage(title, msg, TrayIcon.MessageType.INFO);
    }

    private void initSystemTray() {

        //Image
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream("euroexam.jpg"));
        } catch (IOException e) {
            System.out.println("Hiba a fileal:" + e.getMessage());
        }
        final PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(img, "TrayIcon");
        
        
        final SystemTray tray = SystemTray.getSystemTray();

        //Create a pop-up menu components
        CheckboxMenuItem cbStartStop = new CheckboxMenuItem("StartStop");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to pop-up menu

        popup.add(cbStartStop);
        popup.addSeparator();
        popup.add(exitItem);
        trayIcon.setImageAutoSize(true);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
            
        } catch (AWTException e) {
            System.out.println("TryIcon could not be added");
        }

        cbStartStop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    onStartStop(true);

                } else {
                    onStartStop(false);
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });

    }
}
