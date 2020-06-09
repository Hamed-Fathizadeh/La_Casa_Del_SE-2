package org.bonn.se.services.util;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;

import java.io.*;

public class PdfUploader implements Upload.Receiver, Upload.SucceededListener {
    static File file ;
    static byte[] myByte;



    public static File getFile() {
        return file;
    }

    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
        // Create and return a file output stream
        FileOutputStream fos;

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        file = new File(basepath + "/VAADIN/themes/demo/PDF/" + filename);

        try {
            fos = new FileOutputStream(file);
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
        return fos;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        myByte = readFileToByteArray(file);

    }



    public static byte[] getByte() {
        return myByte;
    }
    private static byte[] readFileToByteArray(File file){
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }





}

