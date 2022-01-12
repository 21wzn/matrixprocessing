package matrixprocessing;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DialogBox {

    String message;

    public DialogBox(String message) {
        this.message = message;
    }

    public void show(){
        Stage dialogStage = new Stage();
        Pane dialogPane = new Pane();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30,30,30,30));
        grid.setVgap(40);
        grid.add(new Text(message), 0,0);

        Button ok = new Button("ok");
        ok.setOnAction(e->{
            dialogStage.hide();
        });
        grid.add(ok, 0,1);
        dialogPane.getChildren().add(grid);
        dialogStage.setScene(new Scene(dialogPane,400,200));
        dialogStage.show();
    }

}
