CREATE TABLE IF NOT EXISTS library_user (
    library_user_id int AUTO_INCREMENT PRIMARY KEY,
    username varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    mobile_number varchar(100) NOT NULL,
    created_at date NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS books (
    book_id int AUTO_INCREMENT PRIMARY KEY,
    book_name varchar(100) NOT NULL,
    author varchar(100) DEFAULT NULL,
    page_count varchar(100) NOT NULL,
    created_at date NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS checked_books (
    checked_book_id int AUTO_INCREMENT PRIMARY KEY,
    library_user_id int,
    foreign key (library_user_id) references library_user(library_user_id),
    book_name varchar(100) DEFAULT NULL,
    checked_status varchar(100) DEFAULT NULL,
    created_at date NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL
    );

insert INTO library_user(username, email, mobile_number, created_at, created_by)
VALUES
    ('admin_user', 'admin@gmail.com', '0987654321', '2025-02-13', 'admin'),
    ('admin_user2', 'admin2@gmail.com', '0987654312', '2025-02-13', 'admin');

insert INTO checked_books(library_user_id, book_name, checked_status, created_at, created_by)
VALUES
    ('1','Frankenstein','ON_TIME','2025-02-13', 'admin');


INSERT INTO books (book_name, author, page_count, created_at, created_by)
VALUES
    ('To Kill a Mockingbird', 'Harper Lee', '281', '2025-02-15', 'admin'),
    ('1984', 'George Orwell', '328', '2025-02-15', 'admin'),
    ('Pride and Prejudice', 'Jane Austen', '279', '2025-02-15', 'admin'),
    ('The Great Gatsby', 'F. Scott Fitzgerald', '180', '2025-02-15', 'admin'),
    ('Moby Dick', 'Herman Melville', '635', '2025-02-15', 'admin'),
    ('War and Peace', 'Leo Tolstoy', '1225', '2025-02-15', 'admin'),
    ('Ulysses', 'James Joyce', '730', '2025-02-15', 'admin'),
    ('The Catcher in the Rye', 'J.D. Salinger', '277', '2025-02-15', 'admin'),
    ('The Lord of the Rings', 'J.R.R. Tolkien', '1178', '2025-02-15', 'admin'),
    ('The Hobbit', 'J.R.R. Tolkien', '310', '2025-02-15', 'admin'),
    ('The Odyssey', 'Homer', '475', '2025-02-15', 'admin'),
    ('The Brothers Karamazov', 'Fyodor Dostoevsky', '796', '2025-02-15', 'admin'),
    ('Crime and Punishment', 'Fyodor Dostoevsky', '671', '2025-02-15', 'admin'),
    ('Brave New World', 'Aldous Huxley', '311', '2025-02-15', 'admin'),
    ('The Picture of Dorian Gray', 'Oscar Wilde', '254', '2025-02-15', 'admin'),
    ('Dracula', 'Bram Stoker', '418', '2025-02-15', 'admin'),
    ('The Divine Comedy', 'Dante Alighieri', '798', '2025-02-15', 'admin'),
    ('Frankenstein', 'Mary Shelley', '280', '2025-02-15', 'admin'),
    ('Don Quixote', 'Miguel de Cervantes', '1023', '2025-02-15', 'admin'),
    ('The Alchemist', 'Paulo Coelho', '208', '2025-02-15', 'admin');
