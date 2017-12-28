package libraryx.ui.addMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import libraryx.database.databasehandler;

public class Add_MemberController implements Initializable {

    databasehandler handler;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField member_id;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton btnaddMember;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = databasehandler.getInstance();
    }

    private void addMember() {
        String mName = name.getText();
        String mMember_id = member_id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();

        Boolean flag = mName.isEmpty() || mMember_id.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty();
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("All Field Required");
            alert.showAndWait();
            return;
        }

        String addQuery = "INSERT INTO MEMBER VALUES ("
                + "'" + mMember_id + "',"
                + "'" + mName + "',"
                + "'" + mMobile + "',"
                + "'" + mEmail + "'"
                + ")";
        if (handler.execAction(addQuery)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successful");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) name.getScene().getWindow()).close();
    }

    @FXML
    private void addMemberEvent(ActionEvent event) {
        addMember();
    }

}
