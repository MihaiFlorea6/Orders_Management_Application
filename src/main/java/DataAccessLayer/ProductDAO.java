package DataAccessLayer;

import Model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care se ocupă de operațiile de acces la baza de date pentru entitatea.
 * Permite gestionarea produselor prin metode CRUD: creare, citire, actualizare și ștergere.
 */
public class ProductDAO {

    /**
     * Inserează un produs nou în baza de date.
     *
     * @param product Produsul care trebuie adăugat
     */
    public void insert(Product product) {
        String query = "INSERT INTO product (name, quantity) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returnează o listă cu toate produsele existente în baza de date.
     *
     * @return Lista de produse
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                products.add(new Product(id, name, quantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Șterge un produs din baza de date, pe baza ID-ului acestuia.
     *
     * @param id ID-ul produsului care trebuie șters
     */
    public void delete(int id) {
        String query = "DELETE FROM product WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualizează datele unui produs existent în baza de date.
     *
     * @param product Obiectul Product cu datele actualizate
     */
    public void update(Product product) {
        String query = "UPDATE product SET name = ?, quantity = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.setInt(3, product.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Caută și returnează un produs din baza de date pe baza ID-ului.
     *
     * @param id ID-ul produsului căutat
     * @return Obiectul Product dacă a fost găsit, altfel null
     */
    public Product findById(int id) {
        String query = "SELECT * FROM product WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                return new Product(id, name, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
