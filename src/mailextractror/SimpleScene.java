/*
This is a simle class to generate dialogue box to say somethings to user.

*/
package mailextractror;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SimpleScene {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(300);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        HBox lay = new HBox(10);
        lay.getChildren().addAll(yesButton, noButton);
        lay.setAlignment(Pos.CENTER);
        //Add buttons
        layout.getChildren().addAll(label, lay);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }
}
