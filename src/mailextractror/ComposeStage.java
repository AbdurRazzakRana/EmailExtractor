/*
This class will show a window to type message and add attachments.
its will takes it as perametters, after taking it will call SendMail class
to send mails to selected reciepents containg this message and attchments.

*/
package mailextractror;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


class ComposeStage {
    private Desktop desktop = Desktop.getDesktop();
    static String[] attachFiles;
    static String sendMailSubject;
    int type = 0;
    
    void displaytab (){

    Stage window = new Stage(); 
        final FileChooser fileChooser = new FileChooser();
        window.setTitle("Send common Mail to all selected Reciepents");

        Label label = new Label("Subject : ");
        label.setStyle("-fx-highlight-text-fill: green;-fx-underline: true;");
        TextField sub = new TextField();
        sub.setPromptText("Type the subject here");
        
        TextArea textpart = new TextArea();
       
        textpart.setPromptText("Write your Statements");
        
   
        Button addattachment = new Button ("Add attachments");
        addattachment.setOnAction(e ->{
            try{
        int sz = 0;
        List<File> list =
                        fileChooser.showOpenMultipleDialog(window);
         sz = list.size();
        attachFiles = new String[sz];
                    if (list != null) {
                        int i = 0;
                        type++;
                        for (File file : list) {
                            String s = file.getPath();
                            attachFiles[i] = s;
                            System.out.println("path is: "+s);
                            System.out.println("\n");
                            i++;
                        }
                    }
            }catch (NullPointerException po){
            
            }
        
        });
        Button send = new Button ("Send");
        send.setAlignment(Pos.BOTTOM_CENTER);
        send.setOnAction(er ->{
        
        if(sub.getText().isEmpty()){
        sendMailSubject = " ";
        }
        else{sendMailSubject = sub.getText();}
         try {
             
//Calling SendMail to send message
             SendMail sendfile = new SendMail("smtp.gmail.com","587",sendMailSubject, textpart.getText(), attachFiles,
                     TableMaker.ans, MailExtractror.recenttable, MailExtractror.selfID, MailExtractror.selfPass, type);
             sendfile.setPriority(3);
             sendfile.start();

            
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
        Label tt = new Label("          Message Sent Successfuly!");
            
            Scene sn = new Scene (tt , 200,200);
            window.setScene(sn);
        try {
            Thread.sleep(3000);
            window.close();
        } catch (InterruptedException ex) {
            System.out.println("Sent with no exteption");
        }
        
        
        });

       HBox top = new HBox(10);
       top.setPadding(new Insets(5,5,5,5));
       top.getChildren().addAll(label,sub);
       top.setAlignment(Pos.TOP_LEFT);
       
       VBox side = new VBox(10);
       side.setPadding(new Insets(5,5,5,5));
       side.getChildren().addAll(addattachment,new Label("\n\n\n\n\n\n\n\n\n\n\n"), send);
       side.setAlignment(Pos.CENTER_RIGHT);
       
        BorderPane bp = new BorderPane(textpart, top,side, null,null);
        
        Scene scenecompose = new Scene(bp,900,450);
        window.setScene(scenecompose);
        window.showAndWait();  
    }
}
