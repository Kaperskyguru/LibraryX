package libraryx.ui.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.*;
import libraryx.settings.Preferences;
import libraryx.ui.main.MainController;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    Preferences preferences;
    @FXML
    private Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preferences = Preferences.getPreferences();
        // TODO
    }

    @FXML
    private void loadLogin(ActionEvent event) {
        titleLabel.setStyle("-fx-background-color:black");
        titleLabel.setText("LibraryX Login");
        String uname = username.getText();
        String pword = DigestUtils.sha1Hex(password.getText().toLowerCase());

        if (uname.equals(preferences.getUsername()) && pword.equals(preferences.getPassword())) {
            // Open Main
            CloseStage();
        } else {
            titleLabel.setStyle("-fx-background-color:#d32f2f");
            titleLabel.setText("Invalid Credentials");
        }
    }

    @FXML
    private void loadCancel(ActionEvent event) {
        System.exit(0);
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/libraryx/ui/main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library X ");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void CloseStage() {
        ((Stage) username.getScene().getWindow()).close();
        loadMain();
    }

}
