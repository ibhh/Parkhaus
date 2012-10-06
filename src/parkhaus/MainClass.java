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
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author Simon
 */
public class MainClass extends JFrame {

    public static boolean debug = true;
    private JPanel hauptPanel;
    private static JLabel StatusLabel;
    private static Parkhaus parkhaus;
    private static boolean projektloaded = false;
    private static DatenUebersicht daten;
    private static JMenu Menu = new JMenu("Datei");
    private static JMenuItem NeuesParkhaus = new JMenuItem("Neues Parkhaus");
    private static JMenuItem NeuerStellplatz = new JMenuItem("Neuer Stellplatz");
    private static JMenuItem itemOeffnen = new JMenuItem("Datei öffnen");
    private static JMenuItem itemSpeichern = new JMenuItem("Datei speichern");
    private static JMenu Menu2 = new JMenu("Ändern");
    private static JMenuItem Nameaendern = new JMenuItem("Name ändern");
    private static JMenuItem Stellplatz_Anzeige = new JMenuItem("Stellplätze");
    private static JButton Button1 = new JButton("Aktualisieren");
    private static JButton oeffnen;
    private static JButton speichern;
    private Logger Loggerclass;
    private static ReportToHost report;
    private double version = 1.0;
    private String name = "Parkhaus";

