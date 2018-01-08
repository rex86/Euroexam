package euroexam;

import config.ConfigMap;
import euroexam.spec.Config;
import java.io.File;

public class FileConfig extends ConfigMap implements Config {

    public FileConfig(File cfgFile) {
        //Emiatt nem kell egy külön osztályváltozó ConfigMap és elég csak a sima get("kulcs");
        //ugyanaz ConfigMap conf = new ConfigMap();
        //conf.get("kulcs");
        super(readFile(cfgFile));
    }

    @Override
    public boolean isValid() {
        //Kötelezően megadott értékek ellenőrzése
        // további feltételek pl. ÉS (felhasználónév üres VAGY jelszó nem üres)
        //return !isEmpty(getSmtpUser()) && isEmpty(getEmailFrom()) && (isEmpty(getSmtpUser()) || !isEmpty(getSmtpPassword())); 
        return (isEmpty(getSmtpUser()) || !isEmpty(getSmtpPassword()));         
    }

    @Override
    public long getDelay() {
        return getInt("setDelay",1,60000,5000);
    }

    @Override
    public String getDateMatch() {
        return get("date");
    }

    @Override
    public String getSubject() {
        return get("messageSubject");
    }

    @Override
    public String getSmtpHost() {
        return get("smtpHost");
    }

    @Override
    public int getSmtpPort() {
        return getInt("smtpPort",1,65535,isSmtpSsl() ? 465 : 25);
//        if(isSmtpSsl()){
//            return getInt("smtpPort",1,65535,465);
//        }else{
//            return getInt("smtpPort",1,65535,25);
//        }
    }

    @Override
    public boolean isSmtpSsl() {
        return getBool("isSmtpSSL", true);
    }

    @Override
    public String getSmtpUser() {
        return get("smtpUser");
    }

    @Override
    public String getSmtpPassword() {
        return get("smtpPasswd");
    }

    @Override
    public String getFullName() {
        return get("fullName");
    }

    @Override
    public String getEmailFrom() {
        return get("email-from", getSmtpUser());
    }

    @Override
    public String getEmailTo() {
        return get("emailTo");
    }

    @Override
    public String getEmailBegin() {
        //TODO
        return get("emailBegin");
    }

    @Override
    public String getEmailEnd() {
        //TODO
        return get("emailEnd");
    }
    
}
