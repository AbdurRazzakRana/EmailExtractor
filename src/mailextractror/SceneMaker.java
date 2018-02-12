/*
This class will make the third page based on searched parameter.
And show user found messages of searched items and some action command on them.
user can download files, CSV reports, common reply, set directory, goback, logout, Archive message form here .
*/

package mailextractror;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import static mailextractror.MailExtractror.inbox;
import static mailextractror.MailExtractror.scene;
import static mailextractror.MailExtractror.store;
import static mailextractror.MailExtractror.window;

public class SceneMaker {

    TableView table;
    String selfid, selfpass;
    static int x = 0;
    Stage win;
    Scene sin;
    File ddir = new File("");
    Button goback;
    Button directory;
    ComboBox actioncombo, selectcombo;
    Button logout;
    CheckBox allornot;

    public SceneMaker(TableView t, Stage stg, Scene si) {
        this.table = t;

        this.win = stg;
        this.sin = si;
    }

    public Scene MakingScene() {
        Label welcom = new Label("Welcome to EmailExtractor");
        welcom.setStyle("-fx-border-color: #C1CDC6; -fx-font-size: 25;-fx-highlight-text-fill: white;-fx-underline: true;");
        Label topgap = new Label("\n\n");
//go back button that will takes user to first page;
        goback = new Button("Go Back");
        goback.setMinWidth(100);
        goback.setDisable(true);
        goback.setAlignment(Pos.CENTER_LEFT);
        goback.setOnAction(ww -> {

            MailExtractror.recentMessages = MailExtractror.FirstMessages;
            TableMaker.ans = MailExtractror.firstarrselect;

            win.setScene(MailExtractror.scene2nd);
        });
//If user forgot to set direcotry, he can do it form here also.
        directory = new Button("Set Directory");
        directory.setMinWidth(100);
        directory.setOnAction(x -> {
            System.out.println(MailExtractror.selectedDirectory);
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Select The Location Where Files to be Downloaded");
            chooser.setInitialDirectory(MailExtractror.defaultDirectory);
            MailExtractror.selectedDirectory = chooser.showDialog(MailExtractror.window);
            if (MailExtractror.selectedDirectory == null) {
            } else {
                MailExtractror.defaultDirectory = MailExtractror.selectedDirectory;
            }

        });

        VBox dirrr = new VBox(5);
        dirrr.setPadding(new Insets(5, 5, 5, 5));
        dirrr.getChildren().add(directory);
        dirrr.setAlignment(Pos.TOP_CENTER);

//select all messages from the found massages or none.
        allornot = new CheckBox();
        allornot.setOnAction(errr -> {
            SelectAllorNot sar = new SelectAllorNot(MailExtractror.recenttable);
            if (allornot.isSelected()) {
                sar.selectedtoall(true);
            } else {
                sar.selectedtoall(false);
            }

        });
//Download files or CSV report block.
        actioncombo = new ComboBox();
        actioncombo.setMinWidth(100);
        actioncombo.setValue("Download");
        actioncombo.getItems().addAll(
                //"Get All New Emails Attachment",
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
                            int k = 0;
                            for (int i = 0; i < TableMaker.ans.length; i++) {
                                if (TableMaker.ans[i] == 1) {
                                    k++;
                                    break;
                                }
                            }
                            if (k == 0) {
                                Stage wind = new Stage();
                                wind.setTitle("Downloading Failure");
                                Label tt = new Label(" No Message is Selected!");
                                BorderPane pb = new BorderPane(tt, null, null, null, null);
                                Scene sn = new Scene(pb, 300, 200);
                                wind.setScene(sn);
                                wind.showAndWait();

                            } else {

                                SelectedActionWork.downing = 0;
                                int sz = TableMaker.ans.length;
                                int dans[] = new int[sz];
                                dans = TableMaker.ans;
                                Message[] down = new Message[MailExtractror.recentMessages.length];
                                down = MailExtractror.recentMessages;

                                if (MailExtractror.selectedDirectory == null) {
                                } else {
                                    MailExtractror.defaultDirectory = MailExtractror.selectedDirectory;
                                }

                                ddir = MailExtractror.defaultDirectory;
//Download attachments to selected directory block;                                
                                SelectedActionWork selectedactionwork = new SelectedActionWork(ddir, down, dans, t1);
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
//Showing download bar that indicates the amounts of download.
                                DownloadBar dbar = new DownloadBar();
                                dbar.barsetter(maxsize);
                                SelectedActionWork.downing = 0;
                            }
                        } else if (t1 == "CSV Reports") {
                            int k = 0;
                            for (int i = 0; i < TableMaker.ans.length; i++) {
                                if (TableMaker.ans[i] == 1) {
                                    k++;
                                    break;
                                }
                            }
                            if (k == 0) {
                                Stage wind = new Stage();
                                wind.setTitle("Downloading Failure");
                                Label tt = new Label(" No Message is Selected!");
                                BorderPane pb = new BorderPane(tt, null, null, null, null);
                                Scene sn = new Scene(pb, 300, 200);
                                wind.setScene(sn);
                                wind.showAndWait();

                            } else {
//Download CSV report to selected directory

                                CSVScene csvpage = new CSVScene();
                                csvpage.starter();
                            }

                        } else {

                        }

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
        selectcombo = new ComboBox();
        selectcombo.setMinWidth(100);
        selectcombo.getItems().addAll(
                "Reply",
                "Archive",
                "Again"
        );
        selectcombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {

                Selectingoption sel = new Selectingoption();
                try {
                    sel.Selection(t1);
                    // selectedactionwork.SelectedItem(t1);
                } catch (MessagingException ex) {
                    System.out.println(".changed()");
                }
            }
        });

        selectcombo.setValue("Options");

        logout = new Button("LogOut      ");
        logout.setMinWidth(100);
        logout.setAlignment(Pos.CENTER_LEFT);
        logout.setOnAction(e1 -> {
            try {
                //  MailExtractror.ckh = false;
                // MailExtractror.ck = false;
                //MailExtractror.schk = false;

                //MailExtractror.fsrch = 0;
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
                System.out.println("mailextractror.SceneMaker.MakingScene()");
            }

        });
        VBox out = new VBox(5);
        out.setPadding(new Insets(5, 5, 5, 5));
        out.getChildren().add(logout);
        out.setAlignment(Pos.BOTTOM_CENTER);

        VBox selcombo = new VBox(5);
        selcombo.setPadding(new Insets(5, 5, 5, 5));
        selcombo.getChildren().add(selectcombo);
        selcombo.setAlignment(Pos.CENTER);

        VBox combo = new VBox(5);
        combo.setPadding(new Insets(5, 5, 5, 5));
        combo.getChildren().add(actioncombo);
        combo.setAlignment(Pos.CENTER);
        Label gap = new Label("\n\n\n");
        Label gap2 = new Label("\n\n\n\n");
        Label gap3 = new Label("\n\n\n\n\n\n");

        HBox sp = new HBox(5);
        sp.setPadding(new Insets(5, 5, 5, 5));
        sp.getChildren().add(gap);
        sp.setAlignment(Pos.CENTER);
        VBox sp2 = new VBox(5);
        sp.setPadding(new Insets(5, 5, 5, 5));
        sp.getChildren().add(gap2);
        sp.setAlignment(Pos.CENTER);

        VBox set11 = new VBox(5);
        set11.setPadding(new Insets(5, 5, 5, 5));
        set11.getChildren().addAll(goback);
        set11.setAlignment(Pos.CENTER);
        VBox left = new VBox(5);
        left.setPadding(new Insets(5, 5, 5, 5));
        System.out.println("lable ading");
        left.getChildren().addAll(set11, gap3, dirrr, gap, combo, selcombo, gap2, out);
        left.setAlignment(Pos.CENTER);
        System.out.println("lable aded");
        HBox topline = new HBox(5);
        topline.setPadding(new Insets(5, 5, 5, 5));
        topline.getChildren().addAll(allornot, new Label("                                                                           "), welcom, topgap);
        topline.setAlignment(Pos.TOP_LEFT);

        BorderPane root = new BorderPane(table, topline, left, null, null);

        Scene srcscene = new Scene(root, 900, 550);

        return srcscene;
    }

    public void disableAll(boolean state) {
        goback.setDisable(state);
        directory.setDisable(state);
        actioncombo.setDisable(state);
        selectcombo.setDisable(state);
        allornot.setDisable(state);
        logout.setDisable(state);

    }

}
