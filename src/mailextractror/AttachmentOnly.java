/*
This class will search all messages from inbox and find out the messages that contains attachments only.
And its a different thread. 
 */
package mailextractror;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TableView;
import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;

public class AttachmentOnly implements Runnable {

    private Date afterDate;
    private String host = "imap.gmail.com";
    private String port = "993";
    private String userName;
    private String password;
    private String subject;
    private Message[] allmsg;
    private boolean withattach;
    private TableView<TableMaker.Person> tableSearchByAttach;
    private SceneMaker sceneMaker;
    private boolean checkattach;

    public AttachmentOnly(TableView<TableMaker.Person> tableSearchByAttach, Message[] allmsg, boolean withattach, SceneMaker sceneMaker) {
        this.afterDate = afterDate;

        this.allmsg = allmsg;
        this.withattach = withattach;
        this.tableSearchByAttach = tableSearchByAttach;
        this.sceneMaker = sceneMaker;

        this.allmsg = allmsg;

        System.out.println("getting in");
    }

    public void run() {

        System.out.println("Searching by name");
        int n = 0;
        String s = "";
        MailExtractror.SearchedInex.clear();
        List<Integer> list = new ArrayList<>();
        int y = 1;
        for (int i = MailExtractror.allmsgsize - 1; i >= 0; i--) {
            try {
                Message message = allmsg[i];
                System.out.println("Message # " + i);

                //s = message.getSubject();
                if (message.isMimeType("multipart/mixed")) {
                    Multipart mp1 = (Multipart) message.getContent();
                    if (mp1.getCount() > 1) {
                        list.add(i);
                        MailExtractror.SearchedInex.add(i);
                        int sofat = message.getSize();
                        sofat = (sofat - (sofat * 20 / 100)) / 1024;
                        String sofats = Integer.toString(sofat);
                        //tableSearchBySubject.add(sofats+ " KB");
                        sofats = sofats + " KB";
                        Address[] from = message.getFrom();
                        String st = "";
                        st = st + (from == null ? null : ((InternetAddress) from[0]).getAddress());
                        TableMaker.Person newadder = new TableMaker.Person(y, false, message.getSubject(), st, sofats);
                        tableSearchByAttach.getItems().add(newadder);
                        System.out.println("one done");
                        y++;
                        n++;
                    }
                }

            } catch (MessagingException ex) {
                System.out.println("mailextractror.AttachmentOnly.run(). messaging exception");
            } catch (IOException ex) {
                System.out.println("mailextractror.AttachmentOnly.run(). IO exception");
            }
        }

        TableMaker.ans = new int[y];
        Message[] mesg = new Message[n];
        for (int i = 0; i < n; i++) {
            mesg[n - 1 - i] = allmsg[list.get(i)];
        }
        MailExtractror.recentMessages = mesg;
        MailExtractror.recenttable = tableSearchByAttach;
        list.clear();
        //   MailExtractror.schk = true;
        sceneMaker.disableAll(false);

    }
}
