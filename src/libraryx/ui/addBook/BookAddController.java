package libraryx.ui.addBook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import libraryx.database.databasehandler;

public class BookAddController implements Initializable {

    private Label label;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private databasehandler handler;
    @FXML
    private AnchorPane rootPane;

    private void handleButtonAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = databasehandler.getInstance();
        checkData();
    }

    @FXML
    private void addBook(ActionEvent event) {
        String bookTitle = title.getText();
        String bookId = id.getText().toLowerCase();
        String bookPublisher = publisher.getText();
        String bookAuthor = author.getText();
        boolean bool = true;

        if (bookAuthor.isEmpty() || bookId.isEmpty() || bookTitle.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }

        String addQuery = "INSERT INTO BOOK VALUES ("
                + "'" + bookId + "',"
                + "'" + bookTitle + "',"
                + "'" + bookAuthor + "',"
                + "'" + bookPublisher + "',"
                + "'" + bool + "'"
                + ")";
        if (handler.execAction(addQuery)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
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
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    void checkData() {
        String query = "SELECT isAvail FROM BOOK";
        ResultSet rs = handler.executeQuery(query);
        try {
            while (rs.next()) {
                String title = rs.getString("isAvail");
            }

        } catch (SQLException e) {
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
