/*
This class will show a dialogue box to user that which perameters he want to set to make the csv report.
after taking the parameter it will call the ReportDownloader class to download the CSV file.

*/
package mailextractror;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CSVScene {
    
    void starter(){
    Stage win = new Stage();
    
    CheckBox cksub = new CheckBox();
    CheckBox ckfrom = new CheckBox();
    CheckBox ckdate = new CheckBox();
    CheckBox ckattach = new CheckBox();
    
    Label subject = new Label("Subject");
    Label form = new Label("From ID");
    
    Label date = new Label("Sent Date");
    
    Label attch = new Label("Attachment Size");
   
    
    TextField csvname = new TextField();
    Label setname = new Label("Set The Name of File");
    
    Button okey = new Button("Ok");
    okey.setOnAction(ew ->{
        if(csvname.getText().length()!= 0){
            ReportDownloader RD = new ReportDownloader(csvname.getText(), cksub.isSelected(), ckfrom.isSelected(), ckdate.isSelected(),ckattach.isSelected(), TableMaker.ans, MailExtractror.recenttable );
    RD.setPriority(3);
    RD.start();
    win.close();
    
        
        }
    
    });
    okey.setAlignment(Pos.BOTTOM_LEFT);
    
    
    GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        grid.add(setname,  1, 0);
        grid.add(csvname, 0,0);

        grid.add(cksub, 0, 1);
        grid.add(subject, 1, 1);
        
        grid.add(ckfrom, 0, 2);
        grid.add(date, 1, 2);
        
        grid.add(ckdate, 0, 3);
        grid.add(form, 1, 3);
        
        grid.add(ckattach, 0, 4);
        grid.add(attch, 1, 4);
        
        grid.add(okey, 5, 6);


    Scene scsvn = new Scene(grid, 400,250);
    win.setTitle("Select Items you want in your CSV Report");
    win.setScene(scsvn);

    win.showAndWait();
    
    }
}
