package org.bonn.se.gui.window;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.control.exception.BewerbungControl;
import org.bonn.se.model.dao.ProfilDAO;
import org.bonn.se.model.objects.dto.BewerbungDTO;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.ContainerNeuigkeiten;
import org.bonn.se.model.objects.entitites.Student;
import org.bonn.se.model.objects.entitites.Taetigkeit;
import org.bonn.se.model.objects.entitites.Unternehmen;
import org.bonn.se.services.db.exception.DatabaseException;
import org.bonn.se.services.util.ImageUploader;
import org.bonn.se.services.util.PdfUploader;
import org.bonn.se.services.util.Roles;
import org.bonn.se.services.util.Views;

import java.io.OutputStream;

public class BewerbungWindow extends Window {

    public BewerbungWindow(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data)  {
        setUp(stellenanzeige,unternehmen_data);
    }

    public void setUp(StellenanzeigeDTO stellenanzeige, Unternehmen unternehmen_data) {
        center();

        this.setWidth("80%");
        this.setHeight("90%");
        this.setModal(true);
        this.setResizable(false);

        Panel panel = new Panel();
        panel.setWidthFull();


        GridLayout mainGridLayout = new GridLayout(6, 14);
        mainGridLayout.setSizeFull();
        mainGridLayout.setMargin(true);
        Student student = (Student) UI.getCurrent().getSession().getAttribute(Roles.Student);
        System.out.println(student.toString());

        Image profilbild = student.getPicture();
        Label titel = new Label("<h2><b> Bewerbung auf: " + stellenanzeige.getTitel() + "</font></b></h2>", ContentMode.HTML);
        Label vor_nachname = new Label("Vor und Nachname: ");
        Label vor_nachname_data = new Label(student.getVorname()+" "+student.getNachname());
        Label geb_datum = new Label("Geb Datum: ");
        Label geb_datum_data = new Label(""+student.getG_datum());
        Label adresse = new Label("Adresse: ");
        Label adresse_data = new Label(student.getAdresse().getStrasse());
        Label plzOrt = new Label("PLZ Ort: ");
        Label plzOrt_data = new Label(student.getAdresse().getPlz()+" "+student.getAdresse().getOrt());
        Label rufnummer = new Label("Rufnummer: ");
        Label rufnummer_data = new Label(student.getKontakt_nr());
        Label email = new Label("Email: ");
        Label email_data = new Label(student.getEmail());
        Label abschluss = new Label("Abschluss: ");
        Label abschluss_data = new Label(student.getAbschluss());
        Label studiengang = new Label("Studiengang: ");
        Label studiengang_data = new Label(student.getStudiengang());
        Label ausbildung = new Label("Ausbildung: ");
        Label ausbildung_data = new Label(student.getAusbildung());

        Label line = new Label("<h1><color: black h1>", ContentMode.HTML);

        FormLayout form1 = new FormLayout();
        form1.setMargin(false);
        form1.addComponent(new Label("<h1>Berufstätigkeiten: <h1>", ContentMode.HTML));
        System.out.println(student.getTaetigkeiten().size());
        TextArea tAreaTaet = new TextArea();
        String text = "";
        for(Taetigkeit te: student.getTaetigkeiten()){
            text += te.getTaetigkeitName()+"\n"+te.getBeginn()+" - " +te.getEnde()+"\n";
            tAreaTaet.setValue(text);
            form1.addComponent(tAreaTaet);
        }

        FormLayout form2 = new FormLayout();
        form2.setMargin(true);
        form2.addComponent(new Label("<h1>IT-Kenntnisse: <h1>", ContentMode.HTML));
        TextArea tAreaITK = new TextArea();
        text = "";
        for(Student.ITKenntnis itK: student.getItKenntnisList()){
            text+=itK.getKenntnis()+":  "+itK.getNiveau()+"\n";
            tAreaITK.setValue(text);
            form2.addComponent(tAreaITK);
        }

        FormLayout form3 = new FormLayout();
        form3.setMargin(true);
        form3.addComponent(new Label("<h1>Sprachkenntnisse: <h1>", ContentMode.HTML));
        TextArea tAreaSpr = new TextArea();
        text = "";
        for(Student.SprachKenntnis sp: student.getSprachKenntnisList()){
            text += sp.getKenntnis()+":  "+sp.getNiveau()+"\n";
            tAreaSpr.setValue(text);
            form3.addComponent(tAreaSpr);
        }



        mainGridLayout.addComponent(titel, 0, 1, 5, 1);
          mainGridLayout.addComponent(profilbild, 0, 2,0,7);
        mainGridLayout.addComponent(vor_nachname, 1, 2);mainGridLayout.addComponent(vor_nachname_data, 2, 2);
           mainGridLayout.addComponent(geb_datum, 1, 3);mainGridLayout.addComponent(geb_datum_data, 2, 3);
             mainGridLayout.addComponent(adresse, 1, 4); mainGridLayout.addComponent(adresse_data, 2, 4);
              mainGridLayout.addComponent(plzOrt, 1, 5);mainGridLayout.addComponent(plzOrt_data, 2, 5);
           mainGridLayout.addComponent(rufnummer, 1, 6);mainGridLayout.addComponent(rufnummer_data, 2, 6);
               mainGridLayout.addComponent(email, 1, 7); mainGridLayout.addComponent(email_data, 2, 7);

        mainGridLayout.addComponent(abschluss, 3, 2); mainGridLayout.addComponent(abschluss_data, 4, 2);
        mainGridLayout.addComponent(studiengang, 3, 3);  mainGridLayout.addComponent(studiengang_data, 4, 3);
        mainGridLayout.addComponent(ausbildung, 3, 4); mainGridLayout.addComponent(ausbildung_data, 4, 4);

        mainGridLayout.addComponent(line, 0, 8,4,8);

        mainGridLayout.addComponent(form1, 0, 9,1,9);
        mainGridLayout.addComponent(form2, 2, 9,3,9);
        mainGridLayout.addComponent(form3, 4, 9,5,9);



        mainGridLayout.setComponentAlignment(titel, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(profilbild, Alignment.MIDDLE_CENTER);

        mainGridLayout.setComponentAlignment(line, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(form1, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(form2, Alignment.TOP_CENTER);
        mainGridLayout.setComponentAlignment(form3, Alignment.TOP_CENTER);

        Button bewerben = new Button("Bewerben");

        mainGridLayout.addComponent(bewerben, 5, 13);

        PdfUploader receiver = new PdfUploader();
        // Create the upload with a caption and set receiver later
        Upload upload = new Upload("", receiver);
        upload.addSucceededListener(receiver);
        upload.setButtonCaption("PDF hochladen");



        mainGridLayout.addComponent(upload, 0, 13,4,13);
        panel.setContent(mainGridLayout);
        this.setContent(panel);
        final byte[] myByte = null;
        upload.addStartedListener(event -> {
           event.getFilename();

        });



        bewerben.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                BewerbungDTO bewerbungDTO = new BewerbungDTO();
                bewerbungDTO.setDescription(stellenanzeige.getBeschreibung());
                bewerbungDTO.setLebenslauf(PdfUploader.getByte());
                bewerbungDTO.setStatus(1);
                System.out.println("BewWindow hier1.2"+student.getStudent_id());
                bewerbungDTO.setStudentID(student.getStudent_id());
                bewerbungDTO.setAnzeigeID(stellenanzeige.getId());

                BewerbungControl.bewerben(bewerbungDTO);
            }
        });

    }


