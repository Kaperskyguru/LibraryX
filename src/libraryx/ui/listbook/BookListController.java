package libraryx.ui.listbook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import libraryx.database.databasehandler;

public class BookListController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableViewCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> IDCol;
    @FXML
    private TableColumn<Book, String> AuthorCol;
    @FXML
    private TableColumn<Book, String> Publisher;
    @FXML
    private TableColumn<Book, Boolean> AvalCol;

    ObservableList<Book> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InitCol();
        LoadData();
    }

    private void InitCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        Publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        AvalCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    
    }

    private void LoadData() {
        databasehandler handler = databasehandler.getInstance();
        String query = "SELECT * FROM BOOK";
        ResultSet rs = handler.executeQuery(query);
        try {
            while (rs.next()) {
                String title = rs.getString("title");
                String id = rs.getString("id");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");
                list.add(new Book(title, author, id, publisher, avail));
            }

        } catch (SQLException e) {
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, e);
        }

        tableViewCol.getItems().setAll(list);
    }

    public static class Book {

        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty id;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;

        public Book(String title, String author, String id, String publisher, boolean avail) {

            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.id = new SimpleStringProperty(id);
            this.publisher = new SimpleStringProperty(publisher);
            this.availability = new SimpleBooleanProperty(avail);
        }

        public String getTitle() {
            return title.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getId() {
            return id.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailability() {
            return availability.get();
        }

    }

}
