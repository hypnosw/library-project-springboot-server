package com.luv2code.springbootlibrary.service;


import com.luv2code.springbootlibrary.dao.BookRepository;
import com.luv2code.springbootlibrary.dao.CheckoutRepository;
import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.entity.Checkout;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;

    //repository injection to set up the service
    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository){
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    // returns a book
    public Book checkoutBook(String userEmail, Long bookId) throws Exception{

        // optional book to give to the database based on bookid
        Optional<Book> book = bookRepository.findById(bookId);

        // trying to validate the book != null
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0){
            throw new Exception("Book doesn't exist or already checkecd out by user");
        }

        // changing the number available
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);

        // update database
        bookRepository.save(book.get());

        // create new checkout record
        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );

        // save to database
        checkoutRepository.save(checkout);

        // return the book
        return book.get();
    }

}
