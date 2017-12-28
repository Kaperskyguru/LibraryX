package libraryx.ui.main;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libraryx.database.databasehandler;

public class MainController implements Initializable {
    
    databasehandler handler;
    boolean isReadyForSubmmission;
    
    @FXML
    private HBox book_info;
    @FXML
    private HBox member_info;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private TextField BookIDInput;
    @FXML
    private Text bookStatus;
    @FXML
    private TextField memberIDInput;
    @FXML
    private Text memberName;
    @FXML
    private Text memberMobile;
    @FXML
    private JFXTextField bookID;
    @FXML
    private ListView<String> issueDataList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(book_info, 1);
        JFXDepthManager.setDepth(member_info, 1);
        handler = databasehandler.getInstance();
        
    }
    
    @FXML
    private void loadAddMember(ActionEvent event) {
        String loc = "/libraryx/ui/addMember/add_Member.fxml";
        loadWindow(loc, "Add New Member");
    }
    
    @FXML
    private void loadAddBook(ActionEvent event) {
        String loc = "/libraryx/ui/addBook/add_book.fxml";
        loadWindow(loc, "Add New Book");
    }
    
    @FXML
    private void loadMemberTable(ActionEvent event) {
        String loc = "/libraryx/ui/listMember/list_Member.fxml";
        loadWindow(loc, "View Members");
    }
    
    @FXML
    private void loadBookTable(ActionEvent event) {
        String loc = "/libraryx/ui/listbook/book_list.fxml";
        loadWindow(loc, "View Books");
    }
    
    @FXML
    private void loadSettings(ActionEvent event) {
        String loc = "/libraryx/settings/Settings.fxml";
        loadWindow(loc, "Settings");
    }
    
    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void loadBookInfo(ActionEvent event) {
        clearBookCache();
        Boolean flag = false;
        try {
            String id = BookIDInput.getText();
            String qu = "SELECT * FROM BOOK WHERE id = '" + id + "'";
            ResultSet r = handler.executeQuery(qu);
            while (r.next()) {
                String bTitle = r.getString("title");
                String bAuthor = r.getString("author");
                Boolean bStatus = r.getBoolean("isAvail");
                
                bookAuthor.setText(bAuthor);
                bookName.setText(bTitle);
                String status = (bStatus) ? "Available" : "Not Availble";
                bookStatus.setText(status);
                
                flag = true;
            }
            if (!flag) {
                bookName.setText("No Such Book Available");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void loadMemberInfo(ActionEvent event) {
        clearMemberCache();
        Boolean flag = false;
        try {
            String id = memberIDInput.getText();
            String qu = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
            ResultSet r = handler.executeQuery(qu);
            while (r.next()) {
                String bTitle = r.getString("name");
                String bAuthor = r.getString("mobile");
                
                memberName.setText(bTitle);
                memberMobile.setText(bAuthor);
                
                flag = true;
            }
            if (!flag) {
                memberName.setText("No Such Member Available");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void clearBookCache() {
        bookName.setText("");
        bookAuthor.setText("");
        bookStatus.setText("");
    }
    
    void clearMemberCache() {
        memberMobile.setText("");
        memberName.setText("");
        
    }
    
    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String bookID = BookIDInput.getText();
        String memberID = memberIDInput.getText();
        isReadyForSubmmission = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you want to issue the " + bookName.getText() + " \n to " + memberName.getText() + "?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String query = "INSERT INTO ISSUE(bookID,memberID)VALUES("
                    + "'" + bookID + "',"
                    + "'" + memberID + "')";
            
            String query2 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookID + "'";
            
            if (handler.execAction(query) && handler.execAction(query2)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Issued Complete");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Issued Failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Failed");
            alert1.setHeaderText(null);
            alert1.setContentText("Book Issued Cancelled");
            alert1.showAndWait();
        }
        
    }
    
    @FXML
    private void loadBookInfo2(ActionEvent event) {
        ObservableList<String> list = FXCollections.observableArrayList();
        String id = bookID.getText();
        String query = "SELECT * FROM ISSUE WHERE bookID = '" + id + "'";
        ResultSet rs = handler.executeQuery(query);
        try {
            while (rs.next()) {
                String mBookID = id;
                String mMemberID = rs.getString("memberID");
                Timestamp time = rs.getTimestamp("issueTime");
                int mRenewCount = rs.getInt("renew_count");
                
                list.add("Issued Date and Time: " + time.toGMTString());
                list.add("Renew Count: " + mRenewCount);
                list.add("\n");
                
                list.add("Book Information:.".toUpperCase());
                
                query = "SELECT * FROM BOOK WHERE id = '" + mBookID + "'";
                ResultSet r = handler.executeQuery(query);
                while (r.next()) {
                    list.add("Book Name: " + r.getString("title"));
                    list.add("Book ID: " + r.getString("id"));
                    list.add("Book Author: " + r.getString("author"));
                    list.add("Book Publisher: " + r.getString("publisher"));
                }
                list.add("\n");
                list.add("Member Information:. ".toUpperCase());
                
                query = "SELECT * FROM MEMBER WHERE id = '" + mMemberID + "'";
                ResultSet r1 = handler.executeQuery(query);
                while (r1.next()) {
                    list.add("Name: " + r1.getString("name"));
                    list.add("Mobile: " + r1.getString("mobile"));
                    list.add("Email: " + r1.getString("email"));
                }
            }
            isReadyForSubmmission = true;
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        issueDataList.getItems().setAll(list);
    }
    
    @FXML
    private void loadSubmissionOp(ActionEvent event) {
        String id = bookID.getText();
        if (!isReadyForSubmmission) {
            return;
        }
        String query = "DELETE FROM ISSUE WHERE bookID = '" + id + "'";
        String query2 = "UPDATE BOOK SET isAvail = true WHERE id = '" + id + "'";
        
        if (handler.execAction(query) && handler.execAction(query2)) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Success");
            alert1.setHeaderText(null);
            alert1.setContentText("Book Submission Successfull");
            alert1.showAndWait();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Failed");
            alert1.setHeaderText(null);
            alert1.setContentText("Book Submission Failed");
            alert1.showAndWait();
        }
    }
    
    @FXML
    private void loadRenewOp(MouseEvent event) {
        
        if (!isReadyForSubmmission) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failes");
            alert.setHeaderText(null);
            alert.setContentText("Please selet a book to renew");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you want to Renew the book ?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String query = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count + 1 WHERE bookID = '" + bookID.getText() + "'";
            
            if (handler.execAction(query)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Has Been Renewed");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Renewal Failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Failed");
            alert1.setHeaderText(null);
            alert1.setContentText("Book Renewal Cancelled");
            alert1.showAndWait();
        }
        
    }
    
    @FXML
    private void handleMenuClose(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void handleMenuAddBook(ActionEvent event) {
        String loc = "/libraryx/ui/addBook/add_book.fxml";
        loadWindow(loc, "Add New Book");
    }
    
    @FXML
    private void handleMenuAddMember(ActionEvent event) {
        String loc = "/libraryx/ui/addMember/add_Member.fxml";
        loadWindow(loc, "Add New Member");
    }
    
    @FXML
    private void handleMenuViewBook(ActionEvent event) {
        String loc = "/libraryx/ui/listbook/book_list.fxml";
        loadWindow(loc, "View Books");
    }
    
    @FXML
    private void handleMenuViewMember(ActionEvent event) {
        String loc = "/libraryx/ui/listMember/list_Member.fxml";
        loadWindow(loc, "View Members");
    }
    
    @FXML
    private void handleMenuFullScreen(ActionEvent event) {
        Stage stage = ((Stage) bookID.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }
    
    @FXML
    private void handleMenuSettings(ActionEvent event) {
        String loc = "/libraryx/settings/Settings.fxml";
        loadWindow(loc, "Settings");
    }
    
}
