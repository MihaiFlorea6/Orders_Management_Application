package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Clasa MainFrame reprezintă fereastra principală a aplicației de gestiune a comenzilor.
 * Oferă utilizatorului acces la operații legate de clienți, produse și comenzi.
 */
public class MainFrame extends JFrame {

    /**
     * Constructorul clasei MainFrame. Creează și configurează fereastra principală și butoanele aferente.
     */
    public MainFrame() {
        setTitle("Order Management App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // centrează fereastra pe ecran
        setLayout(null); // folosim coordonate absolute pentru plasarea componentelor

        // === Buton operații clienți ===
        JButton clientButton = new JButton("Client Operations");
        clientButton.setBounds(100, 100, 200, 40);
        clientButton.addActionListener(e -> new ClientView());

        // === Buton operații produse ===
        JButton productButton = new JButton("Product Operations");
        productButton.setBounds(100, 160, 200, 40);
        productButton.addActionListener(e -> new ProductView());

        // === Buton creare comandă ===
        JButton orderButton = new JButton("Create Order");
        orderButton.setBounds(100, 220, 200, 40);
        orderButton.addActionListener(e -> new OrderView());

        // Adăugare butoane în fereastră
        add(clientButton);
        add(productButton);
        add(orderButton);
    }

    /**
     * Metoda main lansează aplicația și afișează fereastra principală.
     * @param args argumente din linia de comandă (neutilizate)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
