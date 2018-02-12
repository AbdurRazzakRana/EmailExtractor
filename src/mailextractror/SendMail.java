/*
This Thread class will send messages to the selected recieplents contaings text and attachment.
Server connection neeaded here.

*/

package mailextractror;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class SendMail extends Thread {

    private String host, port, MailSubject, textpart, pass, user;
    private String[] attachFiles;
    int ans[];
    private ObservableList<TableMaker.Person> allProducts;
    private TableView<TableMaker.Person> table = new TableView<TableMaker.Person>();
    private int type;

    SendMail(String host, String port, String MailsSubject, String textpart, String[] attachFiles, int ans[], TableView<TableMaker.Person> table, String user, String pass, int type) {
        this.host = host;
        this.MailSubject = MailsSubject;
        this.port = port;
        this.textpart = textpart;
        this.attachFiles = attachFiles;
        this.ans = ans;
        this.type = type;
        this.table = table;
        this.user = user;
        this.pass = pass;
        allProducts = (ObservableList<TableMaker.Person>) table.getItems();

    }

    public void run() {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", user);
        properties.put("mail.password", pass);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        };
        Session session = Session.getInstance(properties, auth);

        for (TableMaker.Person p : allProducts) {
//sending message one by one to the reciepents from selected Items.
            try {
                if (p.invitedProperty().get()) {
                    String toAddress = p.lastNameProperty().get();

                    Message msg = new MimeMessage(session);

                    msg.setFrom(new InternetAddress(user));
                    InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
                    msg.setRecipients(Message.RecipientType.TO, toAddresses);
                    msg.setSubject(MailSubject);
                    msg.setSentDate(new Date());

                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(textpart, "text/html");

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    // adds attachments
                    if (attachFiles != null && attachFiles.length > 0) {
                        for (String filePath : attachFiles) {
                            MimeBodyPart attachPart = new MimeBodyPart();

                            try {
                                attachPart.attachFile(filePath);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            multipart.addBodyPart(attachPart);
                        }
                    }
                    msg.setContent(multipart);

                    Transport.send(msg);

                }
            } catch (Exception ert) {

                System.out.println("Problem in To ID: " + p.lastNameProperty().get());

            }

        }

    }
}
