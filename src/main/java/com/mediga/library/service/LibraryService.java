package com.mediga.library.service;

import com.mediga.library.entity.Book;
import com.mediga.library.entity.LoanStatus;
import com.mediga.library.entity.LoanedBook;
import com.mediga.library.exception.BookNotAvailableException;
import com.mediga.library.exception.BookNotFoundException;
import com.mediga.library.exception.LoanNotFoundException;
import com.mediga.library.repository.BookRepository;
import com.mediga.library.repository.LoanedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private BookRepository bookRepository;
    private LoanedBookRepository loanedBookRepository;
    private NotificationService notificationService;
    private SubscriptionService subscriptionService;

    public enum SearchCategory {
        AUTHOR, BOOK_NAME, ISBN, PUBLISHER, CATEGORY;
    }

    public List<Book> searchBooks(String searchType, String searchString) {
        if(StringUtils.isEmpty(searchType)) {
            throw new IllegalArgumentException("Invalid searchType : " + searchType);
        }

        SearchCategory searchCategory = SearchCategory.valueOf(searchType.toUpperCase());

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

    public LoanedBook loanBook(long userId, long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book with id : " + bookId + " is not found");
        }
        Book book = optionalBook.get();
        if(!Boolean.valueOf(book.getAvailable())) {
            subscriptionService.subscribe(userId, bookId);
            throw new BookNotAvailableException("Book with id : " + bookId + " is not available");
        }
        book.setAvailable(false);
        bookRepository.save(book);
        LoanedBook loanedBook = new LoanedBook();
        loanedBook.setBookId(bookId);
        loanedBook.setUserId(userId);
        loanedBook.setStatus(LoanStatus.PENDING);
        loanedBook.setDueDate(LocalDateTime.now().plusDays(7));
        return loanedBookRepository.save(loanedBook);
    }

    public void returnBook(long userId, long bookId) {
        LoanedBook loanedBook = loanedBookRepository.findByUserIdAndBookId(userId, bookId);
        if(loanedBook == null) {
            throw new LoanNotFoundException("Loan with book id :" + bookId + " is not found for user id :" + userId);
        }

        loanedBook.getBook().setAvailable(true);
        loanedBook.setStatus(LoanStatus.COMPLETED);
        LocalDateTime returnedDate = LocalDateTime.now();
        loanedBook.setReturnedDate(returnedDate);
        LocalDateTime returnDate = loanedBook.getReturnedDate();
        long days = ChronoUnit.DAYS.between(returnDate, returnedDate);
        if(days > 0) {
            loanedBook.setLateFee(days*5);
        }
        loanedBookRepository.save(loanedBook);
        notificationService.notifySubscribedUsers(bookId);
    }
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setLoanedBookRepository(LoanedBookRepository loanedBookRepository) {
        this.loanedBookRepository = loanedBookRepository;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
}
