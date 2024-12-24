package org.ligson.jtreesize;


import javax.swing.*;

public class App {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JWin jWin = new JWin();
            jWin.setVisible(true);
        });

    }
}
