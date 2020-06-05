package org.bonn.se.gui.window;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.bonn.se.model.objects.dto.StellenanzeigeDTO;
import org.bonn.se.model.objects.entitites.Unternehmen;

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
        this.setClosable(false);

        Panel panel = new Panel();
        panel.setWidthFull();


        GridLayout gridLayout = new GridLayout(5, 14);
        gridLayout.setSizeFull();
        gridLayout.setMargin(true);

        Image logo = unternehmen_data.getLogo();
        Label titel = new Label("<h2><b> Bewerbung auf: " + stellenanzeige.getTitel() + "</font></b></h3>", ContentMode.HTML);


        gridLayout.addComponent(logo, 1, 1);
        gridLayout.addComponent(titel, 1, 2, 4, 2);


        Button bewerben = new Button("Bewerben");
        Button back = new Button("Zurück zu Ergebnissen");

        gridLayout.addComponent(bewerben, 4, 13, 4, 13);
        gridLayout.addComponent(back, 4, 0, 4, 0);


        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                BewerbungWindow.this.close();
            }
        });



        LineBreakCounter lineBreakCounter = new LineBreakCounter();
        lineBreakCounter.setSlow(true);

        Upload sample = new Upload(null, lineBreakCounter);
        sample.setImmediateMode(false);
        sample.setButtonCaption("Upload File");

        gridLayout.addComponent(sample, 3, 13);
        panel.setContent(gridLayout);
        this.setContent(panel);

        UploadInfoWindow  uploadInfoWindow = new UploadInfoWindow(sample, lineBreakCounter);

        sample.addStartedListener(event -> {

            if (uploadInfoWindow.getParent() == null) {
                UI.getCurrent().addWindow(uploadInfoWindow);
            }
            uploadInfoWindow.setClosable(false);
        });
        sample.addFinishedListener(event -> uploadInfoWindow.setClosable(true));


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

