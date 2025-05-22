package Presentation;

import BusinessLayer.ProductService;
import Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Fereastra grafică pentru gestionarea produselor.
 * Permite adăugarea, modificarea, ștergerea și afișarea produselor
 * din aplicația de gestiune. Utilizează un JTable pentru afișare și
 * un ProductService pentru operațiile de business.
 *
 * @author Florea Mihai
 */
public class ProductView extends JFrame {

    /** Serviciul responsabil cu logica de business pentru produse. */
    private final ProductService productService;

    /** Câmp text pentru ID produs. */
    private final JTextField idField;

    /** Câmp text pentru numele produsului. */
    private final JTextField nameField;

    /** Câmp text pentru cantitatea produsului. */
    private final JTextField quantityField;

    /** Modelul tabelului pentru afișarea produselor. */
    private final DefaultTableModel tableModel;

    /**
     * Constructorul clasei ProductView.
     * Inițializează interfața grafică, butoanele, câmpurile de text și tabelul.
     */
    public ProductView() {
        productService = new ProductService();
        setTitle("Gestionare Produse");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // === Panou principal ===
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // === Panou inputuri (ID, Nume, Cantitate) ===
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(5);
        JLabel nameLabel = new JLabel("Nume:");
        nameField = new JTextField(10);
        JLabel quantityLabel = new JLabel("Cantitate:");
        quantityField = new JTextField(5);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 2;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 3;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 4;
        inputPanel.add(quantityLabel, gbc);
        gbc.gridx = 5;
        inputPanel.add(quantityField, gbc);

        // === Panou cu butoane ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Adaugă");
        JButton updateButton = new JButton("Modifică");
        JButton deleteButton = new JButton("Șterge");
        JButton refreshButton = new JButton("Actualizează Tabel");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // === Panou superior (inputuri + butoane) ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // === Tabel pentru produse ===
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nume", "Cantitate"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Adăugăm panoul principal în fereastră
        add(mainPanel);

        // === Acțiuni pentru butoane ===

        /**
         * Butonul Adaugă: validează câmpurile și adaugă un produs nou.
         */
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
                if (productService.addProduct(name, quantity)) {
                    JOptionPane.showMessageDialog(this, "Produs adăugat!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Eroare: Nume invalid sau cantitate negativă.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantitatea trebuie să fie un număr întreg!");
            }
        });

        /**
         * Butonul Modifică: actualizează un produs existent după ID.
         */
        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                if (productService.updateProduct(id, name, quantity)) {
                    JOptionPane.showMessageDialog(this, "Produs actualizat!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Eroare: Date invalide sau produs inexistent.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID și cantitate trebuie să fie numere întregi!");
            }
        });

        /**
         * Butonul Șterge: șterge un produs după ID.
         */
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (productService.deleteProduct(id)) {
                    JOptionPane.showMessageDialog(this, "Produs șters!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Produsul nu există.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID-ul trebuie să fie un număr întreg!");
            }
        });

        /**
         * Butonul Actualizează Tabel: reîncarcă toate produsele în tabel.
         */
        refreshButton.addActionListener(e -> refreshTable());

        // Inițializăm tabelul la pornirea ferestrei
        refreshTable();
        setVisible(true);
    }

    /**
     * Metodă privată care reîncarcă datele din tabelul cu produse.
     * Golește tabelul și îl populează din nou cu datele curente din serviciu.
     */
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Product> products = productService.getAllProducts();
        for (Product p : products) {
            tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getQuantity()});
        }
    }
}
