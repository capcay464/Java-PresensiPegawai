/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formPresensi;

import java.awt.Color;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import koneksi.koneksi;


/**
 *
 * @author Gasom
 */
public class formPresensi extends javax.swing.JFrame {
    
    private Connection conn = new koneksi().connect();
    String waktu2;
    int status,hari,bulan,tahun,jam,menit,detik,telat;
    String idPresen;
    
    private Thread thread;

    /**
     * Creates new form formPresensi
     */
    public formPresensi() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        imgdefault();
        
        thread = new Thread(){
        public void run(){
            while (true){
            Calendar kal = new GregorianCalendar();
            tahun = kal.get(Calendar.YEAR);
            bulan = kal.get(Calendar.MONTH)+1;
            hari = kal.get(Calendar.DAY_OF_MONTH);
            jam = kal.get(Calendar.HOUR_OF_DAY);
            menit = kal.get(Calendar.MINUTE);
            detik = kal.get(Calendar.SECOND);
            
            DecimalFormat mFormat= new DecimalFormat("00");
            
            
            String tanggal = tahun+"-"+bulan+"-"+hari;
            String waktu1 = mFormat.format(Double.valueOf(jam)) +" : "+ mFormat.format(Double.valueOf(menit)) +" : "+ mFormat.format(Double.valueOf(detik));
            waktu2 = mFormat.format(Double.valueOf(jam)) +":"+ mFormat.format(Double.valueOf(menit)) +":"+ mFormat.format(Double.valueOf(detik));
            labeltanggal.setText(tanggal);
            labelwaktu.setText(waktu1);
            
            }
        }
        };
        thread.start();
    }
    
    private void imgdefault(){
        ImageIcon ico = new ImageIcon(getClass().getResource("/images/gd/a29zb25n.png"));
        Image img = ico.getImage();
        Image imgScale = img.getScaledInstance(jfoto.getWidth(), jfoto.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        jfoto.setIcon(scaledIcon);
    
    }
    
    private void kosong(){
        
                txnip.setText("");
                txnama.setText("");
                txjab.setText("");
                txshift.setText("");
                txbagian.setText("");
                txmasuk.setText("");
                txlokasi.setText("");
                txpresensi.setText("");
                
                imgdefault();
        
    }
    private void statusMasuk(){
                bgstatus.setBackground(Color.green);
                txhasil.setText("Masuk");
    
    }
    
     private void statusTelat(){
                bgstatus.setBackground(Color.orange);
                txhasil.setText("Terlambat");
    
    }
    
    private void statusUnvalid(){
                txhasil.setText("NIP Tidak Ditemukan");
                bgstatus.setBackground(Color.red);
    
    }
    
    private void statusAlredy(){
                
                
                if(status == 1){
                    txhasil.setText("Sudah Presensi : Masuk");
                    bgstatus.setBackground(Color.green);
                }else if (status == 2){
                    txhasil.setText("Sudah Presensi : Terlambat");
                    bgstatus.setBackground(Color.orange);
                }

    }
    
    private void autonumber(){
         String sql = "SELECT * FROM presensimasuk ORDER BY id_presensi DESC";
        try{          
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            if (r.next()) {
               String NoPresen = r.getString("id_presensi").substring(0);
               String BR = "" + (Integer.parseInt(NoPresen) + 1);
               idPresen = BR;
               
            }else {
               idPresen = "1";
            }
            r.close();
            s.close();
                    
        }catch(Exception e) {
            System.out.println("autonumber error"+e);
        }
    }
    
    private void tampilData(){
        
        kosong();   
        try {
            //ambil berdasrkan id
            String sqla = "select * from v_pegawai where nip = '"+txtnip.getText()+"'";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sqla);
            
            if(hasil.next()){
                
                txnip.setText(hasil.getString("nip"));
                txnama.setText(hasil.getString("nama"));
                txjab.setText(hasil.getString("nama_jabatan"));
                txshift.setText(hasil.getString("nama_shift"));
                txbagian.setText(hasil.getString("nama_bagian"));
                txmasuk.setText(hasil.getString("waktu_masuk"));
                txlokasi.setText(hasil.getString("nama_lokasi"));
                txpresensi.setText(waktu2);
               
                if("kosong".equals(hasil.getString("foto"))){
                        
                        ImageIcon ico = new ImageIcon(getClass().getResource("/images/gd/a29zb25n.png"));
                        Image img = ico.getImage();
                        Image imgScale = img.getScaledInstance(jfoto.getWidth(), jfoto.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(imgScale);
                        jfoto.setIcon(scaledIcon);
                        
                    } else {
                        ImageIcon ico = new ImageIcon(getClass().getResource("/images/"+hasil.getString("foto")));
                        Image img = ico.getImage();
                        Image imgScale = img.getScaledInstance(jfoto.getWidth(), jfoto.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(imgScale);
                        jfoto.setIcon(scaledIcon);
                    }
                
                
                 //menghitung selisih waktu
                String time1 = txpresensi.getText();
                String time2 = txmasuk.getText();
                
                try{
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Date date1 = format.parse(time1);
                Date date2 = format.parse(time2);
                long difference =  date1.getTime() - date2.getTime();
                String s = Long.toString(difference);
                
                if (difference <= 0){
                    statusMasuk();
                    status = 1;
                }else if (difference > 0){
                    statusTelat();
                    status = 2;
                    telat = 1;
                }
               
                
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "error"+e);
                }
                
                
                // buat mengecek sudah absen atau belom
                try{
                    String sqlb = "select * from v_presensimasuk where nip = '"+txtnip.getText()+"' and tanggal_presen = '"+hari+"' and bulan_presen = '"+bulan+"' and tahun_presen = '"+tahun+"'";
                    Statement statb = conn.createStatement();
                    ResultSet hasilb = statb.executeQuery(sqlb);
                    
                    if(hasilb.next()){
                        statusAlredy();
                        txpresensi.setText(hasilb.getString("jam_presen"));
                    }else {
                        String sqlc = "insert into presensimasuk values (?,?,?,?,?,?,?,?)";
                        try {
                        PreparedStatement statc = conn.prepareStatement(sqlc);
                        autonumber();
                        statc.setString(1, idPresen);
                        statc.setString(2, hasil.getString("id_pegawai"));
                        statc.setString(3, String.valueOf(hari));
                        statc.setString(4, String.valueOf(bulan));
                        statc.setString(5, String.valueOf(tahun));
                        statc.setString(6, txpresensi.getText());
                        statc.setString(7, String.valueOf(status));
                        statc.setInt(8, telat);
                        statc.executeUpdate();
                        }catch (SQLException e){
                            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan"+e);
                        }
                    }
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "Ada Kesalahan"+e);
                }
                
            }else {
                statusUnvalid();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ada Kesalahan"+e);
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

        jPanel1 = new javax.swing.JPanel();
        labelwaktu = new javax.swing.JLabel();
        labeltanggal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtnip = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        tx1 = new javax.swing.JLabel();
        tx2 = new javax.swing.JLabel();
        tx3 = new javax.swing.JLabel();
        txnip = new javax.swing.JLabel();
        txjab = new javax.swing.JLabel();
        txnama = new javax.swing.JLabel();
        tx6 = new javax.swing.JLabel();
        tx7 = new javax.swing.JLabel();
        tx8 = new javax.swing.JLabel();
        txshift = new javax.swing.JLabel();
        txlokasi = new javax.swing.JLabel();
        txbagian = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jfoto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tx4 = new javax.swing.JLabel();
        txmasuk = new javax.swing.JLabel();
        txpresensi = new javax.swing.JLabel();
        tx5 = new javax.swing.JLabel();
        bgstatus = new javax.swing.JPanel();
        txhasil = new javax.swing.JLabel();
        labeltanggal1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpan7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(845, 465));

        labelwaktu.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        labelwaktu.setForeground(new java.awt.Color(0, 102, 255));
        labelwaktu.setText("jLabel2");

        labeltanggal.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        labeltanggal.setForeground(new java.awt.Color(0, 102, 255));
        labeltanggal.setText("jLabel2");

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        txtnip.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtnip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnipActionPerformed(evt);
            }
        });
        txtnip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnipKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnipKeyTyped(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Presensi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tx1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx1.setForeground(new java.awt.Color(255, 255, 255));
        tx1.setText("NIP");

        tx2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx2.setForeground(new java.awt.Color(255, 255, 255));
        tx2.setText("Nama");

        tx3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx3.setForeground(new java.awt.Color(255, 255, 255));
        tx3.setText("Jabatan");

        txnip.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txnip.setForeground(new java.awt.Color(255, 255, 255));

        txjab.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txjab.setForeground(new java.awt.Color(255, 255, 255));

        txnama.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txnama.setForeground(new java.awt.Color(255, 255, 255));

        tx6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx6.setForeground(new java.awt.Color(255, 255, 255));
        tx6.setText("Shift");

        tx7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx7.setForeground(new java.awt.Color(255, 255, 255));
        tx7.setText("Lokasi");

        tx8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx8.setForeground(new java.awt.Color(255, 255, 255));
        tx8.setText("Bagian");

        txshift.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txshift.setForeground(new java.awt.Color(255, 255, 255));

        txlokasi.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txlokasi.setForeground(new java.awt.Color(255, 255, 255));

        txbagian.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txbagian.setForeground(new java.awt.Color(255, 255, 255));

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(118, 115));

        jfoto.setBackground(new java.awt.Color(0, 0, 0));

        jDesktopPane1.setLayer(jfoto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jfoto, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jfoto, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tx4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx4.setText("Jam Masuk");

        txmasuk.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txpresensi.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        tx5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx5.setText("Wakut Presensi");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx4)
                    .addComponent(tx5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txmasuk)
                    .addComponent(txpresensi))
                .addGap(56, 56, 56))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx4)
                    .addComponent(txmasuk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx5)
                    .addComponent(txpresensi))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        bgstatus.setBackground(new java.awt.Color(51, 51, 51));

        txhasil.setBackground(new java.awt.Color(255, 255, 255));
        txhasil.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txhasil.setForeground(new java.awt.Color(255, 255, 255));
        txhasil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txhasil.setText("Status");

        javax.swing.GroupLayout bgstatusLayout = new javax.swing.GroupLayout(bgstatus);
        bgstatus.setLayout(bgstatusLayout);
        bgstatusLayout.setHorizontalGroup(
            bgstatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txhasil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bgstatusLayout.setVerticalGroup(
            bgstatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txhasil, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        labeltanggal1.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        labeltanggal1.setForeground(new java.awt.Color(255, 255, 255));
        labeltanggal1.setText("NIP");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(labeltanggal1)
                        .addGap(18, 18, 18)
                        .addComponent(txtnip, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(247, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx3)
                            .addComponent(tx6)
                            .addComponent(tx7)
                            .addComponent(tx2)
                            .addComponent(tx1)
                            .addComponent(tx8))
                        .addGap(107, 107, 107)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txnama)
                            .addComponent(txnip)
                            .addComponent(txjab)
                            .addComponent(txshift)
                            .addComponent(txlokasi)
                            .addComponent(txbagian))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bgstatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnip, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labeltanggal1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bgstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx1)
                            .addComponent(txnip))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx2)
                            .addComponent(txnama))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx3)
                            .addComponent(txjab))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx6)
                            .addComponent(txshift))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx7)
                            .addComponent(txlokasi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txbagian)
                            .addComponent(tx8))))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(0, 153, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Presensi Masuk");

        btnSimpan7.setBackground(new java.awt.Color(255, 0, 0));
        btnSimpan7.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan7.setText("X");
        btnSimpan7.setBorder(null);
        btnSimpan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(16, 16, 16))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btnSimpan7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labeltanggal)
                .addGap(18, 18, 18)
                .addComponent(labelwaktu)
                .addGap(24, 24, 24))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labeltanggal)
                    .addComponent(labelwaktu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnipKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtnipKeyTyped

    private void txtnipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tampilData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSimpan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan7ActionPerformed
        // TODO add your handling code here:
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        thread.stop();
        this.dispose();
    }//GEN-LAST:event_btnSimpan7ActionPerformed

    private void txtnipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnipKeyPressed
        // TODO add your handling code here:
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilData();
         }
        
    }//GEN-LAST:event_txtnipKeyPressed

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
            java.util.logging.Logger.getLogger(formPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formPresensi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bgstatus;
    private javax.swing.JButton btnSimpan7;
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel jfoto;
    private javax.swing.JLabel labeltanggal;
    private javax.swing.JLabel labeltanggal1;
    private javax.swing.JLabel labelwaktu;
    private javax.swing.JLabel tx1;
    private javax.swing.JLabel tx2;
    private javax.swing.JLabel tx3;
    private javax.swing.JLabel tx4;
    private javax.swing.JLabel tx5;
    private javax.swing.JLabel tx6;
    private javax.swing.JLabel tx7;
    private javax.swing.JLabel tx8;
    private javax.swing.JLabel txbagian;
    private javax.swing.JLabel txhasil;
    private javax.swing.JLabel txjab;
    private javax.swing.JLabel txlokasi;
    private javax.swing.JLabel txmasuk;
    private javax.swing.JLabel txnama;
    private javax.swing.JLabel txnip;
    private javax.swing.JLabel txpresensi;
    private javax.swing.JLabel txshift;
    private javax.swing.JTextField txtnip;
    // End of variables declaration//GEN-END:variables
}
