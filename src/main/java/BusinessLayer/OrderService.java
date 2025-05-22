package BusinessLayer;

import DataAccessLayer.OrderDAO;
import DataAccessLayer.ProductDAO;
import Model.Order;
import Model.Product;

import java.util.List;

/**
 * Clasa OrderService gestionează logica de afaceri pentru operațiile legate de comenzi.
 * Se ocupă de validarea comenzilor și de actualizarea stocului produselor.
 */
public class OrderService {
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    /**
     * Constructorul inițializează obiectele DAO pentru comenzi și produse.
     */
    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.productDAO = new ProductDAO();
    }

    /**
     * Adaugă o comandă nouă, după validarea datelor și verificarea stocului.
     * Dacă produsul există și stocul este suficient, inserează comanda și actualizează stocul.
     *
     * @param clientId  ID-ul clientului
     * @param productId ID-ul produsului
     * @param quantity  Cantitatea comandată
     * @return true dacă comanda a fost adăugată cu succes, false altfel
     */
    public boolean addOrder(int clientId, int productId, int quantity) {
        if (clientId <= 0 || productId <= 0 || quantity <= 0) {
            System.out.println("Date invalide pentru creare comandă.");
            return false;
        }

        Product product = productDAO.findById(productId);
        if (product == null) {
            System.out.println("Produsul cu ID " + productId + " nu există.");
            return false;
        }

        if (product.getQuantity() < quantity) {
            System.out.println("Under-stock: produsul \"" + product.getName() + "\" are doar " + product.getQuantity() + " în stoc.");
            return false;
        }

        Order order = new Order(0, clientId, productId, quantity);
        orderDAO.insert(order);

        product.setQuantity(product.getQuantity() - quantity);
        productDAO.update(product);

        System.out.println("Comandă adăugată și stoc actualizat cu succes.");
        return true;
    }

    /**
     * Returnează toate comenzile înregistrate în sistem.
     *
     * @return Lista de obiecte {@link Order}
     */
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    /**
     * Actualizează o comandă existentă și ajustează stocul produsului corespunzător.
     *
     * @param id         ID-ul comenzii de actualizat
     * @param clientId   ID-ul clientului
     * @param productId  ID-ul produsului
     * @param newQuantity Noua cantitate comandată
     * @return true dacă actualizarea s-a realizat cu succes, false altfel
     */
    public boolean updateOrder(int id, int clientId, int productId, int newQuantity) {
        if (id <= 0 || clientId <= 0 || productId <= 0 || newQuantity <= 0) {
            System.out.println("Date invalide pentru actualizare comandă.");
            return false;
        }

        Order oldOrder = orderDAO.findById(id);
        if (oldOrder == null) {
            System.out.println("Comanda cu ID " + id + " nu există.");
            return false;
        }

        Product product = productDAO.findById(productId);
        if (product == null) {
            System.out.println("Produsul nu există.");
            return false;
        }

        int oldQuantity = oldOrder.getQuantity();
        int difference = newQuantity - oldQuantity;

        if (difference > 0) {
            if (product.getQuantity() < difference) {
                System.out.println("Under-stock: nu sunt destule produse pentru a crește comanda.");
                return false;
            }
            product.setQuantity(product.getQuantity() - difference);
        } else if (difference < 0) {
            product.setQuantity(product.getQuantity() + (-difference));
        }

        orderDAO.update(new Order(id, clientId, productId, newQuantity));
        productDAO.update(product);

        System.out.println("Comandă actualizată și stoc ajustat.");
        return true;
    }

    /**
     * Șterge o comandă existentă în funcție de ID-ul acesteia.
     *
     * @param id ID-ul comenzii de șters
     * @return true dacă ștergerea a fost realizată, false dacă ID-ul e invalid
     */
    public boolean deleteOrder(int id) {
        if (id <= 0) {
            System.out.println("ID invalid pentru ștergere comandă.");
            return false;
        }

        orderDAO.delete(id);
        return true;
    }
}
