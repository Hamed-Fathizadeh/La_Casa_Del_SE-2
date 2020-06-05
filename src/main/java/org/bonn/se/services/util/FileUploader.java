package org.bonn.se.services.util;

import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUploader implements Upload.Receiver, Upload.SucceededListener {
    static File file ;
    static PdfWriter writer;
    static PdfDocument pdfDoc;
    final static Image image = new Image();



    public static File getFile() {
        return file;
    }

    public OutputStream receiveUpload(String filename, String mimeType) {
        // Create and return a file output stream
        FileOutputStream fos;

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        file = new File(basepath + "/lebenslauf/" + filename);
        String dest = basepath + "/lebenslauf/" + filename;
        return null;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {



    }



    public static Image getImage() {
        return image;
    }


}


