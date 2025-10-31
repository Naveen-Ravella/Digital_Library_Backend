package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.BookTransaction;
import com.library.service.BookTransactionService;

@RestController
@RequestMapping("/transaction")
public class BookTransactionController {
	
	@Autowired
    private BookTransactionService transactionService;
    
    @PostMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<BookTransaction> borrowBook(@PathVariable int userId, @PathVariable int bookId) {
        try {
            BookTransaction transaction = transactionService.borrowBook(userId, bookId);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/return/{transactionId}")
    public ResponseEntity<BookTransaction> returnBook(@PathVariable int transactionId) {
        try {
            BookTransaction transaction = transactionService.returnBook(transactionId);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/getall")
    public List<BookTransaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
    
    @GetMapping("/user/{userId}")
    public List<BookTransaction> getUserTransactions(@PathVariable int userId) {
        return transactionService.getUserTransactions(userId);
    }
    
    @GetMapping("/user/{userId}/current")
    public List<BookTransaction> getUserCurrentBorrowedBooks(@PathVariable int userId) {
        return transactionService.getUserCurrentBorrowedBooks(userId);
    }
    
    @GetMapping("/book/{bookId}")
    public List<BookTransaction> getBookTransactions(@PathVariable int bookId) {
        return transactionService.getBookTransactions(bookId);
    }
    
    @GetMapping("/current-borrowed")
    public List<BookTransaction> getCurrentBorrowedBooks() {
        return transactionService.getCurrentBorrowedBooks();
    }
    
    @GetMapping("/overdue")
    public List<BookTransaction> getOverdueBooks() {
        return transactionService.getOverdueBooks();
    }
    
}
