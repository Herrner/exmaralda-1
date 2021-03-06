/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MatchListDialog.java
 *
 * Created on 18.01.2013, 13:05:30
 */
package org.exmaralda.folker.matchlist;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import org.exmaralda.common.dialogs.ProgressBarDialog;
import org.exmaralda.folker.application.ApplicationControl;
import org.exmaralda.folker.utilities.PreferencesUtilities;
import org.exmaralda.partitureditor.jexmaraldaswing.fileFilters.ParameterFileFilter;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Schmidt
 */
public class MatchListDialog extends javax.swing.JDialog implements MouseListener {

    File referenceDir = null;
    File workingDir = null;
    File lastList = null;
    MatchList ml;
    ApplicationControl applicationControl;
    ProgressBarDialog pbd = null;
    
    /** Creates new form MatchListDialog */
    public MatchListDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        matchListList.setModel((ListModel) new MatchListListModel(new MatchList()));
        matchListList.setCellRenderer(new MatchListCellRenderer());
        matchListList.addMouseListener(this);
    }
    
    public void setApplicationControl(ApplicationControl ac){
        applicationControl = ac;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        matchListScrollPane = new javax.swing.JScrollPane();
        matchListList = new javax.swing.JList();
        controlPanel = new javax.swing.JPanel();
        referenceDirPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        referenceDirLabel = new javax.swing.JLabel();
        referenceDirBrowseLabel = new javax.swing.JButton();
        workingDirPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        workingDirLabel = new javax.swing.JLabel();
        workingDirBrowseLabel = new javax.swing.JButton();
        openButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Trefferliste");
        setMinimumSize(new java.awt.Dimension(400, 600));

        matchListList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        matchListScrollPane.setViewportView(matchListList);

        getContentPane().add(matchListScrollPane, java.awt.BorderLayout.CENTER);

        controlPanel.setLayout(new java.awt.GridLayout(3, 1));

        jLabel1.setText("Referenz-Verzeichnis: ");
        referenceDirPanel.add(jLabel1);

        referenceDirLabel.setText("???");
        referenceDirPanel.add(referenceDirLabel);

        referenceDirBrowseLabel.setText("Browse...");
        referenceDirBrowseLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                referenceDirBrowseLabelActionPerformed(evt);
            }
        });
        referenceDirPanel.add(referenceDirBrowseLabel);

        controlPanel.add(referenceDirPanel);

        jLabel3.setText("Arbeits-Verzeichnis: ");
        workingDirPanel.add(jLabel3);

        workingDirLabel.setText("???");
        workingDirPanel.add(workingDirLabel);

        workingDirBrowseLabel.setText("Browse...");
        workingDirBrowseLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workingDirBrowseLabelActionPerformed(evt);
            }
        });
        workingDirPanel.add(workingDirBrowseLabel);

        controlPanel.add(workingDirPanel);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exmaralda/folker/tangoicons/tango-icon-theme-0.8.1/22x22/actions/document-open.png"))); // NOI18N
        openButton.setText("Liste �ffnen...");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        controlPanel.add(openButton);

        getContentPane().add(controlPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new ParameterFileFilter("xml", "XML-Dateien"));
        fileChooser.setCurrentDirectory(lastList);
        int ret = fileChooser.showOpenDialog(this);
        if (ret==JFileChooser.APPROVE_OPTION){
            try {
                final File f = fileChooser.getSelectedFile();
                lastList = f;
                final MatchList newML = new MatchList();
                newML.read(f);
                if (!(newML.isAugmented())){
                    pbd = new ProgressBarDialog(applicationControl.getFrame(), false);
                    pbd.setLocationRelativeTo(this);
                    pbd.setTitle("Trefferliste erg�nzen");
                    newML.addMatchListListener(pbd);
                    pbd.setVisible(true);

                    final Runnable doDisplayList = new Runnable() {
                            public void run() {
                                    displayList(newML);
                            }

                    };
                    Thread checkThread = new Thread() {
                            @Override
                            public void run() {
                                    try {
                                        newML.augment(referenceDir);
                                        newML.write(f);
                                        javax.swing.SwingUtilities.invokeLater(doDisplayList);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        JOptionPane.showMessageDialog(applicationControl.getFrame(), ex);
                                        pbd.setVisible(false);
                                    }
                            }
                    };
                    checkThread.start();                                                            
                } else {
                    displayList(newML);
                }
            } catch (JDOMException ex) {
                JOptionPane.showMessageDialog(controlPanel, ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(controlPanel, ex);
            }
        }
    }//GEN-LAST:event_openButtonActionPerformed

    void displayList(MatchList newML){
        if (pbd!=null){
            pbd.setVisible(false);
        }
        matchListList.setModel((ListModel) new MatchListListModel(newML));
        ml = newML;                        
    }
    private void referenceDirBrowseLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_referenceDirBrowseLabelActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = fileChooser.showOpenDialog(this);
        if (ret==JFileChooser.APPROVE_OPTION){
            File f = fileChooser.getSelectedFile();
            referenceDir = f;
            referenceDirLabel.setText(f.getName());
            referenceDirLabel.setToolTipText(f.getAbsolutePath());
        }        
    }//GEN-LAST:event_referenceDirBrowseLabelActionPerformed

    private void workingDirBrowseLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workingDirBrowseLabelActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = fileChooser.showOpenDialog(this);
        if (ret==JFileChooser.APPROVE_OPTION){
            File f = fileChooser.getSelectedFile();
            workingDir = f;
            workingDirLabel.setText(f.getName());
            workingDirLabel.setToolTipText(f.getAbsolutePath());
        } 
    }//GEN-LAST:event_workingDirBrowseLabelActionPerformed

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
            java.util.logging.Logger.getLogger(MatchListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MatchListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MatchListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MatchListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MatchListDialog dialog = new MatchListDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList matchListList;
    private javax.swing.JScrollPane matchListScrollPane;
    private javax.swing.JButton openButton;
    private javax.swing.JButton referenceDirBrowseLabel;
    private javax.swing.JLabel referenceDirLabel;
    private javax.swing.JPanel referenceDirPanel;
    private javax.swing.JButton workingDirBrowseLabel;
    private javax.swing.JLabel workingDirLabel;
    private javax.swing.JPanel workingDirPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount()==2){
            Element matchElement = (Element) matchListList.getSelectedValue();
            ((MatchListListModel)(matchListList.getModel())).setChecked(matchListList.getSelectedIndex());
            applicationControl.processMatch(matchElement, workingDir);
            try {
                ml.write(lastList);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    public void retrieveSettings() {
        String rd = PreferencesUtilities.getProperty("matchlist-ref-dir", System.getProperty("user.home"));
        referenceDir = new File(rd);
        referenceDirLabel.setText(referenceDir.getName());
        referenceDirLabel.setToolTipText(referenceDir.getAbsolutePath());
        String wd = PreferencesUtilities.getProperty("matchlist-work-dir", System.getProperty("user.home"));
        workingDir = new File(wd);
        workingDirLabel.setText(workingDir.getName());
        workingDirLabel.setToolTipText(workingDir.getAbsolutePath());
        String ld = PreferencesUtilities.getProperty("matchlist-dir", System.getProperty("user.home"));
        lastList = new File(ld);
    }
    
    public void storeSettings() {
        PreferencesUtilities.setProperty("matchlist-ref-dir", referenceDir.getAbsolutePath());
        PreferencesUtilities.setProperty("matchlist-work-dir", workingDir.getAbsolutePath());
        PreferencesUtilities.setProperty("matchlist-dir", lastList.getAbsolutePath());
    }
    
}
