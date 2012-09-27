/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkhaus;

import javax.swing.JOptionPane;

/**
 *
 * @author Simon
 */
public class NeuesParkhaus extends javax.swing.JDialog {

    /**
     * Creates new form NeuesParkhaus
     */
    public NeuesParkhaus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ok = new javax.swing.JButton();
        Abbruch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        stellplaetze_text = new javax.swing.JTextField();
        parkhausname_text = new javax.swing.JTextField();
        hoehe_text = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Neues Parkhaus");
        setAlwaysOnTop(true);
        setBounds(new java.awt.Rectangle(200, 200, 0, 0));

        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        Abbruch.setText("Abbruch");
        Abbruch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbbruchActionPerformed(evt);
            }
        });

        jLabel1.setText("Parkhausname:");

        jLabel2.setText("Stellplatzanzahl:");

        jLabel3.setText("Höhe in cm:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(hoehe_text))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(91, 91, 91)
                            .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(Abbruch)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(parkhausname_text))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stellplaetze_text)))))
                .addGap(82, 82, 82))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parkhausname_text, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stellplaetze_text, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hoehe_text, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok)
                    .addComponent(Abbruch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        int hoehe = 0;
        try {
            hoehe = Integer.parseInt(hoehe_text.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Die Höhe muss eine Ganzahl sein!", "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int stellplaetze = 0;
        try {
            stellplaetze = Integer.parseInt(stellplaetze_text.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Die Stellplatzanzahl muss eine Ganzahl sein!", "Fehler beim speichern", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MainClass.neuesParkhaus(parkhausname_text.getText(), hoehe, stellplaetze);
        JOptionPane.showMessageDialog(this, "Erfolgreich erstellt!", "ok", JOptionPane.INFORMATION_MESSAGE);
        MainClass.getStatusLabel().setText("Status: Projekt " + MainClass.getParkhaus().getParkhaus_Name() + " geöffnet!");
        MainClass.getDaten().aktualisieren();
        MainClass.setProjektloaded();
        this.remove(this);
        this.repaint();
        this.dispose();
    }//GEN-LAST:event_okActionPerformed

    private void AbbruchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbbruchActionPerformed
        this.remove(this);
        this.repaint();
        this.dispose();
    }//GEN-LAST:event_AbbruchActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Abbruch;
    private javax.swing.JTextField hoehe_text;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton ok;
    private javax.swing.JTextField parkhausname_text;
    private javax.swing.JTextField stellplaetze_text;
    // End of variables declaration//GEN-END:variables
}
