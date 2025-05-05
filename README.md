# Diyetisyen Takip Uygulaması

Bu uygulama, diyetisyenlerin hastalarını takip etmelerini, randevuları yönetmelerini ve hastaların kilo, vücut ölçümleri ve beslenme kayıtlarını izlemelerini sağlayan bir web uygulamasıdır.

## Uygulama prod ortamı linki:
https://dietitian-tracking-app-fafa781eaa16.herokuapp.com/index

## Kullanılan Teknolojiler

### Backend
- **Java 17**: Modern Java özellikleri ve gelişmiş performans için tercih edilmiştir. Java, kurumsal uygulamalar için güvenilir ve yaygın olarak kullanılan bir programlama dilidir.
- **Spring Boot (v3.4.0)**: Hızlı uygulama geliştirme, otomatik yapılandırma ve bağımlılık yönetimi sağlar. Spring Boot, Java tabanlı uygulamaların hızlı bir şekilde geliştirilmesini ve dağıtılmasını kolaylaştırır.
- **Spring Data JPA**: Veritabanı işlemlerini kolaylaştıran, repository pattern'i uygulayan bir Spring modülüdür. Veritabanı işlemlerini daha az kod yazarak gerçekleştirmeyi sağlar.
- **Lombok**: Tekrarlayan kod (getter, setter, constructor vb.) yazımını azaltarak geliştirme sürecini hızlandırır. Kod okunabilirliğini artırır ve bakımı kolaylaştırır.

### Frontend
- **JSF (JavaServer Faces)**: Java EE'nin standart web arayüzü teknolojisidir. Bileşen tabanlı bir UI geliştirme yaklaşımı sunar.
- **JoinFaces (v5.4.0)**: JSF ve Spring Boot entegrasyonunu sağlayan bir kütüphanedir. JSF'nin Spring Boot ile sorunsuz çalışmasını sağlar.
- **PrimeFaces (v14.0.6)**: JSF için zengin UI bileşenleri sunan bir kütüphanedir. Kullanıcı dostu ve modern arayüzler oluşturmayı kolaylaştırır.
- **PrimeFlex (v3.3.1)**: Responsive tasarım için CSS utility kütüphanesidir. Farklı ekran boyutlarına uyumlu arayüzler geliştirmeyi sağlar.
- **OmniFaces (v4.5.1)**: JSF için ek utility fonksiyonları sağlayan bir kütüphanedir. JSF'nin eksik kalan yönlerini tamamlar ve geliştirme sürecini kolaylaştırır.

### Veritabanı
- **H2 Database**: Geliştirme ve test aşamasında kullanılan hafif, in-memory veritabanıdır. Hızlı başlatma ve test etme imkanı sağlar.
- **postgresql**: Üretim ortamında kullanılabilecek güçlü ve yaygın bir ilişkisel veritabanı yönetim sistemidir.

## Proje Yapısı

Proje, standart bir Spring Boot uygulaması yapısını takip etmektedir:

- **controller**: Web isteklerini karşılayan ve işleyen sınıflar
- **converter**: Veri dönüşümlerini gerçekleştiren sınıflar
- **entity**: Veritabanı tablolarını temsil eden JPA entity sınıfları
- **filter**: HTTP isteklerini filtreleme işlemleri
- **repository**: Veritabanı işlemlerini gerçekleştiren Spring Data JPA repository'leri
- **service**: İş mantığını içeren servis sınıfları
- **resources**: Yapılandırma dosyaları ve web kaynakları

## Temel Özellikler

- Diyetisyen ve hasta kullanıcı yönetimi
- Hasta profil bilgilerinin yönetimi
- Kilo ve vücut ölçümlerinin takibi
- Beslenme kayıtlarının tutulması
- Randevu yönetimi

## Nasıl Çalıştırılır

1. Projeyi klonlayın:
   ```
   git clone https://github.com/EnesSimsekEDU/Dietitian-Tracking-App.git
   ```
   
2. Proje dizinine gidin:
   ```
   application.properties dosyasında h2 db bilgilerini aktif edin. postgresql db bilgilerini pasife çekin. Pom xml dosyasında h2 bağımlılığını aktif edin.
   ```   

3. Proje dizinine gidin:
   ```
   cd Dietitian-Tracking-App
   ```

4. Maven ile projeyi derleyin:
   ```
   ./mvnw clean install
   ```

5. Uygulamayı çalıştırın:
   ```
   ./mvnw spring-boot:run
   ```

6. Tarayıcınızda aşağıdaki URL'i açın:
   ```
   http://localhost:8080
   ```

## Referanslar

- Spring Boot: https://spring.io/projects/spring-boot
- JoinFaces: https://github.com/joinfaces/joinfaces
- PrimeFaces: https://www.primefaces.org/
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Lombok: https://projectlombok.org/
- OmniFaces: https://omnifaces.org/