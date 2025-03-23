package org.tus.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tus.libraryservice.entity.CheckedBooks;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckedBooksRepository extends JpaRepository<CheckedBooks, Long> {

    List<CheckedBooks> findByLibraryUserId(int libraryUserId);

    CheckedBooks findByCheckedBookId(int checkedBookId);
}
