package Presentation;

import BusinessLayer.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clasa OrderView reprezintă interfața grafică pentru crearea comenzilor.
 * Permite selectarea unui client și a unui produs, introducerea cantității
 * și plasarea unei comenzi în sistem.
 */
public class OrderView extends JFrame {
    private final ClientService clientService;
    private final ProductService productService;
    private final OrderService orderService;

    private final JComboBox<String> clientBox;
    private final JComboBox<String> productBox;
    private final JTextField quantityField;
    private final JTable ordersTable;
    private final JButton refreshOrdersButton;

    /**
     * Constructorul clasei OrderView. Inițializează toate componentele UI și logica asociată.
     */
    public OrderView() {
        clientService = new ClientService();
        productService = new ProductService();
        orderService = new OrderService();

        setTitle("Creare Comandă");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Panou pentru inputuri (client, produs, cantitate) ===
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel clientLabel = new JLabel("Client:");
        clientBox = new JComboBox<>();

        JLabel productLabel = new JLabel("Produs:");
        productBox = new JComboBox<>();

        JLabel quantityLabel = new JLabel("Cantitate:");
        quantityField = new JTextField(5);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(clientLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(clientBox, gbc);

        gbc.gridx = 2;
        inputPanel.add(productLabel, gbc);
        gbc.gridx = 3;
        inputPanel.add(productBox, gbc);

        gbc.gridx = 4;
        inputPanel.add(quantityLabel, gbc);
        gbc.gridx = 5;
        inputPanel.add(quantityField, gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton orderButton = new JButton("Plasează Comandă");
        buttonPanel.add(orderButton);


        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);


        orderButton.addActionListener(e -> {
            String clientStr = (String) clientBox.getSelectedItem();
            String productStr = (String) productBox.getSelectedItem();

            if (clientStr == null || productStr == null || quantityField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Toate câmpurile sunt necesare!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int clientId = Integer.parseInt(clientStr.split(":")[0]);
                int productId = Integer.parseInt(productStr.split(":")[0]);
                int quantity = Integer.parseInt(quantityField.getText());


                Product product = productService.findById(productId);
                if (product != null && product.getQuantity() < quantity) {
                    JOptionPane.showMessageDialog(this,
                            "Cantitate insuficientă! Stoc disponibil: " + product.getQuantity(),
                            "Eroare stoc",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean result = orderService.addOrder(clientId, productId, quantity);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Comandă plasată cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
                    refreshComboBoxes();
                    refreshOrdersTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Eroare la plasarea comenzii", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduceți valori numerice valide!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });


        ordersTable = new JTable();
        refreshOrdersButton = new JButton("Actualizează Lista Comenzi");

        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        ordersPanel.add(refreshOrdersButton, BorderLayout.SOUTH);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(ordersPanel, BorderLayout.SOUTH);

        add(mainPanel);


        refreshComboBoxes();
        refreshOrdersTable();
        setVisible(true);


        refreshOrdersButton.addActionListener(e -> refreshOrdersTable());
    }

    /**
     * Metodă ce actualizează JComboBox-urile cu clienții și produsele disponibile.
     */
    private void refreshComboBoxes() {
        clientBox.removeAllItems();
        productBox.removeAllItems();

        List<Client> clients = clientService.getAllClients();
        List<Product> products = productService.getAllProducts();

        for (Client c : clients) {
            clientBox.addItem(c.getId() + ": " + c.getName());
        }

        for (Product p : products) {
            productBox.addItem(p.getId() + ": " + p.getName() + " [" + p.getQuantity() + "]");
        }
    }

    /**
     * Metodă ce reîncarcă tabelul comenzilor din sistem.
     * Utilizează clasa auxiliară ReflectionTableGenerator pentru generarea tabelului.
     */
    private void refreshOrdersTable() {
        List<Order> orders = orderService.getAllOrders();
        JTable generatedTable = ReflectionTableGenerator.createTableFromList(orders);
        ordersTable.setModel(generatedTable.getModel());
    }
}
