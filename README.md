Hastane Otomasyon Sistemi

Java 17, JavaFX ve SQLite kullanılarak geliştirilmiş, MVC mimarisine sahip masaüstü hastane yönetim uygulamasıdır. Danışma, Doktor ve Hasta olmak üzere üç farklı kullanıcı rolünü destekler.

Kullanılan Teknolojiler

•
Java 17 & JavaFX (Arayüz)

•
SQLite (Veritabanı)

•
Maven (Bağımlılık Yönetimi)

Modüller ve Özellikler

1. Giriş Sistemi

•
Üç farklı rol (Danışma, Doktor, Hasta) için sekmeli giriş ekranı.

•
TC Kimlik No ve parola ile kimlik doğrulama.

2. Danışma Paneli

•
Yeni hasta kaydı oluşturma (Ad, Soyad, TC, Şikayet).

•
Kayıt işlemiyle birlikte hastaya otomatik giriş yetkisi tanımlanması (Varsayılan şifre: TC No).

3. Doktor Paneli

•
Kayıtlı tüm hastaların listelenmesi.

•
Tablo üzerinde sağ tıklayarak hasta kaydının ve kullanıcı hesabının silinmesi.

4. Hasta Paneli

•
Giriş yapan hastanın kendi şikayet ve kayıt bilgilerini görüntülemesi.

Teknik Detaylar

•
DatabaseManager: Singleton deseni ile merkezi veritabanı yönetimi.

•
Otomatik Kurulum: Uygulama açılışında tabloların oluşturulması ve örnek verilerin yüklenmesi.

•
SceneChanger: Pencereler arası geçiş yönetimi.
