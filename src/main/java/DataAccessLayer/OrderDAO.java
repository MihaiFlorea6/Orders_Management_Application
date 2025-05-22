package DataAccessLayer;

import Model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care gestionează operațiile de acces la baza de date pentru entitatea.
 * Oferă metode pentru inserarea, actualizarea, ștergerea și căutarea comenzilor.
 */
public class OrderDAO {

    /**
     * Inserează o comandă nouă în baza de date.
     *
     * @param order Comanda care urmează să fie adăugată
     */
    public void insert(Order order) {
        String query = "INSERT INTO orders (client_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returnează o listă cu toate comenzile din baza de date.
     *
     * @return Lista de comenzi
     */
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                orders.add(new Order(id, clientId, productId, quantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Șterge o comandă din baza de date pe baza ID-ului său.
     *
     * @param id ID-ul comenzii care urmează să fie ștearsă
     */
    public void delete(int id) {
        String query = "DELETE FROM orders WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualizează o comandă existentă în baza de date.
     *
     * @param order Obiectul Order cu datele actualizate
     */
    public void update(Order order) {
        String query = "UPDATE orders SET client_id = ?, product_id = ?, quantity = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setInt(4, order.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Găsește o comandă în baza de date după ID.
     *
     * @param id ID-ul comenzii căutate
     * @return Obiectul Order dacă a fost găsit, altfel null
     */
    public Order findById(int id) {
        String query = "SELECT * FROM orders WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int clientId = rs.getInt("client_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                return new Order(id, clientId, productId, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
