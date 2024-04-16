/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataPegawai;

/**
 *
 * @author Gasom
 */
public class lokasi {
    
    int id_lokasi;
    String nama_lokasi,alamat,keterangan_lokasi;

    public lokasi(int id_lokasi, String nama_lokasi, String alamat, String keterangan_lokasi) {
        this.id_lokasi = id_lokasi;
        this.nama_lokasi = nama_lokasi;
        this.alamat = alamat;
        this.keterangan_lokasi = keterangan_lokasi;
    }

    public int getId_lokasi() {
        return id_lokasi;
    }

    public String getNama_lokasi() {
        return nama_lokasi;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKeterangan_lokasi() {
        return keterangan_lokasi;
    }
    
}
