package DataAccessLayer;

import Model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care implementează operațiile CRUD (Create, Read, Update, Delete) pentru entitatea Client în baza de date.
 * Utilizează JDBC pentru interacțiunea cu baza de date.
 *
 * @author Florea Mihai
 * @version 1.2
 * @since 1.0
 */
public class ClientDAO {

    /**
     * Inserează un nou client în baza de date.
     *
     * @param client Obiectul Client ce conține datele de inserat
     * @throws SQLException dacă apare o eroare la accesul bazei de date
     */
    public void insert(Client client) {
        String query = "INSERT INTO client (name, email) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Eroare la inserare client: " + e.getMessage());
        }
    }

    /**
     * Returnează o listă cu toți clienții din baza de date.
     *
     * @return Lista de clienți
     * @throws SQLException dacă apare o eroare la accesul bazei de date
     */
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                clients.add(new Client(id, name, email));
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citire clienți: " + e.getMessage());
        }

        return clients;
    }

    /**
     * Caută un client după ID-ul său.
     *
     * @param id ID-ul clientului căutat
     * @return Obiectul Client găsit sau null dacă nu există
     * @throws SQLException dacă apare o eroare la accesul bazei de date
     */
    public Client findById(int id) {
        String query = "SELECT * FROM client WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                return new Client(id, name, email);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la căutarea clientului cu id=" + id + ": " + e.getMessage());
        }

        return null;
    }

    /**
     * Șterge un client din baza de date după ID.
     *
     * @param id ID-ul clientului de șters
     * @throws SQLException dacă apare o eroare la accesul bazei de date
     */
    public void delete(int id) {
        String query = "DELETE FROM client WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Eroare la ștergere client: " + e.getMessage());
        }
    }

    /**
     * Actualizează datele unui client existent în baza de date.
     *
     * @param client Obiectul Client cu datele actualizate
     * @throws SQLException dacă apare o eroare la accesul bazei de date
     */
    public void update(Client client) {
        String query = "UPDATE client SET name = ?, email = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setInt(3, client.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Eroare la actualizare client: " + e.getMessage());
        }
    }
}