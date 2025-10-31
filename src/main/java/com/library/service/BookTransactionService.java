package com.library.service;

import java.util.List;
import java.util.Optional;

import java.lang.RuntimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.BookTransaction;
import com.library.entity.BookTransaction.TransactionStatus;
import com.library.entity.Books;
import com.library.entity.Users;
import com.library.repository.BookRepository;
import com.library.repository.BookTransactionRepository;
import com.library.repository.UserRepository;

@Service
public class BookTransactionService {
	@Autowired
	private BookTransactionRepository bookTransactionRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private UserRepository userRepo;
	
	private static final int Default_Borrow_Days=30;
	private static final double Fine_Per_day=10;
	private static final int Maximum_Books_User=5;
	
	//Borrow a Book
	public BookTransaction borrowBook(int userid,int bookid) {
		Optional<Users> user=userRepo.findById(userid);
		Optional<Books> book=bookRepo.findById(bookid);
		//check for availability
		if(user.isPresent() && book.isPresent()) {
			Users borrowuser=user.get();
			Books borrowbook=book.get();
			if(!borrowuser.getStatus().equals("Active")) {
				throw new RuntimeException("The user is Suspended");
			}
			if(!borrowbook.getStatus().equals("available")) {
				throw new RuntimeException("The book is not Available for Borrowing");
			}
			
			long currentBorrowedCount=bookTransactionRepo.countUserBorrowedBooks(userid);
			if(currentBorrowedCount>=Maximum_Books_User)
			{
				throw new RuntimeException("User has reached maximum book borrowing limit");
			}
			
			BookTransaction transaction=new BookTransaction(borrowbook, borrowuser, Default_Borrow_Days);
			borrowbook.setStatus("Borrowed");
			bookRepo.save(borrowbook);
			return bookTransactionRepo.save(transaction);
		}
		throw new RuntimeException("User or Book not found");
	}
	//Return Book
	public BookTransaction returnBook(int transactionId) {
        Optional<BookTransaction> transaction = bookTransactionRepo.findById(transactionId);
        
        if (transaction.isPresent()) {
            BookTransaction bookTransaction = transaction.get();        
            if (bookTransaction.getStatus() != TransactionStatus.BORROWED) {
                throw new RuntimeException("Book is not currently borrowed");
            }         
            bookTransaction.setReturnDate(LocalDate.now());
            bookTransaction.setStatus(TransactionStatus.RETURNED);
            if (LocalDate.now().isAfter(bookTransaction.getDueDate())) {
                long overdueDays = ChronoUnit.DAYS.between(bookTransaction.getDueDate(), LocalDate.now());
                double fine = overdueDays * Fine_Per_day;
                bookTransaction.setFineAmount(fine);
            }
            Books book = bookTransaction.getBook();
            book.setStatus("AVAILABLE");
            bookRepo.save(book);
            return bookTransactionRepo.save(bookTransaction);
        }
        throw new RuntimeException("Transaction not found");
    }
	// Get all transactions
    public List<BookTransaction> getAllTransactions() {
        return bookTransactionRepo.findAll();
    }
    
    // Get user's borrowing history
    public List<BookTransaction> getUserTransactions(int userId) {
        return bookTransactionRepo.findByUser_Userid(userId);
    }
    
    // Get current borrowed books by user
    public List<BookTransaction> getUserCurrentBorrowedBooks(int userId) {
        return bookTransactionRepo.findUserCurrentBorrowedBooks(userId);
    }
    
    // Get book's transaction history
    public List<BookTransaction> getBookTransactions(int bookId) {
        return bookTransactionRepo.findByBook_Bid(bookId);
    }
    
    // Get all currently borrowed books
    public List<BookTransaction> getCurrentBorrowedBooks() {
        return bookTransactionRepo.findCurrentBorrowedBooks();
    }
    
    // Get overdue books
    public List<BookTransaction> getOverdueBooks() {
        return bookTransactionRepo.findOverdueBooks(LocalDate.now());
    }
	
}
