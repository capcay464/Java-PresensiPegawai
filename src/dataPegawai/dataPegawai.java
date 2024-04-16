/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dataPegawai;
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
import javax.imageio.ImageIO;
import koneksi.koneksi;
/**
 *
 * @author Gasom
 */
public class dataPegawai extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    private String gambar;
    private String kosong = "kosong";
    private Path copy,files;    
    
    String nama_jbt,nama_bgn,nama_lks,nama_shift,sql,id;
    int id_jbt,id_bgn,id_lks,id_shift;

    //arrya buat combo box
    ArrayList<jabatan> arrJabatan = new ArrayList<>();
    ArrayList<Bagian> arrBagian = new ArrayList<>();
    ArrayList<lokasi> arrLokasi = new ArrayList<>();
    ArrayList<shift> arrShift = new ArrayList<>();
    /**
     * Creates new form dataPegawai
     */
    public dataPegawai() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        tblpegawai.setDefaultEditor(Object.class, null);
        txid.setEnabled(false);
        btnedit1.setEnabled(false);
        btntambah.setEnabled(true);
        
        
        
        kosong();
        autonumber();
        showData();
        loadJabatan();
        loadBagian();
        loadLokasi();
        loadShift();
        imgdefault();
    }
    
     private void imgdefault(){
        ImageIcon ico = new ImageIcon(getClass().getResource("/images/gd/a29zb25n.png"));
        Image img = ico.getImage();
        Image imgScale = img.getScaledInstance(jfoto.getWidth(), jfoto.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        jfoto.setIcon(scaledIcon);
    
    }
    
    private void showData(){

            Object[] Baris = {"ID","NIP","Nama Pegawai","Alamat","Kota","Jenis Kelamin","TGL Lahir","Jabatan","Bagian","Shift","Lokasi"};
            tabmode = new DefaultTableModel(null, Baris);
            String cariitem=txcari.getText();
            
            try {
                String sql = "SELECT id_pegawai, nip, nama, alamat, kota, jenis_kelamin, tgl_lahir, nama_jabatan, nama_bagian, nama_shift, nama_lokasi from v_pegawai where id_pegawai like '%"+cariitem+"%' or nama like '%"+cariitem+"%' order by (0 + id_pegawai) asc";
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
                       
                    
                    });
               }
                tblpegawai.setModel(tabmode);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "data gagal dipanggil"+e);
            }
    
    }
    
    private void autonumber(){
         String sql = "SELECT * FROM pegawai ORDER BY id_pegawai DESC";
        try{          
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            if (r.next()) {
               String NoBarang = r.getString("id_pegawai").substring(0);
               String BR = "" + (Integer.parseInt(NoBarang) + 1);
               String No1 = "";
               
//               if (BR.length()==1){
//                   No1 = "00";
//               }else if (BR.length()==2) {
//                   No1 = "0";
//               }else if (BR.length()==3) {
//                   No1 = "";
//               }
               
               txid.setText(BR);
            }else {
                txid.setText("1");
            }
            r.close();
            s.close();
                    
        }catch(Exception e) {
            System.out.println("autonumber error"+e);
        }
    }

    
    private void kosong(){
        
        btntambah.setEnabled(true);
        btnhapus.setEnabled(false);
        btnedit1.setEnabled(false);
        
        
        txnama.setText("");
        txnip.setText("");
        txkota.setText("");
        txalamat.setText("");
        dtlahir.setText("");
        txgaji.setText("");
        buttonGroup1.clearSelection();
        gambar = "";
       
    
    }
    
    private void loadJabatan(){
        cbjabatan.removeAllItems();
        
        try{
            
            String sql = "SELECT id_jabatan, nama_jabatan, keterangan_jabatan FROM jabatan";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            
            while(rs.next()){
                arrJabatan.add(new jabatan( Integer.parseInt ( rs.getString("id_jabatan") ), rs.getString("nama_jabatan"), rs.getString("keterangan_jabatan") ));
            }
            
            for( int i = 0; i < arrJabatan.size(); i++){
                cbjabatan.addItem(arrJabatan.get( i ).getNama_jabatan());
            }
            
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    
    }
    
    private void loadBagian(){
        cbbagian.removeAllItems();
        
        try{
            
            String sql = "SELECT id_bagian, nama_bagian, keterangan_bagian FROM bagian";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            
            while(rs.next()){
                arrBagian.add(new Bagian( Integer.parseInt ( rs.getString("id_Bagian") ), rs.getString("nama_Bagian"), rs.getString("keterangan_bagian") ));
            }
            
            for( int i = 0; i < arrBagian.size(); i++){
                cbbagian.addItem(arrBagian.get( i ).getNama_bagian());
            }
            
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    
    private void loadLokasi(){
        cblokasi.removeAllItems();
        
        try{
            
            String sql = "SELECT id_lokasi, nama_lokasi, alamat,keterangan_lokasi FROM lokasi";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            
            while(rs.next()){
                arrLokasi.add(new lokasi( Integer.parseInt ( rs.getString("id_lokasi") ), rs.getString("nama_lokasi"), rs.getString("alamat"), rs.getString("keterangan_lokasi") ));
            }
            
            for( int i = 0; i < arrLokasi.size(); i++){
                cblokasi.addItem(arrLokasi.get( i ).getNama_lokasi());
            }
            
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    
    }
    
    private void loadShift(){
        cbshift.removeAllItems();
        
        try{
            
            String sql = "SELECT id_shift, nama_shift, waktu_masuk, waktu_keluar, keterangan_shift FROM shift";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            
            while(rs.next()){
                arrShift.add(new shift( Integer.parseInt ( rs.getString("id_shift") ), rs.getString("nama_shift"), rs.getString("waktu_masuk"), rs.getString("waktu_keluar"), rs.getString("keterangan_shift") ));
            }
            
            for( int i = 0; i < arrShift.size(); i++){
                cbshift.addItem(arrShift.get( i ).getNama_shift());
            }
            
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSimpan6 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txalamat = new javax.swing.JTextArea();
        txid = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txnama = new javax.swing.JTextField();
        btnfoto = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cbjabatan = new javax.swing.JComboBox<>();
        btnbatal = new javax.swing.JButton();
        btntambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpegawai = new javax.swing.JTable();
        txcari = new javax.swing.JTextField();
        btncari = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnhapus = new javax.swing.JButton();
        btnedit1 = new javax.swing.JButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jfoto = new javax.swing.JLabel();
        txnip = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txkota = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        rperempuan = new javax.swing.JRadioButton();
        rlaki = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        cbbagian = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cbshift = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cblokasi = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dtlahir = new com.github.lgooddatepicker.components.DatePicker();
        txkos = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txgaji = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel8.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setText("Data Pegawai");
        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        btnSimpan6.setText("X");
        btnSimpan6.setBackground(new java.awt.Color(255, 0, 0));
        btnSimpan6.setBorder(null);
        btnSimpan6.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(16, 16, 16))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnSimpan6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Alamat");
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txalamat.setColumns(20);
        txalamat.setRows(5);
        jScrollPane2.setViewportView(txalamat);

        txid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txidActionPerformed(evt);
            }
        });

        jLabel10.setText("ID Pegawai");
        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setText("Nama Pegawai");
        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txnamaActionPerformed(evt);
            }
        });

        btnfoto.setText("Ambil Foto");
        btnfoto.setBackground(new java.awt.Color(51, 51, 255));
        btnfoto.setForeground(new java.awt.Color(255, 255, 255));
        btnfoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfotoActionPerformed(evt);
            }
        });

        jLabel12.setText("Jabatan");
        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbjabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbjabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjabatanActionPerformed(evt);
            }
        });

        btnbatal.setText("Batal");
        btnbatal.setBackground(new java.awt.Color(255, 102, 0));
        btnbatal.setForeground(new java.awt.Color(255, 255, 255));
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        btntambah.setText("Tambah");
        btntambah.setBackground(new java.awt.Color(51, 0, 255));
        btntambah.setForeground(new java.awt.Color(255, 255, 255));
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        tblpegawai.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpegawaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpegawai);

        txcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txcariActionPerformed(evt);
            }
        });
        txcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txcariKeyTyped(evt);
            }
        });

        btncari.setText("Cari");
        btncari.setBackground(new java.awt.Color(51, 102, 255));
        btncari.setForeground(new java.awt.Color(255, 255, 255));
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        jLabel6.setText("Cari Data");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnhapus.setText("Hapus");
        btnhapus.setBackground(new java.awt.Color(255, 0, 0));
        btnhapus.setForeground(new java.awt.Color(255, 255, 255));
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnedit1.setText("Edit");
        btnedit1.setBackground(new java.awt.Color(51, 51, 255));
        btnedit1.setForeground(new java.awt.Color(255, 255, 255));
        btnedit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnedit1ActionPerformed(evt);
            }
        });

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(118, 115));

        jfoto.setBackground(new java.awt.Color(0, 0, 0));

        jDesktopPane1.setLayer(jfoto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jfoto, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jfoto, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );

        txnip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txnipActionPerformed(evt);
            }
        });
        txnip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txnipKeyTyped(evt);
            }
        });

        jLabel13.setText("NIP");
        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txkota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txkotaActionPerformed(evt);
            }
        });

        jLabel14.setText("Kota");
        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        buttonGroup1.add(rperempuan);
        rperempuan.setText("Perempuan");
        rperempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rperempuanActionPerformed(evt);
            }
        });

        buttonGroup1.add(rlaki);
        rlaki.setText("Laki-Laki");
        rlaki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rlakiActionPerformed(evt);
            }
        });

        jLabel15.setText("Jenis Kelamin");
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbbagian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbagian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbagianActionPerformed(evt);
            }
        });

        jLabel16.setText("Bagian");
        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbshift.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbshift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbshiftActionPerformed(evt);
            }
        });

        jLabel17.setText("Shift");
        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel18.setText("Lokasi");
        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cblokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cblokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cblokasiActionPerformed(evt);
            }
        });

        jLabel4.setText("Input Pegawai");
        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        jLabel7.setText("Tanggal Lahir");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txkos.setEnabled(false);

        jLabel19.setText("Gaji");
        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txgaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txgajiActionPerformed(evt);
            }
        });
        txgaji.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txgajiKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(btntambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(176, 176, 176))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txcari, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txkota, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(txid, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txnama, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txnip, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txgaji, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rlaki)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rperempuan))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cblokasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbshift, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbjabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dtlahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbagian, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(32, 32, 32)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnfoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txkos))
                .addGap(23, 23, 23))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(rlaki)
                            .addComponent(rperempuan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txnip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(dtlahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(cbjabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbagian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(cbshift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cblokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18)
                                    .addComponent(txkota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(txgaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncari))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btntambah, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txkos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txidActionPerformed

    private void txnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txnamaActionPerformed

    private void btnfotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfotoActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Pilih Foto Anda");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnval = chooser.showOpenDialog(this);
        if (returnval == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            BufferedImage bi;

            try {
                //menaru gambar di jlabel
                    bi = ImageIO.read(file);
                    Image dimg = bi.getScaledInstance(jfoto.getWidth(), jfoto.getHeight(), Image.SCALE_SMOOTH);
                    jfoto.setIcon(new ImageIcon(dimg));
                    gambar = file.getName();
                    files = Paths.get(file.toURI());
                    System.out.println(new File(System.getProperty("user.dir")));
               
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.pack();

        }
      
    }//GEN-LAST:event_btnfotoActionPerformed

    private void cbjabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjabatanActionPerformed
        // TODO add your handling code here:
        int idj = cbjabatan.getSelectedIndex();

        if(arrJabatan.size() > 0){
            id_jbt = arrJabatan.get(idj).getId_jabatan();
            nama_jbt = arrJabatan.get(idj).getNama_jabatan();
        }

    }//GEN-LAST:event_cbjabatanActionPerformed

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
        // TODO add your handling code here:
        kosong();
        autonumber();
        imgdefault();
        gambar = "";
    }//GEN-LAST:event_btnbatalActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:

        
        String jenis = null;
        int terlambat = 0;
        
        if(rlaki.isSelected()){
            jenis = "Laki - Laki";
        }else if (rperempuan.isSelected()){
            jenis = "Perempuan";
        }else {
            jenis = "Laki - Laki";
        }
        
        if ((txnip.getText().equals("")) || (txnama.getText().equals(""))
                || (txalamat.getText().equals("")) || (txkota.getText().equals("")) ||
                (dtlahir.getText().equals("")))
        {    
            JOptionPane.showMessageDialog(rootPane, "Lengkapi data terlebih dahulu");
        }else {
            
            String sqlA = "select * from pegawai where nip = '"+txnip.getText()+"'";
            try {
                Statement stata = conn.createStatement();
                ResultSet hasil = stata.executeQuery(sqlA);
                
                if(hasil.next()==true){
                    JOptionPane.showMessageDialog(null, "Data NIP sudah tersedia silahkan masukan data yang lain");
                }else {
                
                    String sql = "insert into pegawai values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try {
                    PreparedStatement stat = conn.prepareStatement(sql);
                    stat.setString(1, txid.getText());
                    stat.setString(2, txnip.getText());
                    stat.setString(3, txnama.getText());
                    stat.setString(4, txalamat.getText());
                    stat.setString(5, txkota.getText());
                    stat.setString(6, jenis);
                    stat.setString(7, dtlahir.getText());


                    if (!"".equals(gambar)){
                        try{
                            File dir = new File(System.getProperty("user.dir"));

                            //membuat folder jika tidak ada
                            File folder = new File(dir+"/src/images/");
                            if (!folder.exists()){
                                folder.mkdir();
                            }

                            copy = Paths.get(dir+"/src/images/"+gambar);
                            CopyOption[] options = new CopyOption[]{
                                StandardCopyOption.REPLACE_EXISTING,
                                StandardCopyOption.COPY_ATTRIBUTES
                            };
                            stat.setString(8, gambar);
                            Files.copy(files, copy,options);
                        }catch(IOException e){
                            JOptionPane.showMessageDialog(null, "Gagal Upload"+e);
                        }
                    }else if (gambar.equals("")){
                         stat.setString(8, kosong);
                         gambar = "";
                    }

                    stat.setInt(9, terlambat);
                    stat.setString(10, txgaji.getText());
                    stat.setString(11, txgaji.getText());
                    stat.setInt(12, id_jbt);
                    stat.setInt(13, id_bgn);
                    stat.setInt(14, id_shift);
                    stat.setInt(15, id_lks);

                    stat.executeUpdate();


                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                    kosong();
                    txnama.requestFocus();
                }catch (SQLException e){
                    JOptionPane.showMessageDialog(null, "Data Gagal Disimpan"+e);
                }
                
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan"+e);
            }
            
            
            
            finally {
            autonumber();
            showData();
            imgdefault();
            kosong();
            gambar = "";
        }
       }
        
        

    }//GEN-LAST:event_btntambahActionPerformed

    private void tblpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpegawaiMouseClicked

        int v = tblpegawai.getSelectedRow();
        if (v == -1){
            return;
        }
        
        btnhapus.setEnabled(true);
        btnedit1.setEnabled(true);
        btntambah.setEnabled(false);
        
        int baris = tblpegawai.getSelectedRow();

        String a = tabmode.getValueAt(baris, 0).toString();

        //ambil berdasrkan id
        String sql = "select * from pegawai where id_pegawai = '"+a+"'";

        try {
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
           
            while(hasil.next()){
                txid.setText(hasil.getString("id_pegawai"));
                txnip.setText(hasil.getString("nip"));
                txnama.setText(hasil.getString("nama"));
                txalamat.setText(hasil.getString("alamat"));
                txkota.setText(hasil.getString("kota"));
                dtlahir.setText(hasil.getString("tgl_lahir"));
                
                
                txkos.setText(hasil.getString("foto"));
                txgaji.setText(hasil.getString("gaji"));
                
                
                
                
                if ("Laki - Laki".equals(hasil.getString("jenis_kelamin"))){
                    rlaki.setSelected(true);
                }else{
                    rperempuan.setSelected(true);
                }
                
                
                id_bgn = hasil.getInt("id_bagian");
                for( int i = 0; i < arrBagian.size(); i++){
                    if(id_bgn == arrBagian.get(i).getId_bagian()){
                        cbbagian.setSelectedIndex(i);
                    }
                } 

                id_jbt = hasil.getInt("id_jabatan");
                for( int i = 0; i < arrJabatan.size(); i++){
                    if(id_jbt == arrJabatan.get(i).getId_jabatan()){
                        cbjabatan.setSelectedIndex(i);
                    }
                }
                
                id_lks = hasil.getInt("id_lokasi");
                for( int i = 0; i < arrLokasi.size(); i++){
                    if(id_lks == arrLokasi.get(i).getId_lokasi()){
                        cblokasi.setSelectedIndex(i);
                    }
                }
                
                id_shift = hasil.getInt("id_shift");
                for( int i = 0; i < arrShift.size(); i++){
                    if(id_shift == arrShift.get(i).getId_shift()){
                        cbshift.setSelectedIndex(i);
                    }
                }
                
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
            
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error"+ex);
        }
    }//GEN-LAST:event_tblpegawaiMouseClicked

    private void txcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txcariActionPerformed

    private void txcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcariKeyTyped
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            showData();
            autonumber();
        }
    }//GEN-LAST:event_txcariKeyTyped

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
        // TODO add your handling code here:

        showData();
        autonumber();
    }//GEN-LAST:event_btncariActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:

        int ok = JOptionPane.showConfirmDialog(null, "hapus","konfrimasi dialog",JOptionPane.YES_NO_OPTION);
        if (ok == 0){
            String sql = "delete from pegawai where id_pegawai='"+txid.getText()+"'";

            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "data berhasil dihapus");
                kosong();
                txid.requestFocus();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "data gagal dihapus"+e);
            }finally {
                showData();
                autonumber();
                kosong();
                imgdefault();
            }
        }
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnedit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnedit1ActionPerformed
        // TODO add your handling code here:
        
        String jenis = null;
        if(rlaki.isSelected()){
            jenis = "Laki - Laki";
        }else if (rperempuan.isSelected()){
            jenis = "Perempuan";
        }

        String sql = "update pegawai set nip=?,nama=?,alamat=?,kota=?,jenis_kelamin=?,tgl_lahir=?,foto=?,gaji=?,id_jabatan=?,id_bagian=?,id_shift=?,id_lokasi=? where id_pegawai = '"+txid.getText()+"'";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txnip.getText());
            stat.setString(2, txnama.getText()); 
            stat.setString(3, txalamat.getText());
            stat.setString(4, txkota.getText());
            stat.setString(5, jenis);
            stat.setString(6, dtlahir.getText());
            
            
