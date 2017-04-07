/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uKontroler;

import gnu.io.CommPortIdentifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.usb.UsbHub;
import java.util.*;
import static uKontroler.FindUsbDevice.getProductId;
import static uKontroler.FindUsbDevice.getUsbDevicesWithId;
import static uKontroler.FindUsbDevice.getVendorId;

/**
 *
 * @author wojtek
 */
public class GUI extends javax.swing.JDialog {

  /**
   * Creates new form GUI
   */
  private boolean changesAccepted = false;
  private Enumeration ports = null;
  private HashMap portMap = new HashMap();
  private HashMap portBMap = new HashMap();
  private Communicator communicator = null;
  JFrame frame;
  public GUI(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    ustawTabele();
    this.setLocation(200, 200);
    pack();
    setVisible(true);
  }

  private void ustawTabele() {
    //tu przygotowujemy dane do edycji
    communicator = new Communicator(this);
    searchForPorts();
    baudForPorts();
  }

  public void searchForPorts() {
    UsbHub virtualRootUsbHub = ShowTopology.getVirtualRootUsbHub();
    List usbDevices = FindUsbDevice.getAllUsbDevices(virtualRootUsbHub);
    usbDevices = getUsbDevicesWithId(virtualRootUsbHub, getVendorId(), getProductId());

    ports = CommPortIdentifier.getPortIdentifiers();

    while (ports.hasMoreElements()) {
      CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();

      //get only serial ports
      if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        cboxPorts.addItem(curPort.getName());
        portMap.put(curPort.getName(), curPort);
      }
    }
  }

  public void baudForPorts() {

    ArrayList al = new ArrayList();
    al.add(110);
    al.add(300);
    al.add(1200);
    al.add(2400);
    al.add(4800);
    al.add(9600);
    al.add(19200);
    al.add(38400);
    al.add(57600);
    al.add(115200);
    al.add(230400);
    al.add(460800);
    al.add(921600);

    //get only serial ports
    for (int i = 0; i < al.size(); i++) {
      baudPort.addItem(al.get(i));
      portBMap.put(al.get(i), i);
    }
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    editPanel = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    cboxPorts = new javax.swing.JComboBox();
    jLabel2 = new javax.swing.JLabel();
    baudPort = new javax.swing.JComboBox();
    buttonPanel = new javax.swing.JPanel();
    jBZapisz = new javax.swing.JButton();
    jBAnuluj = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(new java.awt.GridBagLayout());

    editPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    editPanel.setLayout(new java.awt.GridBagLayout());

    jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle()
    | java.awt.Font.BOLD));
    jLabel1.setText("Wyb�r portu");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    editPanel.add(jLabel1, gridBagConstraints);

    editPanel.add(cboxPorts, new java.awt.GridBagConstraints());

    jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle()
    | java.awt.Font.BOLD));
    jLabel2.setText("Liczba bit�w na sekund�");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
    editPanel.add(jLabel2, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    editPanel.add(baudPort, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
    getContentPane().add(editPanel, gridBagConstraints);

    buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    jBZapisz.setMnemonic('Z');
    jBZapisz.setText("Po��cz");
    jBZapisz.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBZapiszActionPerformed(evt);
      }

    });
    buttonPanel.add(jBZapisz);
    getRootPane().setDefaultButton(jBZapisz);

    jBAnuluj.setMnemonic('A');
    jBAnuluj.setText("Roz��cz");
    jBAnuluj.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBAnulujActionPerformed(evt);
      }

    });
    buttonPanel.add(jBAnuluj);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
    getContentPane().add(buttonPanel, gridBagConstraints);

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jLabel3.setText("Port szeregowy");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    getContentPane().add(jLabel3, gridBagConstraints);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jBZapiszActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBZapiszActionPerformed
    communicator.connect();
    dispose();
//    frame.setEnabled(true);
  }//GEN-LAST:event_jBZapiszActionPerformed

  private void jBAnulujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAnulujActionPerformed
    if (communicator.getConnected()) {
      communicator.disconnect();
    }
    dispose();
    //   frame.setEnabled(true);
  }//GEN-LAST:event_jBAnulujActionPerformed

  public boolean isChangesAccepted() {
    return changesAccepted;
  }

  public int baudRate() {
    return (Integer) baudPort.getSelectedItem();
  }

  public String portName() {
    return (String) cboxPorts.getSelectedItem();
  }

  /**
   * @param args the command line arguments
   */

// Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JComboBox baudPort;
  private javax.swing.JPanel buttonPanel;
  public javax.swing.JComboBox cboxPorts;
  private javax.swing.JPanel editPanel;
  private javax.swing.JButton jBAnuluj;
  private javax.swing.JButton jBZapisz;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
// End of variables declaration//GEN-END:variables
}
