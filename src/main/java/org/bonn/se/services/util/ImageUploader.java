package org.bonn.se.services.util;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
    static File file ;

    final static Image image = new Image();



    public static File getFile() {
        return file;
    }

    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
        // Create and return a file output stream
        FileOutputStream fos;

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        file = new File(basepath + "/images/" + filename);

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

        image.setSource(new FileResource(file));
    }



    public static Image getImage() {
        return image;
    }






}