    public MainClass() throws HeadlessException {
        super();
        setTitle("Parkhaus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        hauptPanel = init();
        this.getContentPane().add(hauptPanel);
        setVisible(true);
        setBounds(200, 200, 500, 300);
        setJMenuBar(getMenu());
        hauptPanel.updateUI();
        Loggerclass = new Logger(this);
        report = new ReportToHost(this);
    }

    @Override
    public String getName() {
        return name;
    }
    
    public double getVersion() {
        return version;
    }

    public static ReportToHost getReportManager() {
        return report;
    }

    public parkhaus.Logger getDebugLogger() {
        return Loggerclass;
    }

    public static void setProjektloaded() {
        itemSpeichern.setEnabled(true);
        Nameaendern.setEnabled(true);
        Button1.setEnabled(true);
        NeuerStellplatz.setEnabled(true);
        Stellplatz_Anzeige.setEnabled(true);
        speichern.setEnabled(true);
        MainClass.projektloaded = true;
    }

    public void Logger(String msg, String TYPE) {
        try {
            if ((TYPE.equalsIgnoreCase("Warning")) || (TYPE.equalsIgnoreCase("Error"))) {
                System.err.println(msg);
                this.Loggerclass.log("Error: " + msg);
            } else if (TYPE.equalsIgnoreCase("Debug")) {
                System.out.println("Debug: " + msg);
                this.Loggerclass.log("Debug: " + msg);

            } else {
                System.out.println(msg);
                this.Loggerclass.log(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[BookShop] Error: Uncatch Exeption!");
            if (this.report != null) {
                this.report.report(3317, "Logger doesnt work", e.getMessage(), "BookShop", e);
            }
        }
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

    private void handleSaveClick() {
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

    private void handleOpenClick() {
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

    public JMenuBar getMenu() {
        //Initialisiern des Menüs
        JMenuBar MenuBar = new JMenuBar();
        try {
            JToolBar toolbar = new JToolBar();
            //Menü zusammenbauen
            oeffnen = new JButton(new ImageIcon(this.getClass().getResource("/resources/Gnome-Document-Open-32.png")));
            oeffnen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    handleOpenClick();
                }
            });
            speichern = new JButton(new ImageIcon(this.getClass().getResource("/resources/Gnome-Document-Save-As-32.png")));
            toolbar.add(oeffnen);
            speichern.setEnabled(false);
            speichern.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    handleSaveClick();
                }
            });
            toolbar.add(speichern);
            JButton oeffnenfake = new JButton(new ImageIcon(this.getClass().getResource("/resources/Gnome-Document-Open-32.png")));
            toolbar.add(oeffnenfake);
            oeffnenfake.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String leer = null;
                    try {
                        if (leer.equals("test")) {
                            Logger("Fail", "Error");
                        }
                    } catch (Exception e) {
                        if (MainClass.getReportManager() != null) {
                            MainClass.getReportManager().report(1, "Fail failed!", e.getMessage(), "MainClass", e);
                        }
                    }
                }
            });
            getContentPane().add(toolbar, BorderLayout.BEFORE_FIRST_LINE);
            KeyStroke ctrl_O = KeyStroke.getKeyStroke('O', Event.CTRL_MASK);
            itemOeffnen.setAccelerator(ctrl_O);
            ImageIcon iconOeffnen = new ImageIcon(this.getClass().getResource("/resources/Gnome-Document-Open-32.png"));
            itemOeffnen.setIcon(iconOeffnen);
            ImageIcon iconSpeichern = new ImageIcon(this.getClass().getResource("/resources/Gnome-Document-Save-As-32.png"));
            itemSpeichern.setIcon(iconSpeichern);
            itemSpeichern.setEnabled(false);
            KeyStroke ctrl_S = KeyStroke.getKeyStroke('S', Event.CTRL_MASK);
            itemSpeichern.setAccelerator(ctrl_S);
            //2.Menü
            Nameaendern.setEnabled(false);
            NeuerStellplatz.setEnabled(false);
            ImageIcon iconNeuerStellplatz = new ImageIcon(this.getClass().getResource("/resources/Gnome-Document-New-32.png"));
            NeuerStellplatz.setIcon(iconNeuerStellplatz);
            NeuesParkhaus.setIcon(iconNeuerStellplatz);
            JMenuItem beenden = new JMenuItem("Beenden");
            ImageIcon iconBeenden = new ImageIcon(this.getClass().getResource("/resources/Gnome-System-Log-Out-32.png"));
            beenden.setIcon(iconBeenden);
            Stellplatz_Anzeige.setEnabled(false);
            //zusammenbauen un registrieren von listenern
            NeuesParkhaus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    new NeuesParkhaus(MainClass.this, true);
                }
            });
            NeuerStellplatz.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    new NeuerStellplatz(MainClass.this, true);
                }
            });
            Stellplatz_Anzeige.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    new Stellplatz_Anzeige(MainClass.this, true);
                }
            });
            Menu.add(NeuesParkhaus);
            Menu.add(NeuerStellplatz);
            Menu.addSeparator();
            itemOeffnen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    handleOpenClick();
                }
            });
            itemSpeichern.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    handleSaveClick();
                }
            });
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
            Menu2.add(Stellplatz_Anzeige);
            MenuBar.add(getAnsichtMenu());
            MenuBar.add(Menu2);
        } catch (Exception e) {
            if (MainClass.getReportManager() != null) {
                MainClass.getReportManager().report(1, "Exception getMenu()", e.getMessage(), "MainClass", e);
            }
        }
        return MenuBar;
    }

    private JMenu getAnsichtMenu() {
        JMenu menu = new JMenu("Ansicht");
        try {
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
        } catch (Exception e) {
            if (MainClass.getReportManager() != null) {
                MainClass.getReportManager().report(1, "Exception getAnsichtMenu()", e.getMessage(), "MainClass", e);
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

    public static void neuesParkhaus(String name, int hoehe, int maxplaetze) {
        parkhaus = new Parkhaus(name, hoehe, maxplaetze);
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
        try {
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
        } catch (Exception e) {
            if (MainClass.getReportManager() != null) {
                MainClass.getReportManager().report(1, "Exception exit()", e.getMessage(), "MainClass", e);
            }
        }
    }

    public JPanel init() {
        JPanel panel = new JPanel(new BorderLayout());
        try {
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
        } catch (Exception e) {
            if (MainClass.getReportManager() != null) {
                MainClass.getReportManager().report(1, "Exception init()", e.getMessage(), "MainClass", e);
            }
        }
        return panel;
    }

    public static DatenUebersicht getDaten() {
        return daten;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            final MainClass dialog = new MainClass();
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    dialog.exit();
                }
            });
        } catch (Exception e) {
            if (MainClass.getReportManager() != null) {
                MainClass.getReportManager().report(1, "Exception somewhere", e.getMessage(), "MainClass", e);
            }
        }
    }

    public static Parkhaus load(String path) throws Exception {
        return parkhaus = ObjectManager.load(path);
    }

    public void save(String path) throws Exception {
        ObjectManager.save(parkhaus, path);
    }
}
