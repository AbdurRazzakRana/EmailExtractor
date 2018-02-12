/*
This is class that contains main mathod and the start up class.
This class will do several things.
It brings the login page(1st page), take the email id and password, then conntect with server,
after connecting, it fatchs the inbox folder of mail. After taking all mails from inbox it goes to the
second page.
 */
package mailextractror;

import com.sun.mail.imap.IMAPFolder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class MailExtractror extends Application {

    static String selfID, selfPass;
    static Stage window;
    static Scene scene, scene2, scene3, scene2nd;
    Button button;
    static IMAPFolder inbox;
    static Store store;

    static TableView recenttable;
    static Message[] recentMessages, FirstMessages;

    ComboBox actioncombo;

    public static int initmsgs, allmsgsize;
    static int idcheker = 0, passcheker = 0;
    static String oldid, oldpass;
    static int[][] tempar;
    static TableView table, tableSearchByName;

    static Message[] messages;

    static int[] archivearr, firstarrselect;
    static List<Integer> SearchedInex = new ArrayList<>();

    static File defaultDirectory, selectedDirectory;
    File dfile;
    static String path, path2;

    public static void main(String[] args) throws IOException {
        
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        path = dir + File.separator + "Imap" + File.separator + "Local Roaming" + File.separator + "Google" + File.separator + "51.0.2704.84" + File.separator + "default_apps" + File.separator + "StartupUser.t";
        File idfile = new File(path);
        idfile.getParentFile().mkdirs();
        idfile.createNewFile();
        
        String tempPath = dir + File.separator + "downloads";
        File file = new File(dir + File.separator + "downloads");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!, it is already exits");
            }
        }
        
        defaultDirectory = new File(dir + File.separator + "downloads");
        
        path2 = dir + File.separator + "Imap" + File.separator + "Local Roaming" + File.separator + "Google" + File.separator + "51.0.2704.84" + File.separator + "default_apps" + File.separator + "StartupAccess.t";
        idfile = new File(path2);
        idfile.getParentFile().mkdirs();
        idfile.createNewFile();
        launch(args);
    }

    public MailExtractror() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("GmailExtractor");

        Label l1 = new Label("Gmail-ID");
        Label l2 = new Label("Password");

        TextField email = new TextField();

        BufferedReader savedid = new BufferedReader(new FileReader(path));
        oldid = "";
        if ((oldid = savedid.readLine()) != null) {
            savedid.close();
            email.setText(oldid);
        } else {
            idcheker++;
        }
        PasswordField password = new PasswordField();
        oldpass = "";
        BufferedReader savedpass = new BufferedReader(new FileReader(path2));
        if ((oldpass = savedpass.readLine()) != null) {
            savedpass.close();
            password.setText(oldpass);
        } else {
            passcheker++;
        }

        button = new Button("Login");
        button.setOnAction((ActionEvent e) -> {
            boolean t;
            try {
//chrcking ID and pass is in correct form
                t = isgmail(email, email.getText());

                if (t == true) {

                    //teking server connection.
                    String user = email.getText();
                    String pwd = password.getText();
                    try {
                        Properties props = new Properties();
                        props.setProperty("mail.store.protocol", "imaps");
                        props.setProperty("mail.imap.partialfetch", "false");
                        
                        
                        Session session = Session.getInstance(props, null);
                        store = session.getStore();
                        store.connect("imap.gmail.com", user, pwd);

                        System.out.println("#" + user + "#" + oldid + "#");

//if it connects, then ask user to save id and password or not, for next time login.
                        if (user.equals(oldid) && pwd.equals(oldpass)) {
                        } else {
                            boolean result = SimpleScene.display("GmailExtractor", "Do you want to save your Email Id & Password");
                            //System.out.println(result);
                            if (result == true) {
                                try (PrintWriter writer = new PrintWriter(path)) {
                                    writer.print("");
                                    writer.print(user);
                                    writer.close();
                                    oldid = user;
                                }
                                try (PrintWriter writer1 = new PrintWriter(path2)) {
                                    writer1.print("");
                                    writer1.print(pwd);
                                    writer1.close();

                                }
                              
                            } else {
                            }
                        }
//Fatching inbox of gamil & keeping in a folder;
                        inbox = (IMAPFolder) store.getFolder("INBOX");
                        inbox.open(Folder.READ_WRITE);

                        messages = inbox.getMessages();

                        //////////////Common

                        //Initailizing variables
                        Massages tab = new Massages(messages);
                        recentMessages = messages;
                        FirstMessages = messages;
                        System.out.println("here it is");
                        table = new TableView<TableMaker.Person>();
                        int n = messages.length;
                        allmsgsize = n;

                        System.out.println("catchong point");

                        selfID = user;
                        selfPass = pwd;

              // not necessary 
              Label topgap = new Label("\n\n");
                        TextField serch_subject = new TextField();
                        serch_subject.setPromptText("Search by Subject");

                        TextField serch_date = new TextField();
                        serch_date.setPromptText("Search by Date");
                        CheckBox checkattach = new CheckBox();
                        checkattach.setText("Attachments only");
                        checkattach.setAlignment(Pos.BOTTOM_LEFT);

                        Button search = new Button("Search");
                        search.setMinWidth(100);
                        System.out.println(search.getMinHeight() + " " + search.getMinWidth());

                        search.setOnAction((ActionEvent ee) -> {
                            System.out.println("attachment hunting " + checkattach.isSelected());
                            firstarrselect = new int[TableMaker.ans.length];
                            firstarrselect = TableMaker.ans;

                            System.out.println("Search ckicked");

                            Scene srchbyname;
                            String s1 = serch_subject.getText();
                            String s2 = serch_date.getText();
                            int x = s1.length();
                            int y = s2.length();
                            if (x != 0 && y != 0) {
//Searching with name & date (with attachments only or all)
                                try {

                                    TableView tableSearchByNameAndDate = null;

                                    TableMaker tm = new TableMaker();
                                    tableSearchByNameAndDate = new TableView<>();
                                    tableSearchByNameAndDate = tm.Make();

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = dateFormat.parse(serch_date.getText());
                                    //textFileName = s1 + "_And_" + s2;

                                    SceneMaker makescene = new SceneMaker(tableSearchByNameAndDate, window, scene2nd);
                                    srchbyname = makescene.MakingScene();
                                    makescene.disableAll(true);
                                    window.setScene(srchbyname);

                                    WaitSerachWithNameAndDay sf = new WaitSerachWithNameAndDay(tableSearchByNameAndDate, serch_subject.getText(), date, messages, checkattach.isSelected(), makescene);
                                    Thread to = new Thread(sf);
                                    to.setPriority(9);
                                    to.start();

                                } catch (ParseException ex) {
                                    Logger.getLogger(MailExtractror.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } else if (x != 0) {
//Searching with name (with attachments only or all)
                                TableView tableSearchByName = null;

                                TableMaker tm = new TableMaker();
                                tableSearchByName = new TableView<>();
                                tableSearchByName = tm.Make();

                                SceneMaker makescene = new SceneMaker(tableSearchByName,  window, scene2nd);
                                srchbyname = makescene.MakingScene();
                                makescene.disableAll(true);
                                window.setScene(srchbyname);

                                WaitSearchByName sbnm = new WaitSearchByName(tableSearchByName, serch_subject.getText(), messages, allmsgsize, checkattach.isSelected(), makescene);
                                Thread xyx = new Thread(sbnm);
                                xyx.setPriority(9);
                                xyx.start();
                                System.out.println("Subject1254");

                            } else if (y != 0) {
//Searching with date (with attachments only or all)
                                try {

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = dateFormat.parse(serch_date.getText());

                                    TableView tableSearchByDate = null;

                                    TableMaker tm = new TableMaker();
                                    tableSearchByDate = new TableView<>();
                                    tableSearchByDate = tm.Make();

                                    SceneMaker makescene = new SceneMaker(tableSearchByDate,  window, scene2nd);
                                    srchbyname = makescene.MakingScene();
                                    makescene.disableAll(true);
                                    window.setScene(srchbyname);

                                    WaitSerachWithDay sbnm = new WaitSerachWithDay(tableSearchByDate, date, messages, checkattach.isSelected(), makescene);
                                    Thread xyx = new Thread(sbnm);
                                    xyx.setPriority(9);
                                    xyx.start();
                                    System.out.println("Subject1254");

                                } catch (ParseException ex) {
                                    Logger.getLogger(MailExtractror.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (checkattach.isSelected()) {
//Searching with attachmnents only                                
                                TableView tableSearchByAttach = null;
                                TableMaker tm = new TableMaker();
                                tableSearchByAttach = new TableView<>();
                                tableSearchByAttach = tm.Make();

                                SceneMaker makescene = new SceneMaker(tableSearchByAttach,  window, scene2nd);
                                srchbyname = makescene.MakingScene();
                                makescene.disableAll(true);
                                window.setScene(srchbyname);
                                AttachmentOnly sbnm = new AttachmentOnly(tableSearchByAttach, messages, true, makescene);
                                Thread xyx = new Thread(sbnm);
                                xyx.setPriority(9);
                                xyx.start();
                                System.out.println("Subject1254");

                            } else {
                                System.out.println("none");
                            }
                        });

                        CheckBox allornot = new CheckBox();
                        allornot.setOnAction(errr -> {
                            SelectAllorNot sar = new SelectAllorNot(MailExtractror.table);
                            if (allornot.isSelected()) {
                                sar.selectedtoall(true);
                            } else {
                                sar.selectedtoall(false);
                            }

                        });

                        actioncombo = new ComboBox();
                        actioncombo.setValue("Download");
                        actioncombo.getItems().addAll(
                                "Attachments",
                                "CSV Reports",
                                "Again"
                        );
                        actioncombo.valueProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue ov, String t, String t1) {
                                try {
                                    if (t1 != "Again") {
                                        if (t1 == "Attachments") {
                                            SelectedActionWork.downing = 0;

                                            int sz = TableMaker.ans.length;
                                            int dans[] = new int[sz];
                                            dans = TableMaker.ans;
                                            Message[] down = new Message[recentMessages.length];
                                            down = recentMessages;

                                            if (selectedDirectory == null) {
                                            } else {
                                                defaultDirectory = selectedDirectory;
                                            }

                                            dfile = new File("");
                                            dfile = defaultDirectory;

                                            SelectedActionWork selectedactionwork = new SelectedActionWork(dfile, down, dans, t1);
                                            System.out.println("now on working ");
                                            Thread dp = new Thread(selectedactionwork);
                                            dp.setPriority(8);
                                            dp.start();

                                            int maxsize = 0;
                                            for (int i = 1; i < TableMaker.ans.length; i++) {
                                                if (TableMaker.ans[i] == 1) {
                                                    maxsize++;
                                                }
                                            }
                                            DownloadBar dbar = new DownloadBar();
                                            dbar.barsetter(maxsize);

                                        } else if (t1 == "CSV Reports") {

                                            CSVScene csvpage = new CSVScene();
                                            csvpage.starter();

                                        } else {

                                        }
                                        actioncombo.setValue("Download");
                                    }
                                } catch (Exception px) {
                                    Label prob = new Label("Working on this Action is Hampered\nPlease check your internet connection or other settings to complete this task.");
                                    BorderPane probpane = new BorderPane(prob, null, null, null, null);
                                    Stage stgprob = new Stage();
                                    Scene probscen = new Scene(probpane, 500, 200);
                                    stgprob.setScene(probscen);
                                    stgprob.showAndWait();

                                }

                            }
                        });
                        final ComboBox selectcombo = new ComboBox();
                        selectcombo.setValue("Options");
                        selectcombo.getItems().addAll(
                                "Reply",
                                "Archive",
                                "Again"
                        );
                        selectcombo.valueProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue ov, String t, String t1) {

                                Selectingoption selectAllorNot = new Selectingoption();
                                try {
                                    selectAllorNot.Selection(t1);
                                } catch (MessagingException ex) {

                                }
                            }
                        });
//taking directory
                        Button directory = new Button("Set Directory");
                        directory.setOnAction(x -> {
                            System.out.println(selectedDirectory);
                            DirectoryChooser chooser = new DirectoryChooser();
                            chooser.setTitle("Select The Location Where Files to be Downloaded");
                            chooser.setInitialDirectory(defaultDirectory);
                            selectedDirectory = chooser.showDialog(window);
                            if (selectedDirectory == null) {
                            } else {
                                defaultDirectory = selectedDirectory;
                            }

                        });
                        directory.setMinWidth(100);
                        VBox dirrr = new VBox(5);
                        dirrr.setPadding(new Insets(5, 5, 5, 5));
                        dirrr.getChildren().add(directory);
                        dirrr.setAlignment(Pos.TOP_CENTER);
//Logout
                        Button logout = new Button("LogOut      ");
                        logout.setMinWidth(100);
                        logout.setOnAction(e1 -> {
                            try {
                                System.out.println("in main deleting");
                                while (!Selectingoption.stk.empty()) {

                                    int z = (int) Selectingoption.stk.peek();
                                    Message delmsg;

                                    delmsg = MailExtractror.messages[z];
                                    try {
                                        delmsg.setFlag(Flags.Flag.DELETED, true);
                                        System.out.println("deleting " + z + ":  " + delmsg.getSubject());
                                    } catch (MessagingException ex) {
                                        System.out.println("Archiving hampers");
                                    }
                                    Selectingoption.stk.pop();
                                }

                                inbox.close(false);
                                store.close();
                                window.setScene(scene);
                                Selectingoption.pppp = false;
                                Selectingoption.stk.clear();

                            } catch (MessagingException ex) {

                            }
                        });
                        Label welcom = new Label("Welcome to EmailExtractor");
                        welcom.setStyle("-fx-border-color: #C1CDC6; -fx-font-size: 25;-fx-highlight-text-fill: white;-fx-underline: true;");
                        VBox out = new VBox(5);
                        out.setPadding(new Insets(5, 5, 5, 5));
                        out.getChildren().add(logout);
                        out.setAlignment(Pos.TOP_CENTER);

                        VBox selcombo = new VBox(5);
                        selcombo.setPadding(new Insets(5, 5, 5, 5));
                        selcombo.getChildren().add(selectcombo);
                        selcombo.setAlignment(Pos.CENTER);

                        VBox combo = new VBox(5);
                        combo.setPadding(new Insets(5, 5, 5, 5));
                        combo.getChildren().add(actioncombo);
                        combo.setAlignment(Pos.CENTER);
                        Label gap = new Label("\n\n\n");
                        Label gap2 = new Label("\n\n\n");
                        Label gap3 = new Label("\n\n\n");

                        HBox sp = new HBox(5);
                        sp.setPadding(new Insets(5, 5, 5, 5));
                        sp.getChildren().add(gap);
                        sp.setAlignment(Pos.CENTER);
                        VBox sp2 = new VBox(5);
                        sp.setPadding(new Insets(5, 5, 5, 5));
                        sp.getChildren().add(gap2);
                        sp.setAlignment(Pos.TOP_CENTER);

                        VBox set11 = new VBox(5);
                        set11.setPadding(new Insets(5, 5, 5, 5));
                        set11.getChildren().addAll(serch_subject, serch_date, checkattach);
                        set11.setAlignment(Pos.TOP_LEFT);
                        VBox set12 = new VBox(5);
                        set11.setPadding(new Insets(5, 5, 5, 5));
                        set11.getChildren().add(search);
                        set11.setAlignment(Pos.TOP_CENTER);
                        VBox left = new VBox(5);
                        left.setPadding(new Insets(5, 5, 5, 5));
                        System.out.println("lable ading");
                        left.getChildren().addAll(set11, set12, gap3, dirrr, new Label("\n\n"), gap, new Label("\n\n"), gap2, new Label("\n"), out);
                        left.setAlignment(Pos.TOP_CENTER);
                        System.out.println("lable aded");
                        HBox topline = new HBox(5);
                        topline.setPadding(new Insets(5, 5, 5, 5));
                        topline.getChildren().addAll(new Label("                                                                                   "), welcom, topgap);
                        topline.setAlignment(Pos.TOP_LEFT);
//Launching 2nd page
                        BorderPane ot = new BorderPane(table, topline, left, null, null);

                        scene2nd = new Scene(ot, 900, 550);
                        window.setScene(scene2nd);

                    } catch (Exception ex) {
                        Label lWrongId = new Label("Gmail Id or Passsword is not Correct.");
                        VBox layoutInIng = new VBox(10);
                        Label l4 = new Label("\n\n\n\n\n");
                        layoutInIng.setPadding(new Insets(20, 20, 20, 20));
                        layoutInIng.getChildren().addAll(l1, email, l2, password, button, l4, lWrongId);
                        scene3 = new Scene(layoutInIng, 400, 400);
                        window.setScene(scene3);
                        //  ex.printStackTrace();
                    }

                } else {
                    Label l3 = new Label("Wrong format of mail.");
                    VBox layoutgfalse = new VBox(10);
                    Label l4 = new Label("\n\n\n\n\n");
                    layoutgfalse.setPadding(new Insets(20, 20, 20, 20));
                    layoutgfalse.getChildren().addAll(l1, email, l2, password, button, l4, l3);
                    scene2 = new Scene(layoutgfalse, 400, 400);
                    window.setScene(scene2);
                }
            } catch (Exception tr) {
                System.out.println("mailextractror.MailExtractror.start() is hamperd, no id pass taken");
            }

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(l1, email, l2, password, button);

        scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

    static boolean hasAttachments(Message msg) throws MessagingException, IOException {
        if (msg.isMimeType("multipart/mixed")) {
            Multipart mp = (Multipart) msg.getContent();
            if (mp.getCount() > 1) {
                return true;
            }
        }
        return false;
    }

    private boolean isgmail(TextField input, String message) {

        String g = "@gmail.com";
        char[] ar = message.toCharArray();
        int i = ar.length - 1;
        if (ar[i] == 'm' && ar[i - 1] == 'o' && ar[i - 2] == 'c' && ar[i - 3] == '.' && ar[i - 4] == 'l' && ar[i - 5] == 'i' && ar[i - 6] == 'a' && ar[i - 7] == 'm' && ar[i - 8] == 'g' && ar[i - 9] == '@') {
            return true;
        } else {
            return false;
        }
    }

}
