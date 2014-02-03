package com.company;

import com.company.gui.Monitor;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Monitor monitor = new Monitor();
        monitor.init();


//        DBConnector dbConnector = new DBConnector();
//        dbConnector.openConnection(driver, server, port, db, user, password);
//        dbConnector.executeSelect(dbConnector.getConnection(), "*", "hosts");
//        dbConnector.executeInsert(dbConnector.getConnection(), "hosts (host, host_md5, host_base64)", "'01', '01', '01'");
//        dbConnector.executeDelete(dbConnector.getConnection(), "hosts", "host = '01'");
//        dbConnector.executeUpdate(dbConnector.getConnection(), "hosts", "host_md5 = '4815162342'", "host = 'vk.com'");
//        dbConnector.executeSelect(dbConnector.getConnection(), "*", "hosts");
//        dbConnector.closeConnection(dbConnector.getConnection());
    }
}
