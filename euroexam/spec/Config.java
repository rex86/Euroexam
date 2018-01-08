package euroexam.spec;

public interface Config {
    public boolean isValid();
    public long getDelay();
    public String getDateMatch();
    public String getSubject();
    public String getSmtpHost();
    public int getSmtpPort();
    public boolean isSmtpSsl();
    public String getSmtpUser();
    public String getSmtpPassword();
    public String getFullName();
    public String getEmailFrom();
    public String getEmailTo();
    public String getEmailBegin();
    public String getEmailEnd();
}
