package junit.util;

import org.bonn.se.services.db.JDBCConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageConverter {



    public static byte[] getImage(File file) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
                return bos.toByteArray();

            }
        } catch (IOException e) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            assert fis != null;
            fis.close();
        }
        return null;
    }

}
