/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import util.StringUtils;

/**
 *
 * @author nhile
 */
public class PnCreateFile extends javax.swing.JPanel {

    private final Color DEFAUT_COLOR_BUTTON;

    public PnCreateFile() {
        initComponents();
        initEvent();
        lbError.setVisible(false);
        DEFAUT_COLOR_BUTTON = btCreate.getBackground();
    }

    private void initEvent() {
        tfPathEvent();
        btCreateEvent();
    }

    private void tfPathEvent() {
        tfPath.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String path = tfPath.getText().trim();
                GlobalFunction.setVisibleLb(lbError, path);
            }
        });
    }

    private void btCreateEvent() {
        btCreate.addMouseListener(new MouseAdapter() {
            String parentPath = "";

            @Override
            public void mousePressed(MouseEvent e) {
                btCreate.setBackground(Color.GREEN);
                parentPath = tfPath.getText();
                if (GlobalFunction.isValid(btCreate, lbError, parentPath)) {
                    String pathFile = parentPath.substring(0, parentPath.lastIndexOf("/"));
                    String nameFile = parentPath.substring(parentPath.lastIndexOf("/") + 1);
                    if (!getListFile(pathFile).contains(nameFile)) {
                        try {
                            Process process = Runtime.getRuntime().exec(StringUtils.CMD_CREATE_FILE + StringUtils.HOME_PATH + parentPath); // for Linux
                            //Process process = Runtime.getRuntime().exec("cmd /c dir"); //for Windows
                            process.waitFor();
                            JOptionPane.showConfirmDialog(null, "Đã tạo file thành công", "Message", JOptionPane.YES_OPTION);
                            GlobalFunction.openFolder(StringUtils.HOME_PATH + pathFile);
                            tfPath.setText("");
                            btCreate.setBackground(DEFAUT_COLOR_BUTTON);

                        } catch (InterruptedException | IOException ex) {
                        }
                    } else {
                        JOptionPane.showConfirmDialog(null, "File đã tồn tại !!! \n Hãy tạo một folder khác", "Message", JOptionPane.YES_OPTION);
                        btCreate.setBackground(DEFAUT_COLOR_BUTTON);
                    }
                }
            }
        });
    }

    public List<String> getListFile(String pathFile) {
        List<String> listFile = new ArrayList<>();
        String[] arrString;
        try {
            Process process = Runtime.getRuntime().exec(StringUtils.CMD_SHOW_FOLDER + pathFile); // for Linux
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                arrString = line.split(" ");
                for (String s : arrString) {
                    listFile.add(s);
                }
            }
        } catch (Exception e) {
        }
        return listFile;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnTop = new javax.swing.JPanel();
        lbPath = new javax.swing.JLabel();
        tfPath = new javax.swing.JTextField();
        btCreate = new javax.swing.JButton();
        pnCenter = new javax.swing.JPanel();
        lbError = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        lbPath.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbPath.setText("Path File:");
        pnTop.add(lbPath);

        tfPath.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tfPath.setPreferredSize(new java.awt.Dimension(300, 40));
        pnTop.add(tfPath);

        btCreate.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btCreate.setText("Create");
        btCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnTop.add(btCreate);

        add(pnTop, java.awt.BorderLayout.PAGE_START);

        lbError.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbError.setForeground(new java.awt.Color(204, 0, 0));
        lbError.setText("** Bạn chưa nhập thông tin ");
        pnCenter.add(lbError);

        add(pnCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCreate;
    private javax.swing.JLabel lbError;
    private javax.swing.JLabel lbPath;
    private javax.swing.JPanel pnCenter;
    private javax.swing.JPanel pnTop;
    private javax.swing.JTextField tfPath;
    // End of variables declaration//GEN-END:variables
}
