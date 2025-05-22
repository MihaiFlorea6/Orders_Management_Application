package Presentation;

import BusinessLayer.ClientService;
import Model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clasa ClientView oferă o interfață grafică (GUI) pentru gestionarea clienților.
 * Permite adăugarea, modificarea, ștergerea și afișarea clienților într-un tabel.
 */
public class ClientView extends JFrame {
    private final ClientService clientService;
    private final JTextField idField, nameField, emailField;
    private final DefaultTableModel tableModel;

    /**
     * Constructorul creează fereastra principală, configurează layout-ul și adaugă funcționalitate butoanelor.
     */
    public ClientView() {
        clientService = new ClientService();
        setTitle("Gestionare Clienți");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // === Panou principal ===
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // === Panou inputuri ===
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(5);
        JLabel nameLabel = new JLabel("Nume:");
        nameField = new JTextField(10);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(12);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 2;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 3;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 4;
        inputPanel.add(emailLabel, gbc);
        gbc.gridx = 5;
        inputPanel.add(emailField, gbc);

        // === Panou butoane ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Adaugă");
        JButton updateButton = new JButton("Modifică");
        JButton deleteButton = new JButton("Șterge");
        JButton refreshButton = new JButton("Actualizează Tabel");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // === Top Panel: inputuri + butoane ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // === Tabel clienți ===
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nume", "Email"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        // === Funcționalitate butoane ===

        // Adăugare client
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (clientService.addClient(name, email)) {
                JOptionPane.showMessageDialog(this, "Client adăugat!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Eroare la adăugare client.");
            }
        });

        // Modificare client
        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String email = emailField.getText();
                if (clientService.updateClient(id, name, email)) {
                    JOptionPane.showMessageDialog(this, "Client modificat!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Clientul nu există.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID invalid.");
            }
        });

        // Ștergere client
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (clientService.deleteClient(id)) {
                    JOptionPane.showMessageDialog(this, "Client șters!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Clientul nu există.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID invalid.");
            }
        });

        // Actualizare tabel
        refreshButton.addActionListener(e -> refreshTable());

        refreshTable(); // Populează tabelul la inițializare
        setVisible(true);
    }

    /**
     * Reîncarcă datele din tabelul de clienți din baza de date.
     */
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Client> clients = clientService.getAllClients();
        for (Client c : clients) {
            tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getEmail()});
        }
    }
}
