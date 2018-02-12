/*
This Thread class will download the CSV file according to the perameters that the user sets.
The downloaded file will be saved in selected directory.
*/

package mailextractror;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;

public class ReportDownloader extends Thread {

    private String fileName;
    private boolean a, b, c, d;
    private int arr[];
    private ObservableList<TableMaker.Person> allProducts;
    private TableView<TableMaker.Person> table = new TableView<TableMaker.Person>();

    ReportDownloader(String fileName, boolean a, boolean b, boolean c, boolean d, int arr[], TableView<TableMaker.Person> table) {
        this.fileName = fileName;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.arr = arr;
        this.table = table;
        allProducts = (ObservableList<TableMaker.Person>) table.getItems();

    }

    public void run() {
        PrintWriter pw;
        String pah;
        try {
            if (MailExtractror.selectedDirectory == null) {
                pw = new PrintWriter(new File(MailExtractror.defaultDirectory + File.separator + fileName + ".csv"));

            } else {
                pw = new PrintWriter(new File(MailExtractror.selectedDirectory + File.separator + fileName + ".csv"));
            }
            StringBuilder sb = new StringBuilder();
            int g = 0;
            for (TableMaker.Person p : allProducts) {
                if (p.invitedProperty().get()) {
                    if (g == 0) {

                        if (a) {
                            sb.append("Subject");
                            sb.append(',');
                        }

                        if (b) {
                            sb.append("From");
                            sb.append(',');
                        }

                        if (c) {
                            sb.append("Date");
                            sb.append(',');

                        }

                        if (d) {
                            sb.append("File Size");
                        }
                        g++;
                        sb.append('\n');
                    }

                    if (a) {
                        sb.append(p.firstNameProperty().get());
                        sb.append(',');
                    }

                    if (b) {
                        sb.append(p.lastNameProperty().get());
                        sb.append(',');
                    }

                    if (c) {

                        int pp = p.numBerProperty().get();

                        pp = -MailExtractror.SearchedInex.get(pp - 1) + MailExtractror.allmsgsize;
                        Message msg = MailExtractror.messages[MailExtractror.messages.length - pp];
                        try {
                            sb.append(msg.getSentDate().toString());
                        } catch (MessagingException ex) {
                        }

                        sb.append(',');
                    }

                    if (d) {
                        sb.append(p.emailProperty().get());
                    }

                    sb.append('\n');

                }
            }
            pw.write(sb.toString());
            pw.close();
            System.out.println("done!");

        } catch (FileNotFoundException ex) {

        }
    }
}
