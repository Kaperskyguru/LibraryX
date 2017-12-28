package libraryx.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

    @FXML
    private JFXTextField txtnDaysWithoutFine;
    @FXML
    private JFXTextField txtFinePerDay;
    @FXML
    private JFXTextField txtUser;
    @FXML
    private JFXTextField txtPass;
    @FXML
    private JFXButton btnSaveSettings;
    @FXML
    private JFXButton cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefualtValue();
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        int nDays = Integer.parseInt(txtnDaysWithoutFine.getText());
        float fine = Float.parseFloat(txtFinePerDay.getText());
        String user = txtUser.getText();
        String pass = txtPass.getText();
        if (pass.length() > 15) {
            txtPass.setPromptText("Password (15 Max)******");
        } else {
            Preferences preferences = Preferences.getPreferences();
            preferences.setnDaysWithoutFine(nDays);
            preferences.setFinePerDay(fine);
            preferences.setUsername(user);
            preferences.setPassword(pass);

            Preferences.writePreferenceToFile(preferences);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) txtnDaysWithoutFine.getScene().getWindow()).close();
    }

    public void initDefualtValue() {
        Preferences preferences = Preferences.getPreferences();
        txtnDaysWithoutFine.setText(String.valueOf(preferences.getnDaysWithoutFine()));
        txtFinePerDay.setText(String.valueOf(preferences.getFinePerDay()));
        txtUser.setText(String.valueOf(preferences.getUsername()));
        txtPass.setText(String.valueOf(preferences.getPassword()));

    }

}
