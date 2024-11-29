package Test;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class testController {

    @FXML
    private Button myButton;

    @FXML
    private void handleButtonClick() {
        System.out.println("Button clicked!");
    }
}
