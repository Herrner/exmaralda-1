/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewLogEntryDialog.java
 *
 * Created on 08.12.2011, 11:48:20
 */
package org.exmaralda.folker.gui;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import org.exmaralda.folker.utilities.FOLKERInternationalizer;
import org.jdom.Element;

/**
 *
 * @author thomas
 */
public class NewLogEntryDialog extends javax.swing.JDialog {

    public boolean approved = false;
    Date startEdit;
    Date endEdit;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String editPerson;
    
    /** Creates new form NewLogEntryDialog */
    public NewLogEntryDialog(java.awt.Frame parent, boolean modal, Date startEdit, Date endEdit, String editPerson, String description) {
        super(parent, modal);
        initComponents();
        
        descriptionTextArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"ignoreEnterAndCloseWithOK"); 
        descriptionTextArea.getActionMap().put("ignoreEnterAndCloseWithOK", new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonActionPerformed(e);
            }
            
        });
        
        
        this.startEdit = startEdit;
        this.endEdit = endEdit;
        this.editPerson = editPerson;
        
        editStartLabel.setText(dateFormat.format(startEdit));        
        editEndLabel.setText(dateFormat.format(endEdit));        
        editPersonLabel.setText(editPerson);
        descriptionTextArea.setText(description);                        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        editStartLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        editEndLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        editPersonLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        okCancelPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(FOLKERInternationalizer.getString("dialog.newlog.title"));

        topPanel.setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setText(FOLKERInternationalizer.getString("dialog.newlog.startedit"));
        topPanel.add(jLabel1);

        editStartLabel.setText("jLabel4");
        topPanel.add(editStartLabel);

        jLabel2.setText(FOLKERInternationalizer.getString("dialog.newlog.endedit"));
        topPanel.add(jLabel2);

        editEndLabel.setText("jLabel4");
        topPanel.add(editEndLabel);

        jLabel3.setText(FOLKERInternationalizer.getString("dialog.newlog.editperson"));
        topPanel.add(jLabel3);

        editPersonLabel.setText("jLabel4");
        topPanel.add(editPersonLabel);

        getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(FOLKERInternationalizer.getString("dialog.newlog.description")));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 200));

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(descriptionTextArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        okCancelPanel.add(okButton);

        cancelButton.setText(FOLKERInternationalizer.getString("error.cancel"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        okCancelPanel.add(cancelButton);

        getContentPane().add(okCancelPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        approved = true;
        setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewLogEntryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewLogEntryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewLogEntryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewLogEntryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                NewLogEntryDialog dialog = new NewLogEntryDialog(new javax.swing.JFrame(), true, null, null, null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JLabel editEndLabel;
    private javax.swing.JLabel editPersonLabel;
    private javax.swing.JLabel editStartLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel okCancelPanel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    public Element getLogEntry() {
        Element result = new Element("log-entry");
        result.setAttribute("start", dateFormat.format(startEdit));
        result.setAttribute("end", dateFormat.format(endEdit));
        result.setAttribute("who", editPerson);
        result.setText(descriptionTextArea.getText());
        return result;
    }
}