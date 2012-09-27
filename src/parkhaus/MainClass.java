package parkhaus;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author Simon
 */
public class MainClass extends JFrame {

    private boolean debug = true;
    private JPanel hauptPanel;
    private static JLabel StatusLabel;
    private static Parkhaus parkhaus;
    private static boolean projektloaded = false;
    private static DatenUebersicht daten;
    private static JMenu Menu = new JMenu("Datei");
    private static JMenuItem NeuesParkhaus = new JMenuItem("Neues Parkhaus");
    private static JMenuItem itemOeffnen = new JMenuItem("Datei öffnen");
    private static JMenuItem itemSpeichern = new JMenuItem("Datei speichern");
    private static JMenu Menu2 = new JMenu("Ändern");
    private static JMenuItem Nameaendern = new JMenuItem("Name ändern");
    private static JButton Button1 = new JButton("Aktualisieren");

    public MainClass() throws HeadlessException {
        super();
        setTitle("Parkhaus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        hauptPanel = init();
        this.getContentPane().add(hauptPanel);
        changelookandfeel("Nimbus");
        setVisible(true);
        setBounds(200, 200, 500, 250);
        setJMenuBar(getMenu());
        hauptPanel.updateUI();
    }
    
    public static void setProjektloaded() {
        itemSpeichern.setEnabled(true);
        Nameaendern.setEnabled(true);
        Button1.setEnabled(true);
        MainClass.projektloaded = true;
    }

    public static JMenuItem getItemSpeichern() {
        return itemSpeichern;
    }

    public static JMenuItem getNameaendern() {
        return Nameaendern;
    }

    public static Parkhaus getParkhaus() {
        return parkhaus;
    }

    public static JLabel getStatusLabel() {
        return StatusLabel;
    }

    public void updateGraphics() {
        SwingUtilities.updateComponentTreeUI(MainClass.this);
    }

    public JMenuBar getMenu() {
        //Initialisiern des Menüs
        JMenuBar MenuBar = new JMenuBar();
        //Menü zusammenbauen
        KeyStroke ctrl_O = KeyStroke.getKeyStroke('O', Event.CTRL_MASK);
        itemOeffnen.setAccelerator(ctrl_O);
        itemSpeichern.setEnabled(false);
        KeyStroke ctrl_S = KeyStroke.getKeyStroke('S', Event.CTRL_MASK);
        itemSpeichern.setAccelerator(ctrl_S);
        //2.Menü
        Nameaendern.setEnabled(false);
        //zusammenbauen un registrieren von listenern
        NeuesParkhaus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new NeuesParkhaus(MainClass.this, true);
            }
        });
        Menu.add(NeuesParkhaus);
        Menu.addSeparator();
        itemOeffnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (parkhaus != null) {
                    int returnstatus = JOptionPane.showConfirmDialog(MainClass.this, "Soll das aktuelle Projekt geschlossen werden?", "Neues Projekt laden?", JOptionPane.OK_CANCEL_OPTION);
                    if (returnstatus != JOptionPane.OK_OPTION) {
                        return;
                    }
                }
                JFileChooser dateiAuswahl = new JFileChooser(".");
                int status = dateiAuswahl.showOpenDialog(MainClass.this);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File datei = dateiAuswahl.getSelectedFile();
                    try {
                        load(datei.getAbsolutePath());
                        JOptionPane.showMessageDialog(MainClass.this, "Datei erfolgreich geladen!", "ok", JOptionPane.INFORMATION_MESSAGE);
                        StatusLabel.setText("Status: Projekt " + parkhaus.getParkhaus_Name() + " geöffnet!");
                        getDaten().aktualisieren();
                        setProjektloaded();
                    } catch (Exception ex) {
                        if (debug) {
                            ex.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht geladen werden: " + ex.getMessage(), "Fehler beim laden", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        itemSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (parkhaus == null) {
                    JOptionPane.showMessageDialog(MainClass.this, "Kein Projekt geladen!", "Kein Projekt", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JFileChooser dateiAuswahl = new JFileChooser(".");
                int status = dateiAuswahl.showSaveDialog(MainClass.this);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File datei = dateiAuswahl.getSelectedFile();
                    try {
                        if (datei.exists()) {
                            int returnstatus = JOptionPane.showConfirmDialog(MainClass.this, "Soll die Datei überschrieben werden?", "Überschreiben?", JOptionPane.OK_CANCEL_OPTION);
                            if (returnstatus == JOptionPane.OK_OPTION) {
                                datei.delete();
                                try {
                                    save(datei.getAbsolutePath());
                                    JOptionPane.showMessageDialog(MainClass.this, "Datei erfolgreich gespeichert!", "ok", JOptionPane.INFORMATION_MESSAGE);
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht überschrieben werden: " + e.getMessage(), "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            try {
                                save(datei.getAbsolutePath());
                                JOptionPane.showMessageDialog(MainClass.this, "Datei erfolgreich gespeichert!", "ok", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht gespeichert werden: " + e.getMessage(), "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht gespeichert werden: " + ex.getMessage(), "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JMenuItem beenden = new JMenuItem("Beenden");
        beenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                exit();
            }
        });
        Menu.add(itemOeffnen);
        Menu.add(itemSpeichern);
        Menu.addSeparator();
        Menu.add(beenden);
        MenuBar.add(Menu);
        //Menü zusammenbauen
        Nameaendern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (parkhaus != null) {
                    EditProjektName name = new EditProjektName(MainClass.this, true);
                } else {
                    JOptionPane.showMessageDialog(MainClass.this, "Kein Projekt geladen!", "Kein Projekt", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        Menu2.add(Nameaendern);
        MenuBar.add(getAnsichtMenu());
        MenuBar.add(Menu2);
        return MenuBar;
    }

    private JMenu getAnsichtMenu() {
        JMenu menu = new JMenu("Ansicht");
        ButtonGroup group = new ButtonGroup();
        for (final javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if (info.getClassName().equals(javax.swing.UIManager.getSystemLookAndFeelClassName())) {
                JRadioButtonMenuItem item = new JRadioButtonMenuItem(info.getName(), true);
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        changelookandfeel(info.getName());
                    }
                });
                group.add(item);
                menu.add(item);
            } else {
                JRadioButtonMenuItem item = new JRadioButtonMenuItem(info.getName(), false);
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        changelookandfeel(info.getName());
                    }
                });
                group.add(item);
                menu.add(item);
            }
        }
        return menu;
    }

    public boolean changelookandfeel(String name) {
        boolean a = false;
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        if (name != null) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if (name.equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        a = true;
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        try {
            if (!a) {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        updateGraphics();
        return a;
    }

    public static void neuesParkhaus(String name, int hoehe, int Stellplaetze) {
        parkhaus = new Parkhaus(name, hoehe, Stellplaetze);
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void exit() {
        if (parkhaus != null) {
            int save = JOptionPane.showConfirmDialog(MainClass.this, "Soll das Projekt gespeichert werden?", "Speichern?", JOptionPane.OK_CANCEL_OPTION);
            if (save == JOptionPane.OK_OPTION) {
                JFileChooser dateiAuswahl = new JFileChooser(".");
                int status = dateiAuswahl.showSaveDialog(MainClass.this);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File datei = dateiAuswahl.getSelectedFile();
                    try {
                        if (datei.exists()) {
                            int returnstatus = JOptionPane.showConfirmDialog(MainClass.this, "Soll die Datei überschrieben werden?", "Überschreiben?", JOptionPane.OK_CANCEL_OPTION);
                            if (returnstatus == JOptionPane.OK_OPTION) {
                                datei.delete();
                                try {
                                    save(datei.getAbsolutePath());
                                    JOptionPane.showMessageDialog(MainClass.this, "Datei erfolgreich gespeichert!", "ok", JOptionPane.INFORMATION_MESSAGE);
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht überschrieben werden: " + e.getMessage(), "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            try {
                                save(datei.getAbsolutePath());
                                JOptionPane.showMessageDialog(MainClass.this, "Datei erfolgreich gespeichert!", "ok", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht gespeichert werden: " + e.getMessage(), "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht gespeichert werden: " + ex.getMessage(), "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        MainClass.this.remove(hauptPanel);
        MainClass.this.repaint();
        MainClass.this.dispose();
        System.exit(0);
    }

    public JPanel init() {
        JPanel panel = new JPanel(new BorderLayout());
        daten = new DatenUebersicht();
        panel.add(daten, BorderLayout.CENTER);
        JPanel lastline = new JPanel(new BorderLayout());
        StatusLabel = new JLabel("Status: No Projekt opened!");
        lastline.add(StatusLabel, BorderLayout.WEST);
        Button1.setEnabled(false);
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!daten.aktualisieren()) {
                    JOptionPane.showMessageDialog(MainClass.this, "Ansicht kann nicht aktualisiert werden, da kein Projekt geladen ist", "Fehler beim aktualisieren", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        lastline.add(Button1, BorderLayout.EAST);
        panel.add(lastline, BorderLayout.SOUTH);
        return panel;
    }

    public static DatenUebersicht getDaten() {
        return daten;
    }

    public void Stellplatzanzahlaendern() {
        final JFrame ChangePanel = new JFrame("Stellplatzanzahl ändern");
        ChangePanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        final JLabel Label = new JLabel("Status: Bitte ändern sie die Anzahl");
        final JTextField Stellplaetze = new JTextField(String.valueOf(parkhaus.getStellpleatze()));
        JButton ButtonSendParkhausname = new JButton("Ok");
        ButtonSendParkhausname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (Tools.isInteger(Stellplaetze.getText())) {
                    parkhaus.setStellplaetze(Integer.parseInt(Stellplaetze.getText()));
                    Label.setText("Status: Stellplatzanzahl geändert!");
                } else {
                    Label.setText("Status: Keine Zahl!");
                }
            }
        });
        JButton Button1 = new JButton("Beenden");
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ChangePanel.remove(ChangePanel);
                ChangePanel.repaint();
                ChangePanel.dispose();
            }
        });
        panel.add(Label, BorderLayout.NORTH);
        panel.add(ButtonSendParkhausname, BorderLayout.EAST);
        panel.add(Stellplaetze, BorderLayout.WEST);
        panel.add(Button1, BorderLayout.SOUTH);
        ChangePanel.getContentPane().add(panel);
        ChangePanel.setVisible(true);
        ChangePanel.setBounds(200, 200, 200, 150);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final MainClass dialog = new MainClass();
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dialog.exit();
            }
        });

    }

    public static Parkhaus load(String path) throws Exception {
        return parkhaus = ObjectManager.load(path);
    }

    public void save(String path) throws Exception {
        ObjectManager.save(parkhaus, path);
    }
}
