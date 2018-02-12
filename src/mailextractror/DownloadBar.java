/*
This class will show Progress rate of Download.
It will say what percentage of files are downloaded and notify while its completed.
*/
package mailextractror;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DownloadBar {
    static Stage pStage;
    boolean con = true;

    void barsetter(int maxsize) {

        //Stage 
        pStage = new Stage();
        Text txtState = new Text();
        txtState.setFont(Font.font(18));
        txtState.setFill(Color.BLUE);
        Text txtState2 = new Text();

        txtState2.setFont(Font.font(18));
        txtState2.setFill(Color.GREEN);
        ProgressBar pbar = new ProgressBar(0);

        pbar.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    txtState.setText("Calculating Time");
                } else {

                    txtState2.setText("Downloading  Files");
                    txtState.setFill(Color.GREEN);

                }
            }
        });
        pbar.setMinSize(350, 30);
        pbar.progressProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {

                if (t1.doubleValue() == 1) {
                    txtState.setText("Download Completed");
                    txtState.setFill(Color.GREEN);
                    
                   
                } else {

                }
            }

        });

        ProgressIndicator pind = new ProgressIndicator(0);
        pind.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    
                    txtState.setText("Calculating Time");
                    txtState.setFill(Color.BLUE);
                } else {
                    txtState.setText("Downloading Files");

                }
            }
        });
        pind.setMinSize(50, 50);

        Task task = taskCreator(maxsize);
        pbar.progressProperty().unbind();
        pbar.progressProperty().bind(task.progressProperty());
        pind.progressProperty().unbind();
        pind.progressProperty().bind(task.progressProperty());
        new Thread(task).start();

        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(pbar, pind, txtState);
        hbox.setPadding(new Insets(50));
        
        BorderPane root = new BorderPane(hbox, null, null, null, null);

        Scene scenedown = new Scene(root, 700, 130);

        pStage.setTitle("Download Action");
        pStage.setScene(scenedown);
        pStage.setResizable(false);
        pStage.show();
        

    }
//backgroung thread to of progressbar
    private Task taskCreator(int maxsize) {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                int i = 0;

                while (i == 0) {


                    updateProgress(SelectedActionWork.downing, maxsize);

                    updateTitle("updating " + SelectedActionWork.downing);
                    updateMessage("Iteration " + SelectedActionWork.downing);

                    if (SelectedActionWork.downing == maxsize) {
                        updateProgress(SelectedActionWork.downing+1, maxsize);
                        i++;
                    }

                }
                
                return true;
            }
        };
    }

}
