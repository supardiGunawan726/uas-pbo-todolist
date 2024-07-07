# TODO LIST

Projek UAS Mata kuliah Pemrograman Berbasis Objek. Anggota Kelompok:

| Nama                              | Kelas | NIM         |
| --------------------------------- | ----- | ----------- |
| SUPARDI G                         | TI22H | 20220040084 |
| AMANDA NURSAFITRI                 | TI22H | 20220040093 |
| FRANSISKUS OCTAVIANUS MADO HURINT | TI22H | 20220040252 |

## Mengenai projek

1. Projek ini GUI Based dengan Java Swing.
2. Dependency seperti `mysql connector` diinstall menggunakan Maven. [Install Maven](https://maven.apache.org/install.html)
3. Perintah install dependency menggunakan maven `mvn install`.
4. Konfigurasikan database pada file [DBConnection](./src/main/java/com/todolist/DBConnection.java).

## Mengenai Database

1. Nama database adalah `todolist`.
2. Username database adalah `root` dan password nya adalah `12345678`.
3. Port database adalah `3306`.
4. Terdapat tiga tabel yang dibuat:

```sql
-- Membuat tabel Team
CREATE TABLE Team (
    team_id INT AUTO_INCREMENT PRIMARY KEY,
    nama_team VARCHAR(255) NOT NULL,
    deskripsi VARCHAR(255)
);

-- Membuat tabel User
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    team_id INT,
    FOREIGN KEY (team_id) REFERENCES Team(team_id)
);

-- Membuat tabel Task
CREATE TABLE Task (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    judul VARCHAR(255) NOT NULL,
    deskripsi VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    prioritas INT NOT NULL,
    team_id INT,
    user_id INT,
    tanggal_tugas DATE,
    FOREIGN KEY (team_id) REFERENCES Team(team_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
```

3. Terdapat satu function yang dibuat:

```sql
DELIMITER //

CREATE FUNCTION login(email VARCHAR(255), password VARCHAR(255)) 
RETURNS INT 
DETERMINISTIC
BEGIN
    DECLARE userId INT;
    
    SELECT user_id INTO userId
    FROM User
    WHERE email = email AND password = password
    LIMIT 1;
    
    RETURN userId;
END //

DELIMITER ;
```

## Sample Data

Jalankan kode SQL berikut untuk menambahkan sample data:

```sql
-- Menambahkan sample data ke tabel Team
INSERT INTO Team (nama_team, deskripsi) VALUES
('Development', 'Tim yang bertanggung jawab atas pengembangan produk.'),
('Marketing', 'Tim yang bertanggung jawab atas pemasaran dan promosi.'),
('Support', 'Tim yang bertanggung jawab atas dukungan pelanggan.');

-- Menambahkan sample data ke tabel User
INSERT INTO User (nama, email, password, team_id) VALUES
('Alice', 'alice@example.com', 'password123', 1),  -- Development
('Bob', 'bob@example.com', 'password123', 1),      -- Development
('Charlie', 'charlie@example.com', 'password123', 2),  -- Marketing
('David', 'david@example.com', 'password123', 3),  -- Support
('Eve', 'eve@example.com', 'password123', 3);      -- Support

-- Menambahkan sample data ke tabel Task
INSERT INTO Task (judul, deskripsi, status, prioritas, team_id, user_id, tanggal_tugas) VALUES
('Develop new feature', 'Develop a new feature for the product.', 'pending', 1, 1, 1, '2024-06-25'),  -- Development, Alice
('Fix bugs', 'Fix bugs reported by users.', 'in progress', 2, 1, 2, '2024-06-26'),  -- Development, Bob
('Launch marketing campaign', 'Launch a new marketing campaign.', 'pending', 1, 2, 3, '2024-06-27'),  -- Marketing, Charlie
('Customer support', 'Provide support to customers.', 'completed', 3, 3, 4, '2024-06-24'),  -- Support, David
('Update documentation', 'Update the product documentation.', 'pending', 2, 3, 5, '2024-06-25');  -- Support, Eve
```