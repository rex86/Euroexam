package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMap extends HashMap<String, String> {
    
    /**
     * Általános rendszerváltozók.
     */
    public static final String LS = System.getProperty("line.separator");
    
    /**
     * A soronként érvényes megjegyzés karakter.
     */
    private static final String CC = "#";
    
    /**
     * Megadja, hogy az inicializáláskor null referencia lett-e
     * átadva a {@link #ConfigMap(java.util.Map)} konstruktornak.
     */
    protected final boolean NULL_INIT;
    
    public ConfigMap(File file) throws IOException {
        NULL_INIT = false;
        List<String> conf = read(file);
        String key, val;
        int ind, indSpace, indTab;
        for (String ln : conf) {
            indSpace = ln.indexOf(" ");
            indTab = ln.indexOf("\t");
            ind = indTab != -1 && indTab < indSpace ? indTab : indSpace;
            if (ind == -1) {
                key = ln;
                val = "";
            }
            else {
                key = ln.substring(0, ind);
                val = ln.substring(ind).trim();
            }
            put(key, val.replace("\\n", "\n"));
        }
    }

    protected ConfigMap(Map<? extends String, ? extends String> map) {
        super(map == null ? new HashMap<String, String>() : map);
        NULL_INIT = map == null;
    }

    public static ConfigMap readFile(File cfgFile) {
        try {
            return new ConfigMap(cfgFile);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public String get(String key, String def) {
        String val = get(key);
        if (val == null) return def;
        return val;
    }
    
    public int getInt(String key, int min, int max) {
        return getInt(key, min, max, min);
    }
    
    public int getInt(String key, int min, int max, int def) {
        try {
            int val = Integer.parseInt(get(key));
            if (val < min) val = min;
            else if (val > max) val = max;
            return val;
        }
        catch (Exception ex) {
            return def;
        }
    }
    
    public boolean getBool(String key, boolean def) {
        try {
            String s = get(key);
            if (s == null) return def;
            if (s.equals("1")) return true;
            if (s.equals("0")) return false;
            return Boolean.parseBoolean(s);
        }
        catch (Exception ex) {
            return def;
        }
    }
    
    protected static boolean isEmpty(String s) {
        if (s == null) return true;
        return s.isEmpty();
    }
    
    private static List<String> read(File f) throws IOException {
        int ind;
        String ln;
        List<String> ls = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        while ((ln = in.readLine()) != null) {
            ln = ln.trim();
            if (ln.startsWith(CC)) continue;
            ind = ln.indexOf(CC);
            if (ind != -1) ln = ln.substring(0, ind);
            if (!ln.isEmpty()) ls.add(ln);
        }
        in.close();
        return ls;
    }
    
}
