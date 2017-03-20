/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opass.kiosk_teller;

import com.alee.laf.WebLookAndFeel;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author agung
 */
public class Kiosk_Teller  implements Runnable{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Kiosk_Teller());
    }

    @Override
    public void run() {
    try {
            try {
                try {
                    MainExec();
                } catch (IOException ex) {
                    Logger.getLogger(Kiosk_Teller.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Kiosk_Teller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Kiosk_Teller.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
     
    void MainExec() throws UnsupportedLookAndFeelException, InterruptedException, IOException {
        try {
            UIManager.setLookAndFeel (WebLookAndFeel.class.getCanonicalName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Kiosk_Teller.class.getName()).log(Level.SEVERE, null, ex);
        }        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
    
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    kioskClient_V3 ks = new kioskClient_V3();
                    ks.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Kiosk_Teller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
      //  kioskClient_V3 ks = new kioskClient_V3();
       // ks.setVisible(true);
        
    }
    
}
