package com.mediga.library.repository;

import com.mediga.library.entity.LoanedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanedBookRepository extends JpaRepository<LoanedBook, Long> {
}
