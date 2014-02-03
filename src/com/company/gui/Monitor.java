package com.company.gui;

import com.company.net.util.DBConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;

public class Monitor extends JFrame {
    private JTabbedPane tabPanel;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTable tableHosts;
    private JTable tableStatus;
    private JTable table3;
    private JButton getServersButton;
    private JButton getStatusButton;
    private JButton startButton;
    private JButton stopButton;
    private JPanel mainPanel;
    private JPanel tabConnect;
    private JScrollPane tabHosts;
    private JButton startCheckButton;
    private JPanel tabServers;
    private JPanel tabStatus;
    private JPanel tabMonitor;
    private JTextField textField1;
    private DBConnector dbConnector;

    public Monitor() {
        disconnectButton.setEnabled(false);
        tabPanel.setEnabledAt(1, false);
        tabPanel.setEnabledAt(2, false);
        tabPanel.setEnabledAt(3, false);
        this.dbConnector = new DBConnector();
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String driver = "com.mysql.jdbc.Driver";
                String server = "spl40.hosting.reg.ru";
                String port = "3306";
                String db = "u7753547_support";
                String user = "u7753_support";
                String password = "fmVSBGlvyTKZPFzS";
                dbConnector.openConnection(driver, server, port, db, user, password);
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
                tabPanel.setEnabledAt(1, true);
                tabPanel.setEnabledAt(2, true);
                tabPanel.setEnabledAt(3, true);
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbConnector.closeConnection(dbConnector.getConnection());
                connectButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                tabPanel.setEnabledAt(1, false);
                tabPanel.setEnabledAt(2, false);
                tabPanel.setEnabledAt(3, false);
            }
        });
        getServersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] objects = dbConnector.executeSelect(dbConnector.getConnection(), "*", "servers");
                String[] headings = (String[]) objects[0];
                Object[][] data = (Object[][]) objects[1];
                final DefaultTableModel model = new DefaultTableModel();
                model.setDataVector(data, headings);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tableHosts.setModel(model);
                    }
                });
            }
        });
        getStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] objects = dbConnector.executeSelect(dbConnector.getConnection(), "*", "status");
                String[] headings = (String[]) objects[0];
                Object[][] data = (Object[][]) objects[1];
                final DefaultTableModel model = new DefaultTableModel();
                model.setDataVector(data, headings);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tableStatus.setModel(model);
                    }
                });
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int rowCount = tableStatus.getRowCount();
                URL url;
                String message = "none";
                int code = -1;
                String set = "";
                String condition = "";
                try {
                    for (int index = 0; index < rowCount; index++) {
                        url = new URL(tableStatus.getValueAt(index, 1).toString());
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();

                        message = http.getResponseMessage();
                        set = "message = '" + message + "'";
                        condition = "url LIKE '" + tableStatus.getValueAt(index, 1) + "'";
                        dbConnector.executeUpdate(dbConnector.getConnection(), "status", set, condition);

                        code = http.getResponseCode();
                        set = "code = '" + code + "'";
                        condition = "url LIKE '" + tableStatus.getValueAt(index, 1) + "'";
                        dbConnector.executeUpdate(dbConnector.getConnection(), "status", set, condition);
                        ((DefaultTableModel) tableStatus.getModel()).fireTableDataChanged();
                        tableStatus.updateUI();
                        getStatusButton.doClick();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };
        final Thread thread = new Thread(runnable);
        startCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.start();
            }
        });
    }

    public void init() {
        JFrame frame = new JFrame("Monitor");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        SwingUtilities.updateComponentTreeUI(frame);
        Double dw = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / (1.7);
        Double dh = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / (1.4);
        frame.setSize(dw.intValue(), dh.intValue());
        frame.setVisible(true);
    }
}
