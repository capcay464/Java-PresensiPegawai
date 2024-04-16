/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataPegawai;

/**
 *
 * @author Gasom
 */
public class shift {
    
    int id_shift;
    String nama_shift,waktu_masuk,waktu_keluar,keterangan_shift;

    public shift(int id_shift, String nama_shift, String waktu_masuk, String waktu_keluar, String keterangan_shift) {
        this.id_shift = id_shift;
        this.nama_shift = nama_shift;
        this.waktu_masuk = waktu_masuk;
        this.waktu_keluar = waktu_keluar;
        this.keterangan_shift = keterangan_shift;
    }

    public int getId_shift() {
        return id_shift;
    }

    public String getNama_shift() {
        return nama_shift;
    }

    public String getWaktu_masuk() {
        return waktu_masuk;
    }

    public String getWaktu_keluar() {
        return waktu_keluar;
    }

    public String getKeterangan_shift() {
        return keterangan_shift;
    }
    
}
