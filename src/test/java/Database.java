import java.sql.*;

public class Database {

    // Data to connect to the database:
    private String user = "dgabor";
    private String password = "root";
    private String connectionUrl = "jdbc:mysql://localhost:3306/agdb?useSSL=false&verifyServerCertificate=false&allowPublicKeyRetrieval=true";

    // Get value with one 'where':
    String GetStringValueFromDatabase(String select, String from, String where, String whereValue) throws SQLException {
        String value = null;
        Connection connection = DriverManager.getConnection(connectionUrl, user, password);
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("select " + select + " from " + from + " where " + where + "='" + whereValue + "'");
        while(result.next()) {
            value = result.getString(select);
        }
        connection.close();
        return value;
    }

    // // Get value with two 'where':
    String GetStringValueFromDatabase(String select, String from, String where, String whereValue, String where1, String whereValue1) throws SQLException {
        String value = null;
        Connection connection = DriverManager.getConnection(connectionUrl, user, password);
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("select " + select + " from " + from + " where " + where + "='" + whereValue + "' and " + where1 + "='" + whereValue1 + "'");
        while(result.next()) {
            value = result.getString(select);
        }
        connection.close();
        return value;
    }

    // Get value with three 'where':
    String GetStringValueFromDatabase(String select, String from, String where, String whereValue, String where1, String whereValue1, String where2, String whereValue2) throws SQLException {
        String value = null;
        Connection connection = DriverManager.getConnection(connectionUrl, user, password);
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("select " + select + " from " + from + " where " + where + "='" + whereValue + "' and " + where1 + "='" + whereValue1 + "' and " + where2 + "='" + whereValue2 + "'");

        while(result.next()) {
            value = result.getString(select);
        }

        connection.close();
        return value;
    }

    // Get count of element in table:
    Integer GetCountFromTable(String table) throws SQLException {
        Integer value = null;
        Connection connection = DriverManager.getConnection(connectionUrl, user, password);
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("select count(*) from " + table);
        while(result.next()) {
            value = result.getInt(1);
        }

        connection.close();
        return value;
    }

    // Get count of element in table with where:
    Integer GetCountFromTableWhere(String table, String where, String whereValue) throws SQLException {
        Integer value = null;
        Connection connection = DriverManager.getConnection(connectionUrl, user, password);
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("select count(*) from " + table + " where " + where + " = '" + whereValue + "'");
        while(result.next()) {
            value = result.getInt(1);
        }

        connection.close();
        return value;
    }
}
