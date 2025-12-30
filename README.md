Hastane Otomasyon Sistemi

<img width="453" height="379" alt="Screenshot 2025-12-30 at 23 40 47" src="https://github.com/user-attachments/assets/67daa526-040c-461b-a915-9e2a9bde9661" />
<img width="912" height="740" alt="Screenshot 2025-12-30 at 23 40 35" src="https://github.com/user-attachments/assets/dd56f430-8683-430e-9e7f-2f0d07642a16" />
<img width="512" height="440" alt="Screenshot 2025-12-30 at 23 40 24" src="https://github.com/user-attachments/assets/e959bfcb-1d2f-48f2-b8af-21631b64c49d" />
<img width="712" height="540" alt="Screenshot 2025-12-30 at 23 40 06" src="https://github.com/user-attachments/assets/3c370aac-c5cc-45a2-bf9c-cb2d852018ea" />
<img width="512" height="440" alt="Screenshot 2025-12-30 at 23 39 25" src="https://github.com/user-attachments/assets/8240ff43-3f40-4be6-b90b-dac287cb998a" />


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
