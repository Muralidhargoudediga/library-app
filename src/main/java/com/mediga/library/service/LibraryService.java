package com.mediga.library.service;

import com.mediga.library.entity.Book;
import com.mediga.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;

    public enum SearchCategory {
        AUTHOR, BOOK_NAME, ISBN, PUBLISHER, CATEGORY;
    }

    public List<Book> searchBooks(String searchType, String searchString) {
        if(StringUtils.isEmpty(searchType)) {
            throw new IllegalArgumentException("Invalid searchType : " + searchType);
        }

        SearchCategory searchCategory = SearchCategory.valueOf(searchType);

        switch (searchCategory) {
            case AUTHOR:
                return bookRepository.findByAuthorName(searchString);
            case BOOK_NAME:
                return bookRepository.findByName(searchString);
            case ISBN:
                return bookRepository.findByIsbn(searchString);
            case PUBLISHER:
                return bookRepository.findByPublisherName(searchString);
            case CATEGORY:
                return bookRepository.findByCategory(searchString);
            default:
                throw new IllegalArgumentException("Invalid searchType : " + searchType);
        }
    }
}
