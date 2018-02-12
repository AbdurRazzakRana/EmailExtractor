/*
This Thread class will Search in inbox with particular date (with attachment only or all ) and also edit the table simultaniously. 
It will waits the user until the search is completed.
note: the date will work with afterdate, like if i search with 8/2/2016, then this class will find the messages that comes to user after that date.
*/

package mailextractror;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import javax.mail.Address;
import java.lang.String;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import mailextractror.TableMaker.Person;

public class WaitSerachWithDay implements Runnable {

    TableView table;
    private Date afterDate;
    private String host = "imap.gmail.com";
    private String port = "993";
    private String userName;
    private String password;
    private String subject;
    private Message[] allmsg;
    private boolean withattach;
    private TableView<TableMaker.Person> tableSearchByDate;
    private SceneMaker sceneMaker;

    public WaitSerachWithDay(TableView<TableMaker.Person> tableSearchByDate, Date afterDate, Message[] allmsg, boolean withattach, SceneMaker sceneMaker) {
        this.afterDate = afterDate;

        this.allmsg = allmsg;
        this.withattach = withattach;
        this.tableSearchByDate = tableSearchByDate;
        this.sceneMaker = sceneMaker;

    }

    public void run() {

        int yy;
        int n = 0;
        int size = allmsg.length;
        boolean b;
        CharSequence sub = this.subject;
        ArrayList<String> tableSearchBySubject = new ArrayList<>();
        MailExtractror.SearchedInex.clear();
        int p = 0;

        List<Integer> list = new ArrayList<>();

        if (withattach == false) {
            
//search for all message 
            int y = 1;
            for (int i = MailExtractror.allmsgsize - 1; i >= 0; i--) {
                try {
                    Message message = allmsg[i];
                    System.out.println("Message # " + i);

                    if (message.getSentDate().after(afterDate)) {

                        list.add(i);
                        MailExtractror.SearchedInex.add(i);

                        int x = 0;
                        String sofats;
                        if (message.isMimeType("multipart/mixed")) {
                            Multipart mp = (Multipart) message.getContent();
                            if (mp.getCount() > 1) {
                                x++;
                            }
                        }
                        if (x == 1) {
                            int sofat = message.getSize();
                            sofat = (sofat - (sofat * 20 / 100)) / 1024;
                            sofats = Integer.toString(sofat);
                            tableSearchBySubject.add(sofats + " KB");
                            sofats = sofats + " KB";
                        } else {
                            tableSearchBySubject.add("");
                            sofats = "";
                        }
                        Address[] from = message.getFrom();
                        String st = "";
                        st = st + (from == null ? null : ((InternetAddress) from[0]).getAddress());
                        Person newadder = new Person(y, false, message.getSubject(), st, sofats);
                        tableSearchByDate.getItems().add(newadder);
                        System.out.println("one done");
                        y++;
                        n++;

                    } else {
                        break;
                    }
                } catch (MessagingException ex) {

                } catch (Exception ep) {
                    ep.printStackTrace();

                }
            }

            yy = y;

        } else {
//Search for attachments only for that date
            int y = 1;
            sub = this.subject;
            for (int i = MailExtractror.allmsgsize - 1; i >= 0; i--) {
                try {
                    Message message = allmsg[i];
                    System.out.println("Message # " + i);

                    boolean ccc = message.getSentDate().after(afterDate);
                    if (ccc == true) {

                        if (message.isMimeType("multipart/mixed")) {
                            Multipart mp1 = (Multipart) message.getContent();
                            if (mp1.getCount() > 1) {
                                list.add(i);
                                MailExtractror.SearchedInex.add(i);

                                int sofat = message.getSize();
                                sofat = (sofat - (sofat * 20 / 100)) / 1024;
                                String sofats = Integer.toString(sofat);

                                sofats = sofats + " KB";
                                Address[] from = message.getFrom();
                                String st = "";
                                st = st + (from == null ? null : ((InternetAddress) from[0]).getAddress());
                                Person newadder = new Person(y, false, message.getSubject(), st, sofats);
                                tableSearchByDate.getItems().add(newadder);
                                System.out.println("one done");
                                y++;
                                n++;
                            }
                        }

                    } else {
                        break;
                    }
                } catch (MessagingException ex) {

                } catch (IOException ex) {
                    Logger.getLogger(WaitSerachWithNameAndDay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            yy = y;
        }

        TableMaker.ans = new int[yy];
        Message[] mesg = new Message[n];
        for (int i = 0; i < n; i++) {
            mesg[n - 1 - i] = allmsg[list.get(i)];
        }
        MailExtractror.recentMessages = mesg;
        MailExtractror.recenttable = tableSearchByDate;
        list.clear();

        sceneMaker.disableAll(false);
    }

}
