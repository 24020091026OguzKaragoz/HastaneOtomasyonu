package org.t3tracon.hastaneotomasyonu2.model;

public class Danisma extends Kullanici {
    public Danisma(int id, String ad, String soyad, String tc, String parola) {
        super(id, ad, soyad, tc, parola, KullaniciTipi.DANISMA);
    }
}
