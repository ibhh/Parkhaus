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
import javax.swing.JPanel;

/**
 *
 * @author Simon
 */
public class Main extends JFrame {

    private JPanel hauptPanel;
    private static Parkhaus parkhaus;

    public Main() throws HeadlessException {
        super("Parkhaus");
        setTitle("Parkhaus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        hauptPanel = init();
        hauptPanel.updateUI();
    }

    public JPanel init() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton Button1 = new JButton("Beenden");
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!save()) {
                    //say cannot save
                    return;
                }
                Main.this.remove(hauptPanel);
                Main.this.repaint();
                Main.this.dispose();
            }
        });
        panel.add(Button1, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            parkhaus = load();
            new Main();
        } catch (Exception e) {
        }
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
}
