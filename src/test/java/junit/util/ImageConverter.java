package junit.util;

import java.io.*;

public class ImageConverter {


    public static byte[] getImage(File file) throws IOException {


    FileInputStream fis = new FileInputStream(file);

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];

        for (int readNum; (readNum = fis.read(buf)) != -1; ){
        bos.write(buf, 0, readNum);
        System.out.println("read " + readNum + " bytes,");
        }
        return bos.toByteArray();
    }
}
