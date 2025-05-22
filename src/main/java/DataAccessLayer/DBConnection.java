package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Furnizează conexiuni la baza de date pentru sistemul de gestionare a comenzilor.
 * Implementează un model de conexiune singleton folosind metoda statică getConnection().
 *
 * @author Florea Mihai
 * @version 1.1
 * @since 1.0
 */
public class DBConnection {
    /**
     * URL-ul de conectare la baza de date MySQL
     */
    private static final String URL = "jdbc:mysql://localhost:3306/orders_management";

    /**
     * Numele utilizatorului pentru autentificare în baza de date
     */
    private static final String USER = "root";

    /**
     * Parola pentru autentificare în baza de date
     */
    private static final String PASSWORD = "AdelinMihai06*";

    /**
     * Obține o conexiune la baza de date orders_management.
     *
     * @return Obiect Connection reprezentând conexiunea la baza de date
     * @throws SQLException dacă apar erori în timpul deschiderii conexiunii
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}