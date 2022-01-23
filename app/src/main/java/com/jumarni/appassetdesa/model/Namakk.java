package com.jumarni.appassetdesa.model;

public class Namakk {
    private int id_kk, dusun_id;
    private String nama_kk, nik, no_kk, created_at;

    public int getDusun_id() {
        return dusun_id;
    }

    public void setDusun_id(int dusun_id) {
        this.dusun_id = dusun_id;
    }

    public int getId_kk() {
        return id_kk;
    }

    public void setId_kk(int id_kk) {
        this.id_kk = id_kk;
    }

    public String getNama_kk() {
        return nama_kk;
    }

    public void setNama_kk(String nama_kk) {
        this.nama_kk = nama_kk;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
