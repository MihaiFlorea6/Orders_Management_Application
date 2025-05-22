package BusinessLayer;

import DataAccessLayer.ClientDAO;
import Model.Client;

import java.util.List;

/**
 * Clasa ClientService gestionează logica de afaceri pentru operațiile cu clienți.
 * Aceasta validează datele înainte de a apela metodele din {@link ClientDAO}.
 */
public class ClientService {
    private final ClientDAO clientDAO;

    /**
     * Constructorul inițializează obiectul ClientDAO necesar pentru interacțiunea cu baza de date.
     */
    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    /**
     * Adaugă un client nou după ce validează numele și emailul.
     *
     * @param name  Numele clientului
     * @param email Emailul clientului
     * @return true dacă inserarea s-a realizat cu succes, false în caz contrar
     */
    public boolean addClient(String name, String email) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            System.out.println("Numele și emailul nu pot fi goale.");
            return false;
        }

        Client client = new Client(0, name, email);
        clientDAO.insert(client);
        return true;
    }

    /**
     * Returnează o listă cu toți clienții din baza de date.
     *
     * @return Lista de obiecte {@link Client}
     */
    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Actualizează datele unui client existent, după validarea datelor.
     *
     * @param id    ID-ul clientului de actualizat
     * @param name  Noul nume al clientului
     * @param email Noul email al clientului
     * @return true dacă actualizarea s-a realizat cu succes, false în caz contrar
     */
    public boolean updateClient(int id, String name, String email) {
        if (id <= 0 || name == null || name.isEmpty() || email == null || email.isEmpty()) {
            System.out.println("Date invalide pentru update.");
            return false;
        }

        Client client = new Client(id, name, email);
        clientDAO.update(client);
        return true;
    }

    /**
     * Șterge un client din baza de date, dacă ID-ul este valid.
     *
     * @param id ID-ul clientului de șters
     * @return true dacă ștergerea s-a realizat, false dacă ID-ul este invalid
     */
    public boolean deleteClient(int id) {
        if (id <= 0) {
            System.out.println("ID invalid pentru ștergere.");
            return false;
        }

        clientDAO.delete(id);
        return true;
    }
}
