package org.bonn.se.services.util;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.services.db.JDBCConnection;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PdfUploader implements Upload.Receiver, Upload.SucceededListener {
    static File file ;
    static byte[] myByte;
    static String path = null;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        PdfUploader.path = path;
    }

    final Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);



    public static File getFile() {
        return file;
    }

    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
        // Create and return a file output stream
        FileOutputStream fos;

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        file = new File(basepath + "/VAADIN/themes/demo/PDF/" + student.getEmail()+filename);
        setPath(basepath + "/VAADIN/themes/demo/PDF/" + student.getEmail()+filename);

        try {
            fos = new FileOutputStream(file);
        } catch (final IOException e) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ioExp);
        }
        return bArray;
    }





}