    @StyleSheet("uploadexample.css")
    private static class UploadInfoWindow extends Window implements
            Upload.StartedListener, Upload.ProgressListener,
            Upload.FailedListener, Upload.SucceededListener,
            Upload.FinishedListener {
        private final Label state = new Label();
        private final Label result = new Label();
        private final Label fileName = new Label();
        private final Label textualProgress = new Label();

        private final ProgressBar progressBar = new ProgressBar();
        private final Button cancelButton;
        private final LineBreakCounter counter;

        private UploadInfoWindow(final Upload upload, final LineBreakCounter lineBreakCounter) {
            super("Status");
            this.counter = lineBreakCounter;

            addStyleName("upload-info");

            setResizable(false);
            setDraggable(false);

            final FormLayout uploadInfoLayout = new FormLayout();
            setContent(uploadInfoLayout);
            uploadInfoLayout.setMargin(true);

            final HorizontalLayout stateLayout = new HorizontalLayout();
            stateLayout.setSpacing(true);
            stateLayout.addComponent(state);

            cancelButton = new Button("Cancel");
            cancelButton.addClickListener(event -> upload.interruptUpload());
            cancelButton.setVisible(false);
            cancelButton.setStyleName("small");
            stateLayout.addComponent(cancelButton);

            stateLayout.setCaption("Current state");
            state.setValue("Idle");
            uploadInfoLayout.addComponent(stateLayout);

            fileName.setCaption("File name");
            uploadInfoLayout.addComponent(fileName);

            result.setCaption("Line breaks counted");
            uploadInfoLayout.addComponent(result);

            progressBar.setCaption("Progress");
            progressBar.setVisible(false);
            uploadInfoLayout.addComponent(progressBar);

            textualProgress.setVisible(false);
            uploadInfoLayout.addComponent(textualProgress);

            upload.addStartedListener(this);
            upload.addProgressListener(this);
            upload.addFailedListener(this);
            upload.addSucceededListener(this);
            upload.addFinishedListener(this);

        }

        @Override
        public void uploadFinished(final Upload.FinishedEvent event) {
            state.setValue("Idle");
            progressBar.setVisible(false);
            textualProgress.setVisible(false);
            cancelButton.setVisible(false);
        }

        @Override
        public void uploadStarted(final Upload.StartedEvent event) {
            // this method gets called immediately after upload is started
            progressBar.setValue(0f);
            progressBar.setVisible(true);
            UI.getCurrent().setPollInterval(500);
            textualProgress.setVisible(true);
            // updates to client
            state.setValue("Uploading");
            fileName.setValue(event.getFilename());

            cancelButton.setVisible(true);
           event.getMIMEType();
        }

        @Override
        public void updateProgress(final long readBytes, final long contentLength) {
            // this method gets called several times during the update
            progressBar.setValue(readBytes / (float) contentLength);
            textualProgress.setValue("Processed " + readBytes + " bytes of " + contentLength);
            result.setValue(counter.getLineBreakCount() + " (counting...)");
        }

        @Override
        public void uploadSucceeded(final Upload.SucceededEvent event) {
            result.setValue(counter.getLineBreakCount() + " (total)");
        }

        @Override
        public void uploadFailed(final Upload.FailedEvent event) {
            result.setValue(counter.getLineBreakCount()
                    + " (counting interrupted at "
                    + Math.round(100 * progressBar.getValue()) + "%)");
        }
    }

    private static class LineBreakCounter implements Upload.Receiver {
        private int counter;
        private int total;
        private boolean sleep;

        /**
         * return an OutputStream that simply counts lineends
         */
        @Override
        public OutputStream receiveUpload(final String filename, final String MIMEType) {
            counter = 0;
            total = 0;
            return new OutputStream() {
                private static final int searchedByte = '\n';

                @Override
                public void write(final int b) {
                    total++;
                    if (b == searchedByte) {
                        counter++;
                    }
                    if (sleep && total % 1000 == 0) {
                        try {
                            Thread.sleep(100);
                        } catch (final InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }

        private int getLineBreakCount() {
            return counter;
        }

        private void setSlow(boolean value) {
            sleep = value;
        }
    }

}

