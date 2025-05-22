package Model;

/**
 * Reprezintă un produs în sistemul de gestionare a stocurilor.
 * Fiecare produs are un identificator unic, un nume și o cantitate disponibilă în stoc.
 *
 * @author Florea Mihai
 * @version 1.0
 * @since 1.0
 */
public class Product {
    private int id;
    private String name;
    private int quantity;

    /**
     * Constructor pentru crearea unui nou produs.
     *
     * @param id ID-ul unic al produsului
     * @param name Numele produsului
     * @param quantity Cantitatea disponibilă în stoc
     * @throws IllegalArgumentException dacă cantitatea este negativă
     */
    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Returnează ID-ul produsului.
     *
     * @return ID-ul produsului
     */
    public int getId() {
        return id;
    }

    /**
     * Setează ID-ul produsului.
     *
     * @param id Noul ID al produsului
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returnează numele produsului.
     *
     * @return Numele produsului
     */
    public String getName() {
        return name;
    }

    /**
     * Setează numele produsului.
     *
     * @param name Noul nume al produsului
     * @throws IllegalArgumentException dacă numele este null sau gol
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returnează cantitatea disponibilă în stoc.
     *
     * @return Cantitatea disponibilă
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setează cantitatea disponibilă în stoc.
     *
     * @param quantity Noua cantitate
     * @throws IllegalArgumentException dacă cantitatea este negativă
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returnează reprezentarea textuală a produsului.
     *
     * @return Șir de caractere ce conține ID-ul, numele și cantitatea produsului
     */
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", quantity=" + quantity + '}';
    }
}