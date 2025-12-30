package org.t3tracon.hastaneotomasyonu2.model;

public class Hasta extends Kullanici {
    private String sikayet;
    private String kayitTarihi;

    public Hasta(int id, String ad, String soyad, String tc, String parola) {
        super(id, ad, soyad, tc, parola, KullaniciTipi.HASTA);
    }

    public Hasta(int id, String ad, String soyad, String tc, String sikayet, String kayitTarihi) {
        super(id, ad, soyad, tc, null, KullaniciTipi.HASTA);
        this.sikayet = sikayet;
        this.kayitTarihi = kayitTarihi;
    }

    public String getSikayet() {
        return sikayet;
    }

    public void setSikayet(String sikayet) {
        this.sikayet = sikayet;
    }

    public String getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(String kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }
}
