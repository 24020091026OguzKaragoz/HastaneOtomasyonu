package org.t3tracon.hastaneotomasyonu2.model;

public abstract class Kullanici {
    private int id;
    private String ad;
    private String soyad;
    private String tc;
    private String parola;
    private KullaniciTipi tip;

    public Kullanici(int id, String ad, String soyad, String tc, String parola, KullaniciTipi tip) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.tc = tc;
        this.parola = parola;
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public KullaniciTipi getTip() {
        return tip;
    }

    public void setTip(KullaniciTipi tip) {
        this.tip = tip;
    }
}
