package parkhaus;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Simon
 */
public class MainClass extends JFrame {

    private boolean debug = true;
    private JPanel hauptPanel;
    private JLabel StatusLabel;
    private JTextField Parkhausname;
    private static Parkhaus parkhaus;

    public MainClass() throws HeadlessException {
        super();
        setTitle("Parkhaus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        hauptPanel = init();
        this.getContentPane().add(hauptPanel);
        setVisible(true);
        setBounds(200, 200, 500, 150);
        setJMenuBar(getMenu());
        hauptPanel.updateUI();
    }

    public static Parkhaus getParkhaus() {
        return parkhaus;
    }

    public JLabel getStatusLabel() {
        return StatusLabel;
    }

    public JMenuBar getMenu() {
        //Initialisiern des Menüs
        JMenuBar MenuBar = new JMenuBar();
        JMenu Menu = new JMenu("Datei");
        //Menü zusammenbauen
        JMenuItem Neu = new JMenuItem("Neues Parkhaus");
        Neu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                parkhaus = new Parkhaus("Parkhausname", 200, 200);
                JOptionPane.showMessageDialog(MainClass.this, "Erfolgreich erstellt!", "ok", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Menu.add(Neu);
        Menu.addSeparator();
        JMenuItem itemOeffnen = new JMenuItem("Datei öffnen", new ImageIcon("open.gif"));
        itemOeffnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFileChooser dateiAuswahl = new JFileChooser(".");
                int status = dateiAuswahl.showOpenDialog(MainClass.this);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File datei = dateiAuswahl.getSelectedFile();
                    try {
                        load(datei.getAbsolutePath());
                        JOptionPane.showMessageDialog(MainClass.this, "Datei erfolgreich geladen!", "ok", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        if (debug) {
                            ex.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(MainClass.this, "Datei konnte nicht geladen werden: " + ex.getMessage(), "Fehler beim laden", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JMenuItem itemSpeichern = new JMenuItem("Datei speichern", new ImageIcon("open.gif"));
        itemSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
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
        Menu.add(itemOeffnen);
        Menu.add(itemSpeichern);
        MenuBar.add(Menu);
        return MenuBar;
    }

    public JPanel init() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton Button1 = new JButton("Beenden");
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //want to save data?
                MainClass.this.remove(hauptPanel);
                MainClass.this.repaint();
                MainClass.this.dispose();
                System.exit(0);
            }
        });
//        JButton Button2 = new JButton("Delete data");
//        Button2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                if (!deleteData()) {
//                    StatusLabel.setText("Status: Can not delete data!");
//                }
//            }
//        });
//        JButton Button3 = new JButton("save data");
//        Button3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                if (!save()) {
//                    StatusLabel.setText("Status: Can not save data!");
//                } else {
//                    StatusLabel.setText("Status: Data saved!");
//                }
//            }
//        });
        JPanel firstline = new JPanel(new BorderLayout());
//        firstline.add(Button2, BorderLayout.EAST);
//        firstline.add(Button3, BorderLayout.WEST);
        panel.add(firstline, BorderLayout.BEFORE_FIRST_LINE);
//        final String[] data = {"Parkhausname ändern", "Stellplatzanzahl ändern", "nichts tun"};
//        final JList<String> myList = new JList<String>(data);
//        JButton ButtonSelect = new JButton("Ok");
//        ButtonSelect.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                if (myList.getSelectedIndices().length != 0) {
//                    if (myList.getSelectedIndex() != -1) {
//                        if (myList.getSelectedIndices().length == 1) {
//                            if (myList.getSelectedValue().equals(data[0])) {
//                                parkHausnameaendern();
//                                StatusLabel.setText("Bitte Parkhausname in neuem Fenster ändern!");
//                            } else if (myList.getSelectedValue().equals(data[1])) {
//                                Stellplatzanzahlaendern();
//                                StatusLabel.setText("Bitte Stellplatzanzahl in neuem Fenster ändern!");
//                            }
//                        } else {
//                            StatusLabel.setText("Nur eine Auswahl treffen!");
//                        }
//                    }
//                }
//            }
//        });
//        panel.add(myList, BorderLayout.WEST);
//        panel.add(ButtonSelect, BorderLayout.EAST);
        JPanel lastline = new JPanel(new BorderLayout());
        StatusLabel = new JLabel("Status: Running");
        lastline.add(StatusLabel, BorderLayout.WEST);
        lastline.add(Button1, BorderLayout.EAST);
        panel.add(lastline, BorderLayout.SOUTH);
        return panel;
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

    public void parkHausnameaendern() {
        final JFrame ChangePanel = new JFrame("ParkhausName ändern");
        ChangePanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        final JLabel Label = new JLabel("Status: Bitte ändern sie den Namen");
        JButton ButtonSendParkhausname = new JButton("Ok");
        ButtonSendParkhausname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setTitle(Parkhausname.getText());
                parkhaus.setParkhaus_Name(Parkhausname.getText());
                Label.setText("Status: Parkhausname geändert!");
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
        Parkhausname = new JTextField(parkhaus.getParkhaus_Name());
        panel.add(Parkhausname, BorderLayout.WEST);
        panel.add(ButtonSendParkhausname, BorderLayout.EAST);
        panel.add(Button1, BorderLayout.SOUTH);
        ChangePanel.getContentPane().add(panel);
        ChangePanel.setVisible(true);
        ChangePanel.setBounds(200, 200, 250, 125);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new MainClass();
    }

    public static Parkhaus load(String path) throws Exception {
        return parkhaus = ObjectManager.load(path);
    }

    public void save(String path) throws Exception {
        ObjectManager.save(parkhaus, path);
    }

    public boolean deleteData() {
        try {
            String path = "ParkhausData" + File.separator;
            File pathFile = new File(path);
            StatusLabel.setText("Status: Deleting data ...");
            pathFile.mkdirs();
            File file = new File(path + "ParkhausData.dat");
            if (file.exists()) {
                file.delete();
                StatusLabel.setText("Status: Deleted data!");
            } else {
                StatusLabel.setText("Status: No data found!");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
