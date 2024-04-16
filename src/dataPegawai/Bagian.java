/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataPegawai;

/**
 *
 * @author Gasom
 */
public class Bagian {
    int id_bagian;
    String nama_bagian,keterangan_bagian;

    public Bagian(int id_bagian, String nama_bagian, String keterangan_bagian) {
        this.id_bagian = id_bagian;
        this.nama_bagian = nama_bagian;
        this.keterangan_bagian = keterangan_bagian;
    }

    public int getId_bagian() {
        return id_bagian;
    }

    public String getNama_bagian() {
        return nama_bagian;
    }

    public String getKeterangan_bagian() {
        return keterangan_bagian;
    }
    
    
}
