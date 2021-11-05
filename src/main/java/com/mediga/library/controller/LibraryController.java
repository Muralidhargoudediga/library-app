package com.mediga.library.controller;

import com.mediga.library.entity.Book;
import com.mediga.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LibraryController {

    private LibraryService libraryService;

    @GetMapping("/search/{searchType}/{searchString}")
    public List<Book> searchBooks(@PathVariable String searchType, @PathVariable String searchString ) {
        try{
            return libraryService.searchBooks(searchType, searchString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/loan_book/{userId}/{bookId}")
    public ResponseEntity loanBook(@PathVariable long userId, @PathVariable long bookId) {
        try{
            libraryService.loanBook(userId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("return_book/{userId}/{bookId}")
    public ResponseEntity returnBook(@PathVariable long userId, @PathVariable long bookId) {
        try{
            libraryService.returnBook(userId, bookId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Autowired
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

}
