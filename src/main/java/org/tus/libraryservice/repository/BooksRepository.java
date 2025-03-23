package org.tus.libraryservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tus.libraryservice.entity.Books;


import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books,Long> {

    Optional<Books> findBookByBookId(long id);

    Optional<Books> findBookByBookName(String title);

    Page<Books> findAll(Pageable pageable);
}
