/*
this class will initailize the variables of this program
*/
package mailextractror;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.TableView;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;

public class Massages implements Runnable {

    Message[] messages;
    int n;
    ArrayList<String> tablearray;
    TableView finaltable;
    Thread t;

    public Massages(Message messages[]) {
        this.messages = messages;
        this.n = messages.length;
        tablearray = new ArrayList<>();
        t = new Thread(this);
        t.setPriority(7);
        t.start();
    }

    @Override
    public void run() {
        int k = 0;
        int l = 0;
        try {
            
            MailExtractror.initmsgs = k;

            TableView tablex;
            TableMaker tbl = new TableMaker();
            tablex = tbl.Make(tablearray, k);
            MailExtractror.table = tablex;
            MailExtractror.recenttable = tablex;
        } catch (Exception e) {
            MailExtractror.initmsgs = k;

            TableView tablex;
            TableMaker tbl = new TableMaker();
            tablex = tbl.Make(tablearray, k);
            MailExtractror.table = tablex;
            MailExtractror.recenttable = tablex;
            e.printStackTrace();
        }
    }
}
