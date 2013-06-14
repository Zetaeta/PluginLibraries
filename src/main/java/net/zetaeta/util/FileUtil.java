package net.zetaeta.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;


public class FileUtil {
    public static String fileToString(File file) {
        Util.notNull("File cannot be null!", file);
        if (!file.exists()) {
            throw new IllegalArgumentException("File must exist!");
        }
        StringBuilder sb = new StringBuilder((int) file.length() / 2);
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String buf;
            while ((buf = reader.readLine()) != null) {
                sb.append(buf).append(System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public static void copyStreams(InputStream in, OutputStream out) {
        Util.notNull("Streams cannot be null!", in, out);
        byte[] buffer = new byte[512];
        try {
            int i;
            while ((i = in.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
        }
        catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error copying streams: " + in + " --> " + out);
            e.printStackTrace();
        }
    }
    
    public static void copyDirectory(File from, File to) throws IOException {
        FileUtils.copyDirectory(from, to);
    }
}
