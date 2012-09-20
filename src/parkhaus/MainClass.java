/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkhaus;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Simon
 */
public class MainClass extends JFrame {

    private JPanel hauptPanel;
    private JLabel label1;
    private static Parkhaus parkhaus;

    public MainClass() throws HeadlessException {
        super();
        setTitle("Parkhaus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        hauptPanel = init();
        this.getContentPane().add(hauptPanel);
        setVisible(true);
        setBounds(200, 200, 400, 400);
        hauptPanel.updateUI();
    }

    public JPanel init() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton Button1 = new JButton("Beenden");
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!save()) {
                    label1.setText("Status: Can not save data!");
                    return;
                }
                MainClass.this.remove(hauptPanel);
                MainClass.this.repaint();
                MainClass.this.dispose();
            }
        });
        panel.add(Button1, BorderLayout.SOUTH);
        JButton Button2 = new JButton("Delete data");
        Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!deleteData()) {
                    label1.setText("Status: Can not delete data!");
                    return;
                }
            }
        });
        JButton Button3 = new JButton("save data");
        Button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!save()) {
                    label1.setText("Status: Can not save data!");
                } else {
                    label1.setText("Status: Data saved!");
                }
            }
        });
        JPanel firstline = new JPanel(new BorderLayout());
        panel.add(Button1, BorderLayout.SOUTH);
        firstline.add(Button2, BorderLayout.EAST);
        firstline.add(Button3, BorderLayout.WEST);
        panel.add(firstline, BorderLayout.BEFORE_FIRST_LINE);
        label1 = new JLabel("Status: Running");
        panel.add(label1);
        return panel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            parkhaus = load();
        } catch (Exception e) {
        }
        new MainClass();
    }

    public static Parkhaus load() throws Exception {
        return parkhaus = ObjectManager.load("ParkhausData" + File.separator + "ParkhausData.dat");
    }

    public boolean save() {
        try {
            String path = "ParkhausData" + File.separator;
            File pathFile = new File(path);
            pathFile.mkdirs();
            ObjectManager.save(parkhaus, path + "ParkhausData.dat");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteData() {
        try {
            String path = "ParkhausData" + File.separator;
            File pathFile = new File(path);
            label1.setText("Status: Deleting data ...");
            pathFile.mkdirs();
            File file = new File(path + "ParkhausData.dat");
            if (file.exists()) {
                file.delete();
                label1.setText("Status: Deleted data!");
            } else {
                label1.setText("Status: No data found!");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
