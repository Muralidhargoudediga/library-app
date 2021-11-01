package com.mediga.library.repository;

import com.mediga.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.authors a where a.name = :authorName")
    List<Book> findByAuthorName(@Param("authorName") String authorName);

    List<Book> findByIsbn(String isbn);

    List<Book> findByName(String name);

    @Query("select b from Book b join fetch b.publisher p where p.name = :publisherName")
    List<Book> findByPublisherName(@Param("publisherName") String publisherName);

    @Query("select b from Book b join fetch b.categories c where c.name = :category")
    List<Book> findByCategory(@Param("category") String category);

}
