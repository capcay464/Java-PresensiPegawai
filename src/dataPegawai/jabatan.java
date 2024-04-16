/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataPegawai;

/**
 *
 * @author Gasom
 */
public class jabatan {
    int id_jabatan;
    String nama_jabatan,keterangan_jabatan;

    public jabatan(int id_jabatan, String nama_jabatan, String keterangan_jabatan) {
        this.id_jabatan = id_jabatan;
        this.nama_jabatan = nama_jabatan;
        this.keterangan_jabatan = keterangan_jabatan;
    }


    public int getId_jabatan() {
        return id_jabatan;
    }

    public String getNama_jabatan() {
        return nama_jabatan;
    }

    public String getKeterangan_jabatan() {
        return keterangan_jabatan;
    }
    
}
