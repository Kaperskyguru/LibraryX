package libraryx.ui.listMember;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import libraryx.database.databasehandler;

public class List_MemberController implements Initializable {
    
    @FXML
    private TableView<Member> tableViewCol;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> IDCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> email;
    databasehandler handler;
    
    ObservableList<Member> list = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = databasehandler.getInstance();
        initCol();
        loadData();
    }
    
    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    
    public static class Member {
        
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
        
        public Member(String name, String id, String mobile, String email) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
            
        }
        
        public String getName() {
            return name.get();
        }
        
        public String getId() {
            return id.get();
        }
        
        public String getMobile() {
            return mobile.get();
        }
        
        public String getEmail() {
            return email.get();
        }
        
    }
    
    private void loadData() {
        String query = "SELECT * FROM MEMBER";
        ResultSet rs = handler.executeQuery(query);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("id");
                String mobile = rs.getString("mobile");
                String email1 = rs.getString("email");
                Member members = new Member(name, id, mobile, email1);
                list.add(members);
            }
        } catch (SQLException ex) {
            Logger.getLogger(List_MemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableViewCol.getItems().setAll(list);
    }
    
}
