package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Utilitar pentru generarea automată de tabele Swing folosind reflection.
 * Transformă orice listă de obiecte într-un JTable, extragând automat proprietățile obiectelor.
 *
 * @author Florea Mihai
 * @version 1.4
 * @since 1.0
 */
public class ReflectionTableGenerator {

    /**
     * Generează un JTable pe baza unei liste de obiecte.
     * Coloanele tabelului sunt create din proprietățile declarate ale clasei obiectelor.
     *
     * @param objectList Lista de obiecte care vor fi afișate în tabel
     * @return JTable configurat cu antetul și datele extrase din obiecte
     *
     * @throws IllegalArgumentException dacă lista este null
     *
     * @see JTable
     * @see DefaultTableModel
     */
    public static JTable createTableFromList(List<?> objectList) {
        DefaultTableModel model = new DefaultTableModel();


        if (objectList == null || objectList.isEmpty()) {
            return new JTable(model);
        }


        Class<?> objectClass = objectList.get(0).getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            model.addColumn(field.getName());
        }


        for (Object obj : objectList) {
            Object[] row = new Object[model.getColumnCount()];
            int i = 0;
            for (Field field : objectClass.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    row[i++] = field.get(obj);
                } catch (IllegalAccessException e) {
                    row[i++] = "N/A";
                }
            }
            model.addRow(row);
        }

        return new JTable(model);
    }
}