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