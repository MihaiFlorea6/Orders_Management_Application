package Model;

/**
 * Reprezintă o comandă în sistemul de gestionare a comenzilor.
 * Conține informații despre client, produs și cantitatea comandată.
 *
 * @author Florea Mihai
 * @version 1.0
 * @since 1.0
 */
public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    /**
     * Constructor pentru crearea unei noi comenzi.
     *
     * @param id ID-ul unic al comenzii
     * @param clientId ID-ul clientului care plasează comanda
     * @param productId ID-ul produsului comandat
     * @param quantity Cantitatea comandată
     * @throws IllegalArgumentException dacă cantitatea este negativă
     */
    public Order(int id, int clientId, int productId, int quantity) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Returnează ID-ul comenzii.
     *
     * @return ID-ul comenzii
     */
    public int getId() {
        return id;
    }

    /**
     * Setează ID-ul comenzii.
     *
     * @param id Noul ID al comenzii
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returnează ID-ul clientului care a plasat comanda.
     *
     * @return ID-ul clientului
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Setează ID-ul clientului care a plasat comanda.
     *
     * @param clientId Noul ID al clientului
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Returnează ID-ul produsului comandat.
     *
     * @return ID-ul produsului
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Setează ID-ul produsului comandat.
     *
     * @param productId Noul ID al produsului
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Returnează cantitatea comandată.
     *
     * @return Cantitatea comandată
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setează cantitatea comandată.
     *
     * @param quantity Noua cantitate
     * @throws IllegalArgumentException dacă cantitatea este negativă
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returnează reprezentarea textuală a comenzii.
     *
     * @return Șir de caractere ce conține ID-ul comenzii, ID-ul clientului,
     *         ID-ul produsului și cantitatea
     */
    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", clientId=" + clientId + ", productId=" + productId + ", quantity=" + quantity + '}';
    }
}