//            if(txkos.getText() != "a29zb25n.png"){
//                stat.setString(7, gambar);
//            }else{
//                stat.setString(7, txkos.getText());
//            }


              if (!"".equals(gambar)){
                try{
                    File dir = new File(System.getProperty("user.dir"));

                    //membuat folder jika tidak ada
                    File folder = new File(dir+"/src/images/");
                    if (!folder.exists()){
                        folder.mkdir();
                    }

                    copy = Paths.get(dir+"/src/images/"+gambar);
                    CopyOption[] options = new CopyOption[]{
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES
                    };
                    stat.setString(7, gambar);
                    Files.copy(files, copy,options);
                }catch(IOException e){
                    JOptionPane.showMessageDialog(null, "Gagal Upload"+e);
                }
            }else if (gambar.equals("")){
                 stat.setString(7, txkos.getText());
                 gambar = "";
            }
            
            stat.setString(8, txgaji.getText());
            stat.setInt(9, id_jbt);
            stat.setInt(10, id_bgn);
            stat.setInt(11, id_shift);
            stat.setInt(12, id_lks);

            stat.executeUpdate();
            
            
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            kosong();
            txnama.requestFocus();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah"+e);
        }finally {
            showData();
            autonumber();
            kosong();
            imgdefault();
            gambar = "";
            
        }
    }//GEN-LAST:event_btnedit1ActionPerformed

    private void txnipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txnipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txnipActionPerformed

    private void txkotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txkotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txkotaActionPerformed

    private void rperempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rperempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rperempuanActionPerformed

    private void rlakiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rlakiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rlakiActionPerformed

    private void cbbagianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbagianActionPerformed
        int idb = cbbagian.getSelectedIndex();
        
        if(arrBagian.size() > 0){
            id_bgn = arrBagian.get(idb).getId_bagian();
            nama_bgn = arrBagian.get(idb).getNama_bagian();
        }
    }//GEN-LAST:event_cbbagianActionPerformed

    private void cbshiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbshiftActionPerformed
        int ids = cbshift.getSelectedIndex();
        
        if(arrShift.size() > 0){
            id_shift = arrShift.get(ids).getId_shift();
            nama_shift = arrShift.get(ids).getNama_shift();
        }
    }//GEN-LAST:event_cbshiftActionPerformed

    private void cblokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cblokasiActionPerformed
        int idl = cblokasi.getSelectedIndex();
        
        if(arrLokasi.size() > 0){
            id_lks = arrLokasi.get(idl).getId_lokasi();
            nama_lks = arrLokasi.get(idl).getNama_lokasi();
        }
    }//GEN-LAST:event_cblokasiActionPerformed

    private void btnSimpan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan6ActionPerformed
        // TODO add your handling code here:
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        this.dispose();
    }//GEN-LAST:event_btnSimpan6ActionPerformed

    private void txnipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txnipKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txnipKeyTyped

    private void txgajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txgajiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txgajiActionPerformed

    private void txgajiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txgajiKeyTyped
        // TODO add your handling code here:
         char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txgajiKeyTyped

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
            java.util.logging.Logger.getLogger(dataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan6;
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btncari;
    private javax.swing.JButton btnedit1;
    private javax.swing.JButton btnfoto;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btntambah;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbagian;
    private javax.swing.JComboBox<String> cbjabatan;
    private javax.swing.JComboBox<String> cblokasi;
    private javax.swing.JComboBox<String> cbshift;
    private com.github.lgooddatepicker.components.DatePicker dtlahir;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jfoto;
    private javax.swing.JRadioButton rlaki;
    private javax.swing.JRadioButton rperempuan;
    private javax.swing.JTable tblpegawai;
    private javax.swing.JTextArea txalamat;
    private javax.swing.JTextField txcari;
    private javax.swing.JTextField txgaji;
    private javax.swing.JTextField txid;
    private javax.swing.JTextField txkos;
    private javax.swing.JTextField txkota;
    private javax.swing.JTextField txnama;
    private javax.swing.JTextField txnip;
    // End of variables declaration//GEN-END:variables
}
