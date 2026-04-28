package controller;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ControllerStyle {

    // --- VARIABLES ROOT (Couleurs MedInfo) ---
    public static final Color PRIMARY = Color.decode("#4D61F4");        // Bleu médical
    public static final Color PRIMARY_SOFT = Color.decode("#EEF1FF");   // Bleu très clair
    public static final Color SECONDARY = Color.decode("#D7FF7B");      // Vert accent
    public static final Color BG_MAIN = Color.decode("#FFFFFF");        // Blanc
    public static final Color BG_ALT = Color.decode("#F6F8FC");         // Gris/Bleu très léger
    public static final Color TEXT_MAIN = Color.decode("#1F2933");      // Texte sombre
    public static final Color TEXT_MUTED = Color.decode("#6B7280");     // Texte gris
    public static final Color BORDER_SOFT = Color.decode("#E2E8F0");    // Bordures discrètes
    public static final Color DANGER = Color.decode("#E53E3E");         // Rouge erreur

    // --- MÉTHODES D'APPLICATION (Le "CSS" de Java) ---

    /**
     * Applique le style au bouton principal (Bleu MedInfo)
     */
    public static void applyPrimaryBtn(JButton btn) {
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
            new LineBorder(PRIMARY, 1),
            new EmptyBorder(8, 20, 8, 20)
        ));
    }

    /**
     * Applique le style au bouton secondaire (Vert Accent)
     */
    public static void applySecondaryBtn(JButton btn) {
        btn.setBackground(SECONDARY);
        btn.setForeground(TEXT_MAIN);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
            new LineBorder(SECONDARY, 1),
            new EmptyBorder(8, 20, 8, 20)
        ));
    }

    /**
     * Applique le style aux champs de saisie (TextField)
     */
    public static void applyTextField(JTextField txt) {
        txt.setBackground(Color.WHITE);
        txt.setForeground(TEXT_MAIN);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt.setCaretColor(PRIMARY);
        txt.setBorder(new CompoundBorder(
            new LineBorder(BORDER_SOFT, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
    }

    /**
     * Applique le style aux ComboBox
     */
    public static void applyComboBox(JComboBox<?> cbx) {
        cbx.setBackground(Color.WHITE);
        cbx.setForeground(TEXT_MAIN);
        cbx.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbx.setBorder(new LineBorder(BORDER_SOFT, 1));
    }

    /**
     * Applique le style aux titres de formulaire (Labels grisés)
     */
    public static void applyFormLabel(JLabel lbl) {
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(TEXT_MUTED);
    }

    /**
     * Applique le style aux tableaux (JTable)
     */
    public static void applyTableStyle(JTable table) {
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setGridColor(BORDER_SOFT);
        table.setSelectionBackground(PRIMARY_SOFT);
        table.setSelectionForeground(PRIMARY);
        table.setShowVerticalLines(false); // Design plus moderne
        
        // En-tête du tableau
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(TEXT_MUTED);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, BORDER_SOFT));
    }
}