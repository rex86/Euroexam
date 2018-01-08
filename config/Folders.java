package config;

import java.io.File;

public class Folders {
    
    public static File getFile(String fileName) {
        return getFile(fileName, null);
    }
    
    public static File getFile(String fileName, File def) {
        File f = new File(System.getProperty("user.dir"), fileName);
        if (!f.exists()) {
            try {
                File oldFile = f;
                File srcFile = getSourceFile();
                f = new File(srcFile.getParentFile(), fileName);
                if (!f.exists()) f = def == null ? oldFile : def;
            }
            catch (Exception ex) {
                f = null;
            }
        }
        return f;
    }
    
    public static File getSourceFile() {
        try {
            return new File(Folders.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        }
        catch (Exception ex) {
            return null;
        }
    }
    
}
