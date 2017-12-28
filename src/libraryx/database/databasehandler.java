package libraryx.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class databasehandler {

    private static databasehandler handler = null;

    private static final String DB_URL = "jdbc:derby:database;create = true";
    private static Connection con = null;
    private static Statement st = null;

    private databasehandler() {
        createConnection();
        setupBookTable();
        setupMemberTable();
        setupIssuedBook();
    }

    public static databasehandler getInstance() {
        if (handler == null) {
            handler = new databasehandler();
        }
        return handler;
    }

    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            con = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setupBookTable() {
        final String TABLE_NAME = "BOOK";
        try {
            st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toLowerCase(), null);
            ResultSet d = dbm.getSchemas();
            if (tables.next() || d.next()) {
                System.out.println("Table " + TABLE_NAME + " already exits. Ready for go!");
            } else {
                st.execute("CREATE TABLE " + TABLE_NAME + "( "
                        + "     id varchar(200) primary key,\n"
                        + "     title varchar(200),\n"
                        + "     author varchar(200),\n"
                        + "     publisher varchar(100),\n"
                        + "     isAvail boolean default true"
                        + " )");
            }
        } catch (SQLException ex) {
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setupIssuedBook() {
        final String TABLE_NAME = "ISSUE";
        try {
            st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toLowerCase(), null);
            ResultSet d = dbm.getSchemas();
            if (tables.next() || d.next()) {
                System.out.println("Table " + TABLE_NAME + " already exits. Ready for go!");
            } else {
                st.execute("CREATE TABLE " + TABLE_NAME + "( "
                        + "     bookID varchar(200) primary key,\n"
                        + "     memberID varchar(200),\n"
                        + "     issueTime timestamp default CURRENT_TIMESTAMP,\n"
                        + "     renew_count integer default 0,\n"
                        + "     FOREIGN KEY (bookID) REFERENCES BOOK(id),"
                        + "     FOREIGN KEY (memberID) REFERENCES MEMBER(id)"
                        + " )");
            }
        } catch (SQLException ex) {
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setupMemberTable() {
        final String TABLE_NAME1 = "MEMBER";
        try {
            st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME1.toLowerCase(), null);
            ResultSet d = dbm.getSchemas();
            if (tables.next() || d.next()) {
                System.out.println("Table " + TABLE_NAME1 + " already exits. Ready for go!");
            } else {
                st.execute("CREATE TABLE " + TABLE_NAME1 + "( "
                        + "     id varchar(200) primary key,\n"
                        + "     name varchar(200),\n"
                        + "     mobile varchar(20),\n"
                        + "     email varchar(100)\n"
                        + " )");
            }

        } catch (SQLException ex) {
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultSet executeQuery(String query) {
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage() + " Occurred", "Error Occurred", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
        }
        return rs;
    }

    public boolean execAction(String query) {
        try {
            st = con.createStatement();
            st.execute(query);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage() + " Occurred", "Error Occurred", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(databasehandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {

        }
    }

}
