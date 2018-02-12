/*
This Thread class will Search in inbox with particular Subject (with attachment only or all ) and also edit the table simultaniously. 
It will waits the user until the search is completed.
*/
package mailextractror;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;

 

public class WaitSearchByName implements Runnable{
    TableView table;
    private Date afterDate;
    private String host = "imap.gmail.com";
    private String port = "993";
    private String userName;
    private String password;
    private String subject;
    private Message[] allmsg;
    private boolean withattach;
    private TableView<TableMaker.Person> tableSearchByName;
    private SceneMaker sceneMaker;

    public WaitSearchByName(TableView<TableMaker.Person> tableSearchByName,String serch_subject, Message[] messages,int  allmsgsize,boolean checkatta,SceneMaker makescene) {

        this.subject = serch_subject;
        this.allmsg = messages;
        this.withattach = checkatta;
        this.tableSearchByName = tableSearchByName;
        this.sceneMaker = makescene;

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

                       String ss = message.getSubject();

                       // System.out.println(sub);

                        if (ss != null && sub != null && ss.contains(sub)) {

                            list.add(i);
                            MailExtractror.SearchedInex.add(i);

                            System.out.println("Opps Message " + i + " is matched: " + ss);

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
                            TableMaker.Person newadder = new TableMaker.Person(y, false, message.getSubject(), st, sofats);
                            tableSearchByName.getItems().add(newadder);
                            System.out.println("one done");
                            y++;
                            n++;
                        }
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(WaitSearchByName.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ep) {
                    ep.printStackTrace();

                }
            }

            yy = y;

        } else {
//Search for attachments only for that subject
            int y = 1;
            sub = this.subject;
            for (int i = MailExtractror.allmsgsize - 1; i >= 0; i--) {
                try {
                    Message message = allmsg[i];
                    System.out.println("Message # " + i);


                   
                        String ss = message.getSubject();

                        if (ss != null && sub != null && ss.contains(sub)) {
                            if (message.isMimeType("multipart/mixed")) {
                                Multipart mp1 = (Multipart) message.getContent();
                                if (mp1.getCount() > 1) {
                                    list.add(i);
                                    MailExtractror.SearchedInex.add(i);

                                    System.out.println("Opps Message " + i + " is matched: " + ss);

                                    int sofat = message.getSize();
                                    sofat = (sofat - (sofat * 20 / 100)) / 1024;
                                    String sofats = Integer.toString(sofat);
                                    //tableSearchBySubject.add(sofats+ " KB");
                                    sofats = sofats + " KB";
                                    Address[] from = message.getFrom();
                                    String st = "";
                                    st = st + (from == null ? null : ((InternetAddress) from[0]).getAddress());
                                    TableMaker.Person newadder = new TableMaker.Person(y, false, message.getSubject(), st, sofats);
                                    tableSearchByName.getItems().add(newadder);
                                    System.out.println("one done");
                                    y++;
                                    n++;
                                }
                            }

                        }
                   
                } catch (MessagingException ex) {
                    Logger.getLogger(WaitSearchByName.class.getName()).log(Level.SEVERE, null, ex);
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
        MailExtractror.recenttable = tableSearchByName;
        list.clear();
        sceneMaker.disableAll(false);
    }
    
    
    
    
   
}
