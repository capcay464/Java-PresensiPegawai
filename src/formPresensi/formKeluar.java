/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formPresensi;

import java.awt.event.KeyEvent;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.ImageIO;
import koneksi.koneksi;
import menu.formMenu;


/**
 *
 * @author Gasom
 */
public class formKeluar extends javax.swing.JFrame {
    
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    String waktu2;
    int status,hari,bulan,tahun,jam,menit,detik,stat = 0;
    String idPresen,idPKeluar;
    
    private Thread thread;
 

    /**
     * Creates new form formKeluar
     */
    public formKeluar() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        tblpresensi.setDefaultEditor(Object.class, null);
        
        
    
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
            txttanggal.setText(tanggal);
            labelwaktu.setText(waktu1);
           
            }
        }
            
        };
        
            
       
            thread.start();
        
        
                
        dataPresensi();
        buttonrefresh.doClick();
        imgdefault();
        
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
                txjabatan.setText("");
                txshift.setText("");
                txlokasi.setText("");
                txbagian.setText("");
                txketerangan.setText("");
                txmasuk.setText("");
                txpresensi.setText("");
                txjamkeluar.setText("");
                txpkeluar.setText("");
                imgdefault();
    }
    
    private void dataPresensi(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Foto","Jabatan","Bagian","Lokasi","Shift","Jam Masuk","Tanggal","Bulan","Tahun","Waktu Presensi","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari.getText();
            
            try {
                String sql = "SELECT * from v_presensimasuk where tanggal_presen = '"+hari+"' and bulan_presen = '"+bulan+"' and tahun_presen = '"+tahun+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5),
                        hasil.getString(6),
                        hasil.getString(7),
                        hasil.getString(8),
                        hasil.getString(9),
                        hasil.getString(10),
                        hasil.getString(11),
                        hasil.getString(12),
                        hasil.getString(13),
                        hasil.getString(14),
                    
                    });
               }
                tblpresensi.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
    
    private void caridataPresensi(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Foto","Jabatan","Bagian","Lokasi","Shift","Jam Masuk","Tanggal","Bulan","Tahun","Waktu Presensi","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari.getText();
            
            try {
                String sql = "SELECT * from v_presensimasuk where (nama = '"+cariitem+"' or nip = '"+cariitem+"') and tanggal_presen = '"+hari+"' and bulan_presen = '"+bulan+"' and tahun_presen = '"+tahun+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5),
                        hasil.getString(6),
                        hasil.getString(7),
                        hasil.getString(8),
                        hasil.getString(9),
                        hasil.getString(10),
                        hasil.getString(11),
                        hasil.getString(12),
                        hasil.getString(13),
                        hasil.getString(14),
                    
                    });
               }
                tblpresensi.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
    
    private void autonumber(){
         String sql = "SELECT * FROM presensikeluar ORDER BY id_pkeluar DESC";
        try{          
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            if (r.next()) {
               String NoPresen = r.getString("id_pkeluar").substring(0);
               String BR = "" + (Integer.parseInt(NoPresen) + 1);
               idPKeluar = BR;
               
            }else {
               idPKeluar = "1";
            }
            r.close();
            s.close();
                    
        }catch(Exception e) {
            System.out.println("autonumber error"+e);
        }
    }
    
    private void inputKeluar(){
        
        // buat mengecek sudah absen atau belom
        try{
            autonumber();
            String sqlb = "select * from v_presensikeluar where nip = '"+txnip.getText()+"' and tanggal_presen = '"+hari+"' and bulan_presen = '"+bulan+"' and tahun_presen = '"+tahun+"'";
            Statement statb = conn.createStatement();
            ResultSet hasilb = statb.executeQuery(sqlb);
            
            if(hasilb.next()){
                JOptionPane.showMessageDialog(null, "Sudah Melakukan Presensi Keluar");
                txpkeluar.setText(hasilb.getString("jam_keluar"));
            }else {
                
                 
                if("".equals(txnip.getText())){
                    JOptionPane.showMessageDialog(null, "Anda Belum Memilih Pegawai");
                }else {
                    
                            //menghitung selisih waktu
                        String time1 = txjamkeluar.getText();
                        String time2 = txpkeluar.getText();

                        try{
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        java.util.Date date1 = format.parse(time1);
                        java.util.Date date2 = format.parse(time2);
                        long difference =  date1.getTime() - date2.getTime();
                        String s = Long.toString(difference);

                        if (difference >= 0){
                            status = 1;
                        }else if (difference < 0){
                            status = 2;
                        }


                        }catch (Exception e){
                            JOptionPane.showMessageDialog(null, "error"+e);
                        }
                    
                    String sqlc = "insert into presensikeluar values (?,?,?,?)";
                        try {
                        PreparedStatement statc = conn.prepareStatement(sqlc);
                        autonumber();
                        statc.setString(1, idPKeluar);
                        statc.setString(2, idPresen);
                        statc.setString(3, waktu2);
                        
                        if(status == 1){
                            String ket = "Sesudah Waktu Keluar";
                            statc.setString(4, ket);
                        }else if(status == 2){
                            String ket = "Sebelum Waktu Keluar";
                            statc.setString(4, ket);
                        }
                        
                        
                        statc.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Berhasil Melakukan Presensi Keluar");
                        kosong();
                        }catch (SQLException e){
                            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan"+e);
                        }
                }
                
                
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
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpan7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txcari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpresensi = new javax.swing.JTable();
        buttonrefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labeltanggal7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        labelwaktu = new javax.swing.JLabel();
        txttanggal = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jfoto = new javax.swing.JLabel();
        tx1 = new javax.swing.JLabel();
        tx2 = new javax.swing.JLabel();
        tx3 = new javax.swing.JLabel();
        tx6 = new javax.swing.JLabel();
        tx7 = new javax.swing.JLabel();
        tx8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tx4 = new javax.swing.JLabel();
        txmasuk = new javax.swing.JLabel();
        txpresensi = new javax.swing.JLabel();
        tx5 = new javax.swing.JLabel();
        txjamkeluar = new javax.swing.JLabel();
        tx10 = new javax.swing.JLabel();
        tx11 = new javax.swing.JLabel();
        txpkeluar = new javax.swing.JLabel();
        tx9 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txnip = new javax.swing.JLabel();
        txjabatan = new javax.swing.JLabel();
        txnama = new javax.swing.JLabel();
        txshift = new javax.swing.JLabel();
        txlokasi = new javax.swing.JLabel();
        txbagian = new javax.swing.JLabel();
        txketerangan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel9.setBackground(new java.awt.Color(0, 153, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Presensi Keluar");

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txcari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txcariActionPerformed(evt);
            }
        });
        txcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txcariKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txcariKeyTyped(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblpresensi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblpresensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpresensiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpresensi);

        buttonrefresh.setBackground(new java.awt.Color(0, 204, 0));
        buttonrefresh.setForeground(new java.awt.Color(255, 255, 255));
        buttonrefresh.setText("Refresh Data");
        buttonrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonrefreshActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("*Klik Refresh data jika data tidak muncul");

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        labeltanggal7.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        labeltanggal7.setForeground(new java.awt.Color(255, 255, 255));
        labeltanggal7.setText("Data Presensi Untuk Hari Ini");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labeltanggal7, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labeltanggal7)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txcari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(buttonrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txcari, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(39, 39, 39))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelwaktu.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        labelwaktu.setForeground(new java.awt.Color(0, 102, 255));
        labelwaktu.setText("jLabel2");

        txttanggal.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txttanggal.setForeground(new java.awt.Color(0, 102, 255));
        txttanggal.setText("jLabel2");

        jDesktopPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
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

        tx1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx1.setText("NIP");

        tx2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx2.setText("Nama");

        tx3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx3.setText("Jabatan");

        tx6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx6.setText("Shift");

        tx7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx7.setText("Lokasi");

        tx8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx8.setText("Bagian");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tx4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx4.setText("Jam Masuk");

        txmasuk.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txpresensi.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        tx5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx5.setText("Wakut Presensi");

        txjamkeluar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        tx10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx10.setText("Jam Keluar");

        tx11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx11.setText("Waktu Presen Keluar");

        txpkeluar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(tx11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(txpkeluar))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx5)
                            .addComponent(tx4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txmasuk, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txpresensi, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(tx10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txjamkeluar)))
                .addGap(67, 67, 67))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx4)
                    .addComponent(txmasuk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx5)
                    .addComponent(txpresensi))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx10)
                    .addComponent(txjamkeluar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx11)
                    .addComponent(txpkeluar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tx9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tx9.setText("Keterangan");

        jButton3.setBackground(new java.awt.Color(51, 51, 255));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Presensi Keluar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txnip.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txjabatan.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txnama.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txshift.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txlokasi.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txbagian.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        txketerangan.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx9)
                            .addComponent(tx3)
                            .addComponent(tx6)
                            .addComponent(tx7)
                            .addComponent(tx2)
                            .addComponent(tx8))
                        .addGap(252, 254, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(tx1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txjabatan)
                            .addComponent(txnip)
                            .addComponent(txnama)
                            .addComponent(txshift)
                            .addComponent(txlokasi)
                            .addComponent(txbagian)
                            .addComponent(txketerangan))
                        .addGap(86, 86, 86))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txttanggal)
                        .addGap(18, 18, 18)
                        .addComponent(labelwaktu)
                        .addGap(15, 15, 15))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelwaktu)
                    .addComponent(txttanggal))
                .addGap(31, 31, 31)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx1)
                    .addComponent(txnip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx2)
                    .addComponent(txnama))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx3)
                    .addComponent(txjabatan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx6)
                    .addComponent(txshift))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx7)
                    .addComponent(txlokasi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx8)
                    .addComponent(txbagian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx9)
                    .addComponent(txketerangan))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan7ActionPerformed
        // TODO add your handling code here:
        
        thread.stop();
        dispose();
    }//GEN-LAST:event_btnSimpan7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        caridataPresensi();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcariKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txcariKeyTyped

    private void txcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcariKeyPressed
        // TODO add your handling code here:

        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            caridataPresensi();
        }
    }//GEN-LAST:event_txcariKeyPressed

    private void txcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txcariActionPerformed

    private void buttonrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonrefreshActionPerformed
        // TODO add your handling code here:
        dataPresensi();
    }//GEN-LAST:event_buttonrefreshActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        inputKeluar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblpresensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpresensiMouseClicked
        // TODO add your handling code here:
        int v = tblpresensi.getSelectedRow();
        if (v == -1){
            return;
        }
        
        int baris = tblpresensi.getSelectedRow();

        String a = tabmode.getValueAt(baris, 0).toString();
        
        //ambil berdasrkan id
        String sql = "select * from v_presensimasuk where id_presensi = '"+a+"'";
        
        try {
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            
            while(hasil.next()){
                idPresen = hasil.getString("id_presensi");
                txnip.setText(hasil.getString("nip"));
                txnama.setText(hasil.getString("nama"));
                txjabatan.setText(hasil.getString("nama_jabatan"));
                txshift.setText(hasil.getString("nama_shift"));
                txlokasi.setText(hasil.getString("nama_lokasi"));
                txbagian.setText(hasil.getString("nama_bagian"));
                txketerangan.setText(hasil.getString("keterangan"));
                txmasuk.setText(hasil.getString("waktu_masuk"));
                txpresensi.setText(hasil.getString("jam_presen"));
                txjamkeluar.setText(hasil.getString("waktu_keluar"));
                txpkeluar.setText(waktu2);
                
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
            
            }
        
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "error"+ex);
        }
    }//GEN-LAST:event_tblpresensiMouseClicked

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
            java.util.logging.Logger.getLogger(formKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formKeluar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan7;
    private javax.swing.JButton buttonrefresh;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jfoto;
    private javax.swing.JLabel labeltanggal7;
    private javax.swing.JLabel labelwaktu;
    private javax.swing.JTable tblpresensi;
    private javax.swing.JLabel tx1;
    private javax.swing.JLabel tx10;
    private javax.swing.JLabel tx11;
    private javax.swing.JLabel tx2;
    private javax.swing.JLabel tx3;
    private javax.swing.JLabel tx4;
    private javax.swing.JLabel tx5;
    private javax.swing.JLabel tx6;
    private javax.swing.JLabel tx7;
    private javax.swing.JLabel tx8;
    private javax.swing.JLabel tx9;
    private javax.swing.JLabel txbagian;
    private javax.swing.JTextField txcari;
    private javax.swing.JLabel txjabatan;
    private javax.swing.JLabel txjamkeluar;
    private javax.swing.JLabel txketerangan;
    private javax.swing.JLabel txlokasi;
    private javax.swing.JLabel txmasuk;
    private javax.swing.JLabel txnama;
    private javax.swing.JLabel txnip;
    private javax.swing.JLabel txpkeluar;
    private javax.swing.JLabel txpresensi;
    private javax.swing.JLabel txshift;
    private javax.swing.JLabel txttanggal;
    // End of variables declaration//GEN-END:variables
}
