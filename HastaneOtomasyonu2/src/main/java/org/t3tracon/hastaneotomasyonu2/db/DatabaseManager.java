package org.t3tracon.hastaneotomasyonu2.db;

import org.t3tracon.hastaneotomasyonu2.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static final String DB_URL = "jdbc:sqlite:hastane.db";
    private Connection connection;

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
            seedData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void createTables() throws SQLException {
        String createKullanicilar = "CREATE TABLE IF NOT EXISTS kullanicilar (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ad TEXT NOT NULL," +
                "soyad TEXT NOT NULL," +
                "tc TEXT UNIQUE NOT NULL," +
                "parola TEXT NOT NULL," +
                "tip TEXT NOT NULL" +
                ");";

        String createHastalar = "CREATE TABLE IF NOT EXISTS hastalar (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ad TEXT NOT NULL," +
                "soyad TEXT NOT NULL," +
                "tc TEXT NOT NULL," +
                "sikayet TEXT," +
                "kayit_tarihi TEXT" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createKullanicilar);
            stmt.execute(createHastalar);
        }
    }

    private void seedData() throws SQLException {
        // Check if data exists
        String checkSql = "SELECT count(*) FROM kullanicilar";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                // Insert sample data
                insertKullanici("Danisma", "Personel", "123", "123", KullaniciTipi.DANISMA);
                insertKullanici("Doktor", "Uzman", "456", "456", KullaniciTipi.DOKTOR);
                insertKullanici("Hasta", "Vatandas", "789", "789", KullaniciTipi.HASTA);
                
                // Insert sample patient record
                insertHasta("Hasta", "Vatandas", "789", "Bas agrisi");
            }
        }
    }

    public void insertKullanici(String ad, String soyad, String tc, String parola, KullaniciTipi tip) throws SQLException {
        String sql = "INSERT INTO kullanicilar(ad, soyad, tc, parola, tip) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ad);
            pstmt.setString(2, soyad);
            pstmt.setString(3, tc);
            pstmt.setString(4, parola);
            pstmt.setString(5, tip.name());
            pstmt.executeUpdate();
        }
    }

    public void insertHasta(String ad, String soyad, String tc, String sikayet) throws SQLException {
        // 1. Tıbbi kayıt oluştur (Hastalar tablosu)
        String sql = "INSERT INTO hastalar(ad, soyad, tc, sikayet, kayit_tarihi) VALUES(?, ?, ?, ?, datetime('now'))";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ad);
            pstmt.setString(2, soyad);
            pstmt.setString(3, tc);
            pstmt.setString(4, sikayet);
            pstmt.executeUpdate();
        }

        // 2. Giriş kullanıcısı oluştur (Kullanicilar tablosu)
        // Parola varsayılan olarak TC numarası atanır.
        // INSERT OR IGNORE: Eğer kullanıcı zaten varsa (TC çakışması) hata vermez, sadece atlar.
        String userSql = "INSERT OR IGNORE INTO kullanicilar(ad, soyad, tc, parola, tip) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(userSql)) {
            pstmt.setString(1, ad);
            pstmt.setString(2, soyad);
            pstmt.setString(3, tc);
            pstmt.setString(4, tc); // Parola = TC
            pstmt.setString(5, KullaniciTipi.HASTA.name());
            pstmt.executeUpdate();
        }
    }

    public void deleteHasta(String tc) throws SQLException {
        // 1. Tıbbi kaydı sil (Hastalar tablosu)
        String sqlHasta = "DELETE FROM hastalar WHERE tc = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlHasta)) {
            pstmt.setString(1, tc);
            pstmt.executeUpdate();
        }

        // 2. Kullanıcı kaydını sil (Kullanicilar tablosu)
        // Sadece HASTA tipindeki kullanıcıyı siler, aynı TC ile başka rol varsa (örn. doktor) dokunmaz.
        String sqlKullanici = "DELETE FROM kullanicilar WHERE tc = ? AND tip = 'HASTA'";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlKullanici)) {
            pstmt.setString(1, tc);
            pstmt.executeUpdate();
        }
    }

    public Kullanici login(String tc, String parola, KullaniciTipi tip) {
        String sql = "SELECT * FROM kullanicilar WHERE tc = ? AND parola = ? AND tip = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tc);
            pstmt.setString(2, parola);
            pstmt.setString(3, tip.name());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String ad = rs.getString("ad");
                    String soyad = rs.getString("soyad");
                    
                    switch (tip) {
                        case DANISMA:
                            return new Danisma(id, ad, soyad, tc, parola);
                        case DOKTOR:
                            return new Doktor(id, ad, soyad, tc, parola);
                        case HASTA:
                            return new Hasta(id, ad, soyad, tc, parola);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Hasta> getAllHastalar() {
        List<Hasta> hastalar = new ArrayList<>();
        String sql = "SELECT * FROM hastalar";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                hastalar.add(new Hasta(
                        rs.getInt("id"),
                        rs.getString("ad"),
                        rs.getString("soyad"),
                        rs.getString("tc"),
                        rs.getString("sikayet"),
                        rs.getString("kayit_tarihi")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastalar;
    }

    public Hasta getHastaByTc(String tc) {
        String sql = "SELECT * FROM hastalar WHERE tc = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tc);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Hasta(
                            rs.getInt("id"),
                            rs.getString("ad"),
                            rs.getString("soyad"),
                            rs.getString("tc"),
                            rs.getString("sikayet"),
                            rs.getString("kayit_tarihi")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
