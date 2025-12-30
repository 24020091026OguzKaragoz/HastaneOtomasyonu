package org.t3tracon.hastaneotomasyonu2.model;

public class Doktor extends Kullanici {
    public Doktor(int id, String ad, String soyad, String tc, String parola) {
        super(id, ad, soyad, tc, parola, KullaniciTipi.DOKTOR);
    }
}
