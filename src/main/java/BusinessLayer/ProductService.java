package BusinessLayer;

import DataAccessLayer.ProductDAO;
import Model.Product;

import java.util.List;

/**
 * Clasa ProductService oferă funcționalitate de nivel business pentru gestionarea produselor.
 * Validează datele și apelează metodele DAO pentru accesul la baza de date.
 */
public class ProductService {
    private final ProductDAO productDAO;

    /**
     * Constructorul inițializează obiectul DAO pentru produse.
     */
    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    /**
     * Adaugă un produs nou în sistem, după validarea datelor.
     *
     * @param name     Numele produsului
     * @param quantity Cantitatea disponibilă
     * @return true dacă produsul a fost adăugat cu succes, false altfel
     */
    public boolean addProduct(String name, int quantity) {
        if (name == null || name.isEmpty() || quantity < 0) {
            System.out.println("Nume invalid sau cantitate negativă.");
            return false;
        }

        Product product = new Product(0, name, quantity);
        productDAO.insert(product);
        return true;
    }

    /**
     * Returnează lista tuturor produselor din sistem.
     *
     * @return Listă de obiecte {@link Product}
     */
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Actualizează un produs existent cu datele furnizate.
     *
     * @param id       ID-ul produsului de actualizat
     * @param name     Noul nume al produsului
     * @param quantity Noua cantitate
     * @return true dacă produsul a fost actualizat cu succes, false altfel
     */
    public boolean updateProduct(int id, String name, int quantity) {
        if (id <= 0 || name == null || name.isEmpty() || quantity < 0) {
            System.out.println("Date invalide pentru actualizare produs.");
            return false;
        }

        Product product = new Product(id, name, quantity);
        productDAO.update(product);
        return true;
    }

    /**
     * Șterge un produs existent din sistem.
     *
     * @param id ID-ul produsului de șters
     * @return true dacă produsul a fost șters, false altfel
     */
    public boolean deleteProduct(int id) {
        if (id <= 0) {
            System.out.println("ID invalid pentru ștergere produs.");
            return false;
        }

        productDAO.delete(id);
        return true;
    }

    /**
     * Caută un produs după ID.
     *
     * @param id ID-ul produsului
     * @return Obiectul {@link Product} dacă a fost găsit, altfel null
     */
    public Product findById(int id) {
        if (id <= 0) {
            System.out.println("ID invalid pentru căutare produs.");
            return null;
        }
        return productDAO.findById(id);
    }
}
