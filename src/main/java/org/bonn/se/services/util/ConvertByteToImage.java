package org.bonn.se.services.util;

import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import org.bonn.se.services.db.JDBCConnection;
import org.bonn.se.services.db.exception.DatabaseException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ConvertByteToImage {

    public static Image getImage(byte[] bytes)  {
        Image imageRes;

        try {

                if(bytes == null) {
                    ThemeResource unknownPic = new ThemeResource("images/Unknown.png");
                    imageRes =  new Image("",unknownPic);
                    imageRes.setHeight("40px");
                    imageRes.setWidth("40px");
                    return imageRes;
                }


            StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                    public InputStream getStream()
                    {
                        return (bytes == null) ? null : new ByteArrayInputStream(
                                bytes);
                    }
                };
                imageRes = new Image(
                        null, new StreamResource(
                        streamSource, "streamedSourceFromByteArray"));
            imageRes.setHeight("40px");
            imageRes.setWidth("40px");

                return imageRes;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCConnection.getInstance().closeConnection();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        ThemeResource resource = new ThemeResource("img/RegisterStudent/Unknown.png");
        imageRes = new Image(null,resource);
        imageRes.setHeight("40px");
        imageRes.setWidth("40px");
        return imageRes;
    }
}
