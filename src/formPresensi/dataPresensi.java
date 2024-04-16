/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formPresensi;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.ImageIO;
import koneksi.koneksi;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Gasom
 */
public class dataPresensi extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    String waktu2;
    int status,hari,bulan,tahun,jam,menit,detik,pmasuktoday,pmasuktgl,pmasukbln,pmasukthn,pkeluartoday,pkeluartgl,pkeluarbln,pkeluarthn,pmasukall,pkeluarall;
    String stgl1,sbln1,sthn1;
    private Thread thread;

    /**
     * Creates new form dataPresensi
     */
    public dataPresensi() {
        initComponents();
        this.setLocationRelativeTo(null);
        
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
            //txttanggal.setText(tanggal);
            //labelwaktu.setText(waktu1);
            
            stgl1 = String.valueOf(hari);
            sbln1 = String.valueOf(bulan);
            sthn1 = String.valueOf(tahun);
            
            
            
           
            }
        }
            
        };
        thread.start();
            
            //blnn.setText(sbln1);
            //thnn.setText(sthn1);
            //ctgl.setSelectedItem(stgl1);
            
            
            
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(lthn1, "#");
        lthn1.setEditor(editor);
        JSpinner.NumberEditor editor2 = new JSpinner.NumberEditor(lthn2, "#");
        lthn2.setEditor(editor2);
        
        pmasukall = 0;
        pmasuktoday = 0;
        pmasuktgl = 0;
        pmasukbln = 0;
        pmasukthn = 0;
        
        pkeluarall = 0;
        pkeluartoday = 0;
        pkeluartgl = 0;
        pkeluarbln = 0;
        pkeluarthn = 0;
        
        
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        caridataPresensimasuk();
        caridataPresensikeluar();
            
    }
    
    
    private void kosong(){
    
        txcari1.setText("");
        caridataPresensimasuk();
    
    }
    private void kosong1(){
    
        txcari2.setText("");
        caridataPresensikeluar();
    
    }
    
    
    private void caridataPresensimasuk(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensimasuk order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        
                       
                        hasil.getString(9),
                        hasil.getString(14),
                        hasil.getString(15),
                        
                        
                    
                    });
               }
                tblpresensi.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
    
    
     private void caridataPresensikeluar(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Waktu Keluar","Presensi Keluar","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensikeluar order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(3),
                        hasil.getString(4),
                        
                       
                        hasil.getString(10),
                        hasil.getString(15),
                        hasil.getString(11),
                        hasil.getString(16),
                        hasil.getString(17),
                        
                        
                    
                    });
               }
                tblpresensi1.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
    

     private void caridataPresensimasukToday(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari1.getText();
            String tgl1 = String.valueOf(hari);
            String bln1 = String.valueOf(bulan);
            String thn1 = String.valueOf(tahun);
            
            ltgl1.setValue(hari);
            lbln1.setValue(bulan);
            lthn1.setValue(tahun);
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensimasuk where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and tanggal_presen = '"+tgl1+"' and bulan_presen = '"+bln1+"' and tahun_presen = '"+thn1+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        
                        
                        hasil.getString(9),
                        hasil.getString(14),
                        hasil.getString(15),
                        
                        
                    
                    });
               }
                tblpresensi.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
     
     
     private void caridataPresensikeluarToday(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Waktu Keluar","Presensi Keluar","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari2.getText();
            String tgl1 = String.valueOf(hari);
            String bln1 = String.valueOf(bulan);
            String thn1 = String.valueOf(tahun);
            
            ltgl2.setValue(hari);
            lbln2.setValue(bulan);
            lthn2.setValue(tahun);
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensikeluar where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and tanggal_presen = '"+tgl1+"' and bulan_presen = '"+bln1+"' and tahun_presen = '"+thn1+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(3),
                        hasil.getString(4),
                        
                       
                        hasil.getString(10),
                        hasil.getString(15),
                        hasil.getString(11),
                        hasil.getString(16),
                        hasil.getString(17),
                        
                        
                    
                    });
               }
                tblpresensi1.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
     
     private void caridataPresensikeluartanggal(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Waktu Keluar","Presensi Keluar","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari2.getText();
            String tgl2 = String.valueOf(ltgl2.getValue());
            String bln2 = String.valueOf(lbln2.getValue());
            String thn2 = String.valueOf(lthn2.getValue());
            
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensikeluar where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and tanggal_presen = '"+tgl2+"' and bulan_presen = '"+bln2+"' and tahun_presen = '"+thn2+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(3),
                        hasil.getString(4),
                        
                       
                        hasil.getString(10),
                        hasil.getString(15),
                        hasil.getString(11),
                        hasil.getString(16),
                        hasil.getString(17),
                        
                        
                    
                    });
               }
                tblpresensi1.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
     
     
     private void caridataPresensikeluarbulan(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Waktu Keluar","Presensi Keluar","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari2.getText();
            String tgl2 = String.valueOf(ltgl2.getValue());
            String bln2 = String.valueOf(lbln2.getValue());
            String thn2 = String.valueOf(lthn2.getValue());
            
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensikeluar where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and bulan_presen = '"+bln2+"' and tahun_presen = '"+thn2+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(3),
                        hasil.getString(4),
                        
                       
                        hasil.getString(10),
                        hasil.getString(15),
                        hasil.getString(11),
                        hasil.getString(16),
                        hasil.getString(17),
                        
                        
                    
                    });
               }
                tblpresensi1.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
     
     private void caridataPresensikeluartahun(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Waktu Keluar","Presensi Keluar","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari2.getText();
            String tgl2 = String.valueOf(ltgl2.getValue());
            String bln2 = String.valueOf(lbln2.getValue());
            String thn2 = String.valueOf(lthn2.getValue());
            
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensikeluar where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and tahun_presen = '"+thn2+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(3),
                        hasil.getString(4),
                        
                       
                        hasil.getString(10),
                        hasil.getString(15),
                        hasil.getString(11),
                        hasil.getString(16),
                        hasil.getString(17),
                        
                        
                    
                    });
               }
                tblpresensi1.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
     
     
     private void caridataPresensimasukTanggal(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari1.getText();
            
            String tgl1 = String.valueOf(ltgl1.getValue());
            String bln1 = String.valueOf(lbln1.getValue());
            String thn1 = String.valueOf(lthn1.getValue());
            
            
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensimasuk where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and tanggal_presen = '"+tgl1+"' and bulan_presen = '"+bln1+"' and tahun_presen = '"+thn1+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        
                        
                        hasil.getString(9),
                        hasil.getString(14),
                        hasil.getString(15),
                        
                        
                    
                    });
               }
                tblpresensi.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
     
     private void caridataPresensimasukBulan(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari1.getText();
            
            String bln1 = String.valueOf(lbln1.getValue());
            String thn1 = String.valueOf(lthn1.getValue());
            
            
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensimasuk where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and bulan_presen = '"+bln1+"' and tahun_presen = '"+thn1+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        
                       
                        hasil.getString(9),
                        hasil.getString(14),
                        hasil.getString(15),
                        
                        
                    
                    });
               }
                tblpresensi.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    }
     
       private void caridataPresensimasukTahun(){
        
        Object[] Baris = {"ID","NIP","Nama Pegawai","Waktu Masuk","Presensi","Keterangan"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari1.getText();
            
            
            String thn1 = String.valueOf(lthn1.getValue());
            
            
            
            //(nama = '"+cariitem+"' or nip = '"+cariitem+"') and
            
            try {
                String sql = "SELECT * from v_presensimasuk where (nip like '%"+cariitem+"%' or nama like '%"+cariitem+"%') and tahun_presen = '"+thn1+"' order by id_presensi asc";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    tabmode.addRow(new Object[]{
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        
                        
                        hasil.getString(9),
                        hasil.getString(14),
                        hasil.getString(15),
                        
                        
                    
                    });
               }
                tblpresensi.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
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
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpresensi = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        txcari1 = new javax.swing.JTextField();
        labeltanggal7 = new javax.swing.JLabel();
        labeltanggal1 = new javax.swing.JLabel();
        labeltanggal2 = new javax.swing.JLabel();
        labeltanggal3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ltgl1 = new javax.swing.JSpinner();
        lbln1 = new javax.swing.JSpinner();
        lthn1 = new javax.swing.JSpinner();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblpresensi1 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        txcari2 = new javax.swing.JTextField();
        labeltanggal8 = new javax.swing.JLabel();
        labeltanggal4 = new javax.swing.JLabel();
        labeltanggal5 = new javax.swing.JLabel();
        labeltanggal6 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ltgl2 = new javax.swing.JSpinner();
        lbln2 = new javax.swing.JSpinner();
        lthn2 = new javax.swing.JSpinner();
        jButton19 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(0, 153, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Laporan Presensi");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1097, Short.MAX_VALUE)
                .addComponent(btnSimpan7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(16, 16, 16))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btnSimpan7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA PRESENSI MASUK");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(14, 14, 14))
        );

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

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txcari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txcari1KeyTyped(evt);
            }
        });

        labeltanggal7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal7.setText("NIP / Nama :");

        labeltanggal1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal1.setText("Data Pada Tanggal :");

        labeltanggal2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal2.setText("Bulan :");

        labeltanggal3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal3.setText("Tahun :");

        jButton2.setBackground(new java.awt.Color(255, 51, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Per-Bulan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 51, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Per-Tahun");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 51, 0));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Hari Ini");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 51, 51));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Reset");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(51, 51, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Semua Data");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Filter Data");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ltgl1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        lbln1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        lthn1.setModel(new javax.swing.SpinnerNumberModel(2022, 2022, null, 1));

        jButton3.setBackground(new java.awt.Color(255, 51, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Per-Tanggal");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(labeltanggal7)
                                .addGap(55, 55, 55)
                                .addComponent(txcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(labeltanggal1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ltgl1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(labeltanggal2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbln1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labeltanggal3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lthn1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labeltanggal2)
                    .addComponent(labeltanggal1)
                    .addComponent(labeltanggal3)
                    .addComponent(ltgl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbln1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lthn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txcari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labeltanggal7))
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton8.setBackground(new java.awt.Color(0, 255, 0));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Print Laporan");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DATA PRESENSI KELUAR");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(14, 14, 14))
        );

        tblpresensi1.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpresensi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpresensi1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblpresensi1);

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txcari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txcari2KeyTyped(evt);
            }
        });

        labeltanggal8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal8.setText("NIP / Nama :");

        labeltanggal4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal4.setText("Data Pada Tanggal :");

        labeltanggal5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal5.setText("Bulan :");

        labeltanggal6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        labeltanggal6.setText("Tahun :");

        jButton14.setBackground(new java.awt.Color(255, 51, 0));
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Per-Bulan");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(255, 51, 0));
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Per-Tahun");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(255, 51, 0));
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Hari Ini");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(255, 51, 51));
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Reset");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(51, 51, 255));
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Semua Data");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Filter Data");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ltgl2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        lbln2.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        lthn2.setModel(new javax.swing.SpinnerNumberModel(2022, 2022, null, 1));

        jButton19.setBackground(new java.awt.Color(255, 51, 0));
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Per-Tanggal");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(labeltanggal8)
                                .addGap(55, 55, 55)
                                .addComponent(txcari2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(labeltanggal4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ltgl2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(labeltanggal5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbln2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labeltanggal6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lthn2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labeltanggal5)
                    .addComponent(labeltanggal4)
                    .addComponent(labeltanggal6)
                    .addComponent(ltgl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbln2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lthn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txcari2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labeltanggal8))
                .addGap(17, 17, 17)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton9.setBackground(new java.awt.Color(0, 255, 0));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Print Laporan");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton9)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                .addGap(0, 16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblpresensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpresensiMouseClicked
        
    }//GEN-LAST:event_tblpresensiMouseClicked

    private void tblpresensi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpresensi1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblpresensi1MouseClicked

    private void txcari1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcari1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txcari1KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        caridataPresensimasukBulan();
        pmasukall = 0;
        pmasuktoday = 0;
        pmasuktgl = 0;
        pmasukbln = 1;
        pmasukthn = 0;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        caridataPresensimasukTahun();
        pmasukall = 0;
        pmasuktoday = 0;
        pmasuktgl = 0;
        pmasukbln = 0;
        pmasukthn = 1;
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        caridataPresensimasukToday();
        pmasukall = 0;
        pmasuktoday = 1;
        pmasuktgl = 0;
        pmasukbln = 0;
        pmasukthn = 0;
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        kosong();
        pmasukall = 1;
        pmasuktoday = 0;
        pmasuktgl = 0;
        pmasukbln = 0;
        pmasukthn = 0;
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        caridataPresensimasuk();
        pmasukall = 1;
        pmasuktoday = 0;
        pmasuktgl = 0;
        pmasukbln = 0;
        pmasukthn = 0;
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        caridataPresensimasukTanggal();
        pmasukall = 0;
        pmasuktoday = 0;
        pmasuktgl = 1;
        pmasukbln = 0;
        pmasukthn = 0;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txcari2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcari2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txcari2KeyTyped

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        caridataPresensikeluarbulan();
        pkeluarall = 0;
        pkeluartoday = 0;
        pkeluartgl = 0;
        pkeluarbln = 1;
        pkeluarthn = 0;
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        caridataPresensikeluartahun();
        pkeluarall = 0;
        pkeluartoday = 0;
        pkeluartgl = 0;
        pkeluarbln = 0;
        pkeluarthn = 1;
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        caridataPresensikeluarToday();
        pkeluarall = 0;
        pkeluartoday = 1;
        pkeluartgl = 0;
        pkeluarbln = 0;
        pkeluarthn = 0;
        
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        kosong1();
        pkeluarall = 1;
        pkeluartoday = 0;
        pkeluartgl = 0;
        pkeluarbln = 0;
        pkeluarthn = 0;
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        caridataPresensikeluar();
        pkeluarall = 1;
        pkeluartoday = 0;
        pkeluartgl = 0;
        pkeluarbln = 0;
        pkeluarthn = 0;
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        caridataPresensikeluartanggal();
        pkeluarall = 0;
        pkeluartoday = 0;
        pkeluartgl = 1;
        pkeluarbln = 0;
        pkeluarthn = 0;
        
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        
//        if(pmasukall == 1){
//            JOptionPane.showMessageDialog(null, "Print Semua Data?");
//        }else if(pmasuktoday == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Hari Ini?");
//        }else if(pmasuktgl == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Tanggal Terpilih?");
//        }else if(pmasukbln == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Bulan Terpilih?");
//        }else if(pmasukthn == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Tahun Terpilih?");
//        }else {
//            JOptionPane.showMessageDialog(null, "Atur Filter Data Yang Ingin di Print?");
//        }

        String bln1 = String.valueOf(lbln1.getValue());
        String bln22 = "5";
        try {
        HashMap parameter = new HashMap();
        parameter.put("bulan_presensi", bln22);
        JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporan_presensimasuk_bulan.jasper"), null,conn);
       JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
           JOptionPane.showMessageDialog(rootPane, e);
       }

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
//         if(pkeluarall == 1){
//            JOptionPane.showMessageDialog(null, "Print Semua Data?");
//        }else if(pkeluartoday == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Hari Ini?");
//        }else if(pkeluartgl == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Tanggal Terpilih?");
//        }else if(pkeluarbln == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Bulan Terpilih?");
//        }else if(pkeluarthn == 1){
//            JOptionPane.showMessageDialog(null, "Print Data Tahun Terpilih?");
//        }else {
//            JOptionPane.showMessageDialog(null, "Atur Filter Data Yang Ingin di Print?");
//        }
         
         try {
        JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporan_presensikeluar.jasper"), null,conn);
       JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
           JOptionPane.showMessageDialog(rootPane, e);
       }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btnSimpan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan7ActionPerformed
        // TODO add your handling code here:
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        thread.stop();
        this.dispose();
    }//GEN-LAST:event_btnSimpan7ActionPerformed

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
            java.util.logging.Logger.getLogger(dataPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataPresensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataPresensi().setVisible(true);
            }
        });
        
            
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan7;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labeltanggal1;
    private javax.swing.JLabel labeltanggal2;
    private javax.swing.JLabel labeltanggal3;
    private javax.swing.JLabel labeltanggal4;
    private javax.swing.JLabel labeltanggal5;
    private javax.swing.JLabel labeltanggal6;
    private javax.swing.JLabel labeltanggal7;
    private javax.swing.JLabel labeltanggal8;
    private javax.swing.JSpinner lbln1;
    private javax.swing.JSpinner lbln2;
    private javax.swing.JSpinner ltgl1;
    private javax.swing.JSpinner ltgl2;
    private javax.swing.JSpinner lthn1;
    private javax.swing.JSpinner lthn2;
    private javax.swing.JTable tblpresensi;
    private javax.swing.JTable tblpresensi1;
    private javax.swing.JTextField txcari1;
    private javax.swing.JTextField txcari2;
    // End of variables declaration//GEN-END:variables
}
