package com.opass.kiosk_teller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import chat.CTeller;
import chat.ChatCallbackAdapter;
import java.awt.Color;
import java.awt.Cursor;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Properties;
import javax.swing.JOptionPane;
import redis.clients.jedis.Jedis;

import java.awt.Toolkit;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author agung
 */
public class kioskClient_V3 extends javax.swing.JFrame implements ChatCallbackAdapter {

    private static final long serialVersionUID = 8005291198123231481L;

    protected static final Logger logger = Logger.getLogger("KioskLog");
    FileHandler fh;

    Font font;
    DatagramSocket clientSocket;

    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];

    InetAddress IPAddress;

    DatagramPacket sendPacket;

    DatagramPacket receivePacket;

    String sentence;

    private CTeller cteller;

    private Thread thread;

    private String vDefault = "";

    /**
     * STATUS TELLER 0 = mulai 1 = panggil 2 = layani
     *
     */
    private int status_teller = 0;

    private int atr_total = 0;
    private int atr_sisa = 0;
    private int atr_layan = 0;

    private String nomor_aktif = "";
    private String vLabel = "A";
    private String vLabel2 = "q_npwp";
//    private int vLayan = 100;
//    private int vBayar = 200;
    private int vNPWP = 500;
    private int vSPPT = 750;
    private int vLAIN = 0;
    private int total_antrian = 0;
    private int sisa_antrian = 0;
    private int nomor_antrian = 0;
    private int nomor_layanan = 0;
    private int status_antrian = 0;
    private PropertyValues pv;
    private static String JEDIS_SERVER;
    private static String LED_PANEL;
    private static Jedis jedis;
    private static String socketServer;
    private static int socketport;
    private static String NODEJSServer;
    private static int NODEJSport;
    private static String loketname;
    private static String display;
    private static String pos;
    private static String NOLOKET;
    private static Properties prop;
    final int ST_LEWAT = 1;
    final int ST_LAYANAN = 2;
    final int ST_ISTIRAHAT = 3;

    public void startKiosk() {
        cteller = new CTeller(this, "http://" + NODEJSServer + ":" + NODEJSport);
        thread = new Thread(cteller);
        thread.start();
    }
    
    public void stoptKiosk() {
        //thread.interrupt();
        // cteller = null;
        //thread =null;
        System.gc();
    }

    /**
     * Creates new form queClient
     *
     * @throws java.io.IOException
     */
    public kioskClient_V3() throws IOException {

        initComponents();
        pv = new PropertyValues();
        prop = new Properties(pv.getProperties());
        try {
            JEDIS_SERVER = prop.getProperty("JEDIS_SERVER");
            LED_PANEL = prop.getProperty("LED_PANEL");
            socketServer = prop.getProperty("SOCKET_SERVER");
            socketport = Integer.valueOf(prop.getProperty("SOCKET_SERVER_PORT"));
            NODEJSServer = prop.getProperty("NODEJS_SERVER");
            NODEJSport = Integer.valueOf(prop.getProperty("NODEJS_SERVER_PORT"));
            loketname = prop.getProperty("LOKETNAME");
            display = prop.getProperty("DISPLAY");
            pos = prop.getProperty("POS");
            NOLOKET = prop.getProperty("NOLOKET");
            jtxtLoket.setText(display);
//            try {
//                clientSocket = new DatagramSocket();
//                try {
//                    IPAddress = InetAddress.getByName(LED_PANEL);
//                    //System.out.println(IPAddress);
//                } catch (UnknownHostException ex) {
//                    Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } catch (SocketException ex) {
//                Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } finally {
            pv.closeProperties();
        }

        jedis = new Jedis(JEDIS_SERVER);

        btnPanggil.setEnabled(false);
        btnLewat.setEnabled(false);
        btnLayan.setEnabled(false);
        //btnIstirahat.setEnabled(false);
        startKiosk();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl1 = new javax.swing.JPanel();
        lblnomor_antrian = new javax.swing.JLabel();
        jtxtLoket = new javax.swing.JTextField();
        btnPanggil = new javax.swing.JButton();
        btnLayan = new javax.swing.JButton();
        btnLewat = new javax.swing.JButton();
        btnIstirahat = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTotal1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTerlayani = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblconnected = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kiosk Client V.3");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/image/LogoPajak.png")));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnl1.setBackground(new java.awt.Color(0, 51, 102));

        lblnomor_antrian.setBackground(new java.awt.Color(0, 0, 0));
        lblnomor_antrian.setFont(new java.awt.Font("DejaVu Sans", 1, 60)); // NOI18N
        lblnomor_antrian.setForeground(new java.awt.Color(204, 0, 51));
        lblnomor_antrian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnomor_antrian.setText("000");
        lblnomor_antrian.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "NOMOR ANTRIAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        lblnomor_antrian.setOpaque(true);

        jtxtLoket.setEditable(false);
        jtxtLoket.setBackground(new java.awt.Color(21, 20, 16));
        jtxtLoket.setFont(new java.awt.Font("DejaVu Sans", 0, 30)); // NOI18N
        jtxtLoket.setForeground(new java.awt.Color(255, 255, 0));
        jtxtLoket.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtLoket.setText("Loket 01");

        btnPanggil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sound.png"))); // NOI18N
        btnPanggil.setText("Panggil");
        btnPanggil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPanggil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPanggilActionPerformed(evt);
            }
        });

        btnLayan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon-following-b.png"))); // NOI18N
        btnLayan.setText("Layani");
        btnLayan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLayan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayanActionPerformed(evt);
            }
        });

        btnLewat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/right_16.png"))); // NOI18N
        btnLewat.setText("Berikutnya");
        btnLewat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLewat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLewatActionPerformed(evt);
            }
        });

        btnIstirahat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clock_16.png"))); // NOI18N
        btnIstirahat.setText("Mulai");
        btnIstirahat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIstirahat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIstirahatActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(21, 20, 16));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "KETERANGAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(244, 236, 236))); // NOI18N

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(250, 251, 14));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Total :");

        lblTotal.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(242, 233, 233));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("000");

        lblTotal1.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        lblTotal1.setForeground(new java.awt.Color(245, 239, 238));
        lblTotal1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal1.setText("000");

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(250, 251, 14));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Terlayani :");

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(250, 251, 14));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Sisa :");

        lblTerlayani.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        lblTerlayani.setForeground(new java.awt.Color(61, 230, 27));
        lblTerlayani.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTerlayani.setText("000");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTerlayani, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTerlayani, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblLogo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(204, 204, 204));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LogoPajak.png"))); // NOI18N
        lblLogo.setText(" KPP. PRATAMA MEDAN KOTA");

        lblconnected.setForeground(new java.awt.Color(244, 7, 7));
        lblconnected.setText("Disconnect");

        javax.swing.GroupLayout pnl1Layout = new javax.swing.GroupLayout(pnl1);
        pnl1.setLayout(pnl1Layout);
        pnl1Layout.setHorizontalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnomor_antrian, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl1Layout.createSequentialGroup()
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtxtLoket, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLewat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLayan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPanggil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIstirahat, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addComponent(lblconnected, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl1Layout.setVerticalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1Layout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblconnected, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblnomor_antrian, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLayan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtLoket))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl1Layout.createSequentialGroup()
                        .addComponent(btnPanggil, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLewat, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnIstirahat, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLayanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayanActionPerformed
        if (btnLayan.getText().equals("Layani")) {
            cteller.sendLayan(pos, NOLOKET, loketname);
        } else {
            cteller.sendSiap(pos, NOLOKET, loketname);

        }

//        try {
//            if (btnLayan.getText().equals("Layani")) {
//                btnPanggil.setEnabled(false);
//                jedis.set(loketname + "_sts_no", nomor_aktif);
//     
//                sentence = NOLOKET;
//                sendData = sentence.getBytes();
//                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8888);
//                clientSocket.send(sendPacket);
//                String trim = tcpclient(loketname, "LAYAN", pos, NOLOKET, LED_PANEL).trim();
//                lblnomor_antrian.setForeground(Color.blue);
//                btnLayan.setText("Siap");
//                btnLewat.setEnabled(false);
//                btnIstirahat.setEnabled(false);
//            } else if (jedis.get("panngilno").equals("0")) {
//                int response = JOptionPane.showConfirmDialog(this, "No Antrian : '" + jedis.get(loketname + "_sts_no") + "' telah siap dilayani ?", "Confirm",
//                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                if (response == JOptionPane.YES_OPTION) {
//                    if (cekNoAntrian()) {
//                        nomor_aktif = tcpclient(loketname, "LEWAT", pos, NOLOKET, LED_PANEL).trim();
//                        lblnomor_antrian.setText(nomor_aktif);
//                        lblnomor_antrian.setForeground(Color.RED);
//                        HitungTotal();
//                        btnPanggil.setEnabled(true);
//                        btnLewat.setEnabled(true);
//                        btnLayan.setText("Layani");
//                        btnIstirahat.setEnabled(true);
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Antrian Kosong,...");
//                        btnPanggil.setEnabled(false);
//                        btnLayan.setEnabled(false);
//                        btnLewat.setEnabled(true);
//                        btnIstirahat.setEnabled(true);
//                    }
//
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btnLayanActionPerformed

    private void btnPanggilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPanggilActionPerformed
//        try {
//            String vantrian = tcpclient(loketname, "PANGGIL", pos, NOLOKET, LED_PANEL).trim();
//            //System.out.println(vantrian);
//            if (vantrian.equals("wait")) {
//                JOptionPane.showMessageDialog(rootPane, "Harap Tunggu Loket lain sedang melakukan Panggilan!");
//            } else {
//                lblnomor_antrian.setText(vantrian);
//                lblnomor_antrian.setForeground(Color.RED);
//                HitungTotal();
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
//        }
        cteller.sendPanggil(pos, NOLOKET, loketname);
        //System.out.println(pos);
    }//GEN-LAST:event_btnPanggilActionPerformed

    private void btnIstirahatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIstirahatActionPerformed
        try {

            if (btnIstirahat.getText().equals("Stop")) {                
                cteller.sendStop(pos, NOLOKET, loketname);
               clientSocket.close();
            } else {
                try {
            clientSocket = new DatagramSocket();
            try {
                IPAddress = InetAddress.getByName(LED_PANEL);
                //System.out.println(IPAddress);
            } catch (UnknownHostException ex) {
                Logger.getLogger(kioskClient_V2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SocketException ex) {
            Logger.getLogger(kioskClient_V2.class.getName()).log(Level.SEVERE, null, ex);
        }           
                cteller.sendMulai(pos, NOLOKET, loketname);
                
            }

        } catch (Exception ex) {
            Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnIstirahatActionPerformed

    private void btnLewatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLewatActionPerformed
        String vantrian = "";
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
        setCursor(hourglassCursor);
        try {
            // if (cekNoAntrian()) {
            //  vantrian = tcpclient(loketname, "LEWAT", pos, NOLOKET, LED_PANEL).trim();
            if (vantrian.equals("wait")) {
                JOptionPane.showMessageDialog(rootPane, "Harap Tunggu Loket lain sedang melakukan Panggilan!");
                //  lblnomor_antrian.setText(dummy_nm);
            } else {
                cteller.sendLewat(pos, NOLOKET, loketname);
                // System.out.println(pos + "  lok " + NOLOKET + " - " + loketname);

            }

        } catch (Exception ex) {
            Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
            setCursor(normalCursor);
        }

    }//GEN-LAST:event_btnLewatActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            //HitungTotal();
            //lblnomor_antrian.setText("000");
            // btnPanggil.setEnabled(false);
            // btnLewat.setEnabled(false);
            //btnLayan.setEnabled(false);
            btnIstirahat.setText("Mulai");
            btnIstirahat.setEnabled(true);
            // btnLayan.setText("Layani");

        } catch (Exception ex) {
            Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        clientSocket.close();
    }//GEN-LAST:event_formWindowClosed

    private boolean cekNoAntrian() {
        String CEKANTRIAN = "tidakada";
        boolean vAntrian = false;
        try {
            CEKANTRIAN = tcpclient(loketname, "CEKANTRIAN", pos, NOLOKET, LED_PANEL).trim();
        } catch (Exception ex) {
            Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (CEKANTRIAN.equals("ada")) {
                vAntrian = true;
            }
        }

        return vAntrian;
    }

    private void setNoAntrian(String pos, String count, String total, String layan) {
        lblTotal.setText(total);
        lblTotal1.setText(count);
        lblTerlayani.setText(layan);
        switch (pos) {
            case "1":
                lblnomor_antrian.setText("A-" + leftPad(nomor_aktif, 3, "0"));
                break;
            case "2":
                lblnomor_antrian.setText("B-" + leftPad(nomor_aktif, 3, "0"));
                break;
            default:
                lblnomor_antrian.setText("C-" + leftPad(nomor_aktif, 3, "0"));
                break;
        }
    }

    public void serverConnected() {
        lblconnected.setForeground(Color.green);
        lblconnected.setText("Connected");
        //btnIstirahat.setEnabled(true);
        //btnIstirahat.setText("Mulai");
        //cteller.sendMulai(pos, NOLOKET, loketname);
    }

    public void serverDisconected() {
        lblconnected.setForeground(Color.red);
        lblconnected.setText("Disconnect");
        //btnIstirahat.setEnabled(true);
        //btnIstirahat.setText("Mulai");
    }
    
    public static String rgtPad(String originalString, int length,
            String padCharacter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(padCharacter);
        }
        String padding = sb.toString();
        String paddedString = originalString+padding.substring(originalString.length());
        return paddedString;
    }

    public static String leftPad(String originalString, int length,
            String padCharacter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(padCharacter);
        }
        String padding = sb.toString();
        String paddedString = padding.substring(originalString.length())
                + originalString;
        return paddedString;
    }

    private void HitungTotal() {
        switch (pos) {
            case "1":
                atr_total = Integer.parseInt(jedis.get("totalnpwp"));
                atr_sisa = jedis.llen("q_npwp").intValue();
                break;
            case "2":
                atr_total = Integer.parseInt(jedis.get("totallain"));
                atr_sisa = jedis.llen("q_lain").intValue();
                break;
            default:
                atr_total = Integer.parseInt(jedis.get("totalsppt"));
                atr_sisa = jedis.llen("q_sppt").intValue();
                break;
        }

        lblTotal.setText(String.valueOf(atr_total));
        lblTotal1.setText(String.valueOf(atr_sisa));
        lblTerlayani.setText(jedis.get(loketname + "_layan"));
    }

    private static String tcpclient(String loket, String type, String pos, String NOLOKET, String LED_PANEL) throws Exception {
        // String sentence;
        String modifiedSentence = "";
        // BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
        try (Socket clientSocket = new Socket(socketServer, socketport)) {
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // sentence = inFromUser.readLine();
            outToServer.writeBytes(loket + "-" + type + "-" + pos + "-" + NOLOKET + "-" + LED_PANEL + "\n");
            modifiedSentence = inFromServer.readLine();

            // System.out.println(modifiedSentence);
        } catch (Exception ex) {
            // log(ex,"info","You can't divide anything by zero");
        }
        return modifiedSentence;
    }

    public static void log(Exception ex, String level, String msg) {

        FileHandler fh = null;
        try {
            fh = new FileHandler("log.xml", true);
            logger.addHandler(fh);
            switch (level) {
                case "severe":
                    logger.log(Level.SEVERE, msg, ex);
                    if (!msg.equals("")) {
                        JOptionPane.showMessageDialog(null, msg,
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "warning":
                    logger.log(Level.WARNING, msg, ex);
                    if (!msg.equals("")) {
                        JOptionPane.showMessageDialog(null, msg,
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case "info":
                    logger.log(Level.INFO, msg, ex);
                    if (!msg.equals("")) {
                        JOptionPane.showMessageDialog(null, msg,
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case "config":
                    logger.log(Level.CONFIG, msg, ex);
                    break;
                case "fine":
                    logger.log(Level.FINE, msg, ex);
                    break;
                case "finer":
                    logger.log(Level.FINER, msg, ex);
                    break;
                case "finest":
                    logger.log(Level.FINEST, msg, ex);
                    break;
                default:
                    logger.log(Level.CONFIG, msg, ex);
                    break;
            }
        } catch (IOException | SecurityException ex1) {
            logger.log(Level.SEVERE, null, ex1);
        } finally {
            if (fh != null) {
                fh.close();
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIstirahat;
    private javax.swing.JButton btnLayan;
    private javax.swing.JButton btnLewat;
    private javax.swing.JButton btnPanggil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jtxtLoket;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTerlayani;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotal1;
    private javax.swing.JLabel lblconnected;
    private javax.swing.JLabel lblnomor_antrian;
    private javax.swing.JPanel pnl1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void callback(JSONArray data) throws JSONException {

    }

    @Override
    public void on(String event, JSONObject obj) {
        try {
            switch (event) {
                case "user message":
                    // MessagesTextArea.append(obj.getString("user") + ": " + obj.getString("message") + "\n");
                    break;
                case "broadcast_panggil":
                    ///System.err.println(status_teller);
                    btnPanggil.setEnabled(false);
                    btnLayan.setEnabled(false);
                    btnIstirahat.setEnabled(false);
                    btnLewat.setEnabled(false);

                    if (pos.equals(obj.getString("pos"))) {
                        lblTotal1.setText(obj.getString("count"));
                        lblTotal.setText(obj.getString("total"));
                    }

                    if (!obj.getString("antrian").equals("1")) {
//                        btnPanggil.setEnabled(false);
//                        btnLayan.setEnabled(false);
//                        btnIstirahat.setEnabled(false);
//                        btnLewat.setEnabled(false);
//                    } 
//                    else {
                        switch (status_teller) {
                            case 1:
                                lblnomor_antrian.setForeground(Color.red);
                                btnLewat.setEnabled(true);
                                btnLayan.setEnabled(true);
                                btnPanggil.setEnabled(true);
                                btnIstirahat.setEnabled(true);
                                //status_teller = 1;
                                break;
                            case 2:
                                lblnomor_antrian.setForeground(Color.blue);
                                btnLayan.setText("Siap");
                                btnLayan.setEnabled(true);
                                btnIstirahat.setEnabled(true);
                                // status_teller = 2;
                                break;

                            case 3:
                                lblnomor_antrian.setForeground(Color.red);
                                btnLayan.setText("Layani");
                                btnPanggil.setEnabled(false);
                                btnLayan.setEnabled(false);
                                btnIstirahat.setEnabled(true);
                                btnLewat.setEnabled(true);
                                break;
                            case 4:
                                btnLewat.setEnabled(true);
                                btnIstirahat.setEnabled(true);
                                break;
                            default:
                                btnIstirahat.setEnabled(true);
                                break;
                        }
                    }
                    break;

                case "broadcast_panggil_id":
                    if (obj.getString("jml").equals("1") || obj.getString("jml").equals("2")) {
                    //   DatagramSocket ds = null;
                        try{
                            
                              
                                String str = "A-000"; 
                                String noantrian = obj.getString("noantrian");
                                switch (pos) {
                                case "1":
                                 //   lblnomor_antrian.setText("A-" + leftPad(nomor_aktif, 3, "0"));
                                     str =  "A-" + rgtPad(noantrian, 3, " ");
                                    break;
                                case "2":
                                  str = "B-" + rgtPad(noantrian, 3, " ");
                                    break;
                                case "3":
                                    str =  "C-" + rgtPad(noantrian, 3, " ");
                                    break;
                                default:
                                    break;
                                }
                                 // InetAddress ia = InetAddress.getByName(LED_PANEL);
                                  DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), IPAddress, 8888);
                                  clientSocket.send(dp);
                                  
} catch (SocketException ex) {
                                  //  Logger.getLogger(Kiosk_ServerV3.class.getName()).log(Level.SEVERE, null, ex);
                                  //  jedis.set("panngilno", "0");
                                } catch (UnknownHostException ex) {
                                   // Logger.getLogger(Kiosk_ServerV3.class.getName()).log(Level.SEVERE, null, ex);
                                   // jedis.set("panngilno", "0");
                                } catch (IOException ex) {
                                    //Logger.getLogger(Kiosk_ServerV3.class.getName()).log(Level.SEVERE, null, ex);
                                    //jedis.set("panngilno", "0");
                                } 
                              finally {
                            System.gc();
                              //ds.close();
                              }
                        status_teller = Integer.parseInt(obj.getString("status"));
                        switch (status_teller) {
                            case 1:
                                lblnomor_antrian.setForeground(Color.red);
                                btnLewat.setEnabled(true);
                                btnLayan.setEnabled(true);
                                btnPanggil.setEnabled(true);
                                btnIstirahat.setEnabled(true);
                                break;
                            case 2:
                                lblnomor_antrian.setForeground(Color.blue);
                                btnLayan.setText("Siap");
                                btnLayan.setEnabled(true);
                                btnIstirahat.setEnabled(true);
                                break;

                            case 3:
                                lblnomor_antrian.setForeground(Color.red);
                                btnLayan.setText("Layani");
                                btnPanggil.setEnabled(false);
                                btnLayan.setEnabled(false);
                                btnIstirahat.setEnabled(true);
                                btnLewat.setEnabled(true);
                                break;
                            case 4:
                                lblnomor_antrian.setForeground(Color.red);
                                //btnLayan.setText("Layani");
                               // btnPanggil.setEnabled(false);
                                //btnLayan.setEnabled(false);
                                btnIstirahat.setEnabled(true);
                                btnLewat.setEnabled(true);
                                break;
                                   case 5:
                                lblnomor_antrian.setForeground(Color.red);
                                //btnLayan.setText("Layani");
                               // btnPanggil.setEnabled(false);
                                //btnLayan.setEnabled(false);
                                btnIstirahat.setEnabled(true);
                                //btnLewat.setEnabled(true);
                                break;
                            default:
                                btnIstirahat.setEnabled(true);
                                break;
                        }
                        switch (pos) {
                            case "1":
                                lblnomor_antrian.setText("A-" + leftPad(obj.getString("noantrian"), 3, "0"));
                                break;
                            case "2":
                                lblnomor_antrian.setText("B-" + leftPad(obj.getString("noantrian"), 3, "0"));
                                break;
                            default:
                                lblnomor_antrian.setText("C-" + leftPad(obj.getString("noantrian"), 3, "0"));
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Antrian Kosong!");
                    }
                    break;

                case "mulai_teller":
                    nomor_aktif = obj.getString("noantrian");
                    if (btnIstirahat.getText().equals("Stop")) {
                        //clientSocket.close();
                        btnIstirahat.setText("Mulai");
                        //tcpclient(loketname, "ISTIRAHAT", pos, NOLOKET, LED_PANEL);

                    } else {
                        btnIstirahat.setText("Stop");
                        setNoAntrian(obj.getString("pos"), obj.getString("count"), obj.getString("total"), obj.getString("layan"));
                        //lblnomor_antrian.setText(obj.getString("noantrian"));
                        //tcpclient(loketname, "TERSEDIA", pos, NOLOKET, LED_PANEL);
//                        clientSocket = new DatagramSocket();
//                        try {
//                            IPAddress = InetAddress.getByName(LED_PANEL);
//                        } catch (UnknownHostException ex) {
//                            Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                        switch (obj.getString("status")) {
                            case "1":
                                btnLewat.setEnabled(true);
                                btnLayan.setEnabled(true);
                                btnPanggil.setEnabled(true);
                                status_teller = 1;
                                break;
                            case "2":
                                lblnomor_antrian.setForeground(Color.blue);
                                btnLayan.setText("Siap");
                                btnLayan.setEnabled(true);
                                status_teller = 2;
                                break;
                            case "3":
                                lblnomor_antrian.setForeground(Color.red);
                                btnLayan.setText("Layani");
                                btnLayan.setEnabled(false);
                                btnPanggil.setEnabled(false);
                                btnIstirahat.setEnabled(true);
                                btnLewat.setEnabled(true);
                                status_teller = 3;
                                break;

                            case "4":
                                btnLewat.setEnabled(true);
                                btnIstirahat.setEnabled(true);
                                status_teller = 4;
                                break;

                            default:
                                btnIstirahat.setEnabled(true);
                                status_teller = 0;
                                break;
                        }
                    }
                    break;

                case "siap_layan_antrian":
                    status_teller = 3;
                    btnLayan.setText("Layani");
                    btnPanggil.setEnabled(false);
                    btnLayan.setEnabled(false);
                    btnIstirahat.setEnabled(true);
                    btnLewat.setEnabled(true);
                    break;
                case "stop_teller":
                status_teller = 5;
                btnIstirahat.setText("Mulai");
                //tcpclient(loketname, "ISTIRAHAT", pos, NOLOKET, LED_PANEL);
                btnLayan.setEnabled(false);
                btnPanggil.setEnabled(false);
                btnLewat.setEnabled(false);
                this.stoptKiosk();
                break;
                case "sisa_antrian": 
                if (pos.equals(obj.getString("pos"))){
                    lblTotal1.setText(obj.getString("sisa"));
                }
                break;
                case "q_satu":
                    jLabel1.setText(obj.getString("jml"));
                    break;
                case "announcement":

                    break;
                case "okelayan_antrian":
                    try {
                        status_teller = 2;
                        try{
                                //String str = "A-000"; 
                                String loket = "LKT "+obj.getString("loket");
//                                switch (pos) {
//                                case "1":
//                                 //   lblnomor_antrian.setText("A-" + leftPad(nomor_aktif, 3, "0"));
//                                     str =  "A-" + rgtPad(noantrian, 3, " ");
//                                    break;
//                                case "2":
//                                  str = "B-" + rgtPad(noantrian, 3, " ");
//                                    break;
//                                case "3":
//                                    str =  "C-" + rgtPad(noantrian, 3, " ");
//                                    break;
//                                default:
//                                    break;
//                                }
                                 // InetAddress ia = InetAddress.getByName(LED_PANEL);
                                  DatagramPacket dp = new DatagramPacket(loket.getBytes(), loket.length(), IPAddress, 8888);
                                  clientSocket.send(dp);
                                  
                            
                        } catch (SocketException ex) {
                                  //  Logger.getLogger(Kiosk_ServerV3.class.getName()).log(Level.SEVERE, null, ex);
                                  //  jedis.set("panngilno", "0");
                                } catch (UnknownHostException ex) {
                                   // Logger.getLogger(Kiosk_ServerV3.class.getName()).log(Level.SEVERE, null, ex);
                                   // jedis.set("panngilno", "0");
                                } catch (IOException ex) {
                                    //Logger.getLogger(Kiosk_ServerV3.class.getName()).log(Level.SEVERE, null, ex);
                                    //jedis.set("panngilno", "0");
                                }
                        //  if (btnLayan.getText().equals("Layani")) {
                        btnPanggil.setEnabled(false);
                        btnLewat.setEnabled(false);
                        //jedis.set(loketname + "_sts_no", nomor_aktif);

                        //sentence = NOLOKET;
                        //sendData = sentence.getBytes();
                        //sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8888);
                        //clientSocket.send(sendPacket);
                        //String trim = tcpclient(loketname, "LAYAN", pos, NOLOKET, LED_PANEL).trim();
                        lblnomor_antrian.setForeground(Color.blue);
                        btnLayan.setText("Siap");
                        //btnLewat.setEnabled(false);
                        btnIstirahat.setEnabled(true);
                        lblTerlayani.setText(obj.getString("countlayan"));
                        //} 

//                        else if (jedis.get("panngilno").equals("0")) {
//                            int response = JOptionPane.showConfirmDialog(this, "No Antrian : '" + jedis.get(loketname + "_sts_no") + "' telah siap dilayani ?", "Confirm",
//                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                            if (response == JOptionPane.YES_OPTION) {
//                                if (cekNoAntrian()) {
//                                    nomor_aktif = tcpclient(loketname, "LEWAT", pos, NOLOKET, LED_PANEL).trim();
//                                    lblnomor_antrian.setText(nomor_aktif);
//                                    lblnomor_antrian.setForeground(Color.RED);
//                                    HitungTotal();
//                                    btnPanggil.setEnabled(true);
//                                    btnLewat.setEnabled(true);
//                                    btnLayan.setText("Layani");
//                                    btnIstirahat.setEnabled(true);
//                                } else {
//                                    JOptionPane.showMessageDialog(this, "Antrian Kosong,...");
//                                    btnPanggil.setEnabled(false);
//                                    btnLayan.setEnabled(false);
//                                    btnLewat.setEnabled(true);
//                                    btnIstirahat.setEnabled(true);
//                                }
//
//                            }
//                        }
                    } catch (Exception ex) {
                        Logger.getLogger(kioskClient_V3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "nicknames":
                    JSONArray names = obj.names();
                    String str = "";
                    for (int i = 0; i < names.length(); i++) {
                        if (i != 0) {
                            str += ", ";
                        }
                        str += names.getString(i);
                    }
                    //OnlineUsers.setText(str);
                    break;
                default:
                    break;
            }
        } catch (JSONException ex) {
        }
    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onMessage(JSONObject json) {

    }

    @Override
    public void onConnect() {
        System.out.println("connected!");
        cteller.join(loketname);
        cteller.sendcheck(NOLOKET);
        this.serverConnected();
        System.out.println("You joined as " + loketname);
    }

    @Override
    public void onDisconnect() {
        System.out.println("Connection lost");
        this.serverDisconected();
    }

    @Override
    public void onConnectFailure() {
        System.out.println("Connection Failure");
        this.serverDisconected();
    }
}
