package Model;

/**
 * Reprezintă un client în sistemul de gestionare a comenzilor.
 * Fiecare client are un identificator unic, un nume și o adresă de email.
 *
 * @author Florea Mihai
 * @version 1.0
 * @since 1.0
 */
public class Client {
    private int id;
    private String name;
    private String email;

    /**
     * Constructor pentru crearea unui nou client.
     *
     * @param id    ID-ul unic al clientului
     * @param name  Numele clientului
     * @param email Adresa de email a clientului
     * @throws IllegalArgumentException dacă email-ul nu este valid
     */
    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Returnează ID-ul clientului.
     *
     * @return ID-ul clientului
     */
    public int getId() {
        return id;
    }

    /**
     * Setează ID-ul clientului.
     *
     * @param id Noul ID al clientului
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returnează numele clientului.
     *
     * @return Numele clientului
     */
    public String getName() {
        return name;
    }

    /**
     * Setează numele clientului.
     *
     * @param name Noul nume al clientului
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returnează adresa de email a clientului.
     *
     * @return Adresa de email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setează adresa de email a clientului.
     *
     * @param email Noua adresă de email
     * @throws IllegalArgumentException dacă email-ul nu este valid
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returnează reprezentarea textuală a clientului.
     *
     * @return Șir de caractere ce conține ID, nume și email
     */
    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", email=" + email + '}';
    }
}