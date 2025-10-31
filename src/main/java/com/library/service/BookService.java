package com.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Books;
import com.library.repository.BookRepository;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepo;
	
	public Books saveBook(Books b) {
		if(b.getStatus()==null || b.getStatus().isEmpty()) {
			b.setStatus("Available");
		}
		return bookRepo.save(b);
	}
	
	public List<Books> getAll(){
		return bookRepo.findAll();
	}
	
	public Optional<Books> getOne(int bid){
		return bookRepo.findById(bid);
	}
	
	public Books updateBook(Books b, int id) {
		
		Optional<Books> theBook=bookRepo.findById(id);
		
		Books newBook=theBook.get();
		newBook.setBname(b.getBname());
		newBook.setBauthor(b.getBauthor());
		newBook.setPyear(b.getPyear());
		newBook.setBcategory(b.getBcategory());
		newBook.setIsbn(b.getIsbn());
		if(b.getStatus()==null || b.getStatus().isEmpty()) {
			newBook.setStatus(b.getStatus());
		}
		return bookRepo.save(newBook);
	}
	
	public void deleteBook(int id) {
		bookRepo.deleteById(id);
	}
	public List<Books> searchBooksByName(String bookName) {
        return bookRepo.findByBname(bookName);
    }
    
    public List<Books> searchBooksByAuthor(String author) {
        return bookRepo.findByBauthor(author);
    }
    
    public List<Books> searchBooksByCategory(String category) {
        return bookRepo.findByBcategory(category);
    }
    
    public List<Books> getBooksByStatus(String status) {
        return bookRepo.findByStatus(status);
    }
    
    public List<Books> getAvailableBooks() {
        return bookRepo.findAvailableBooks();
    }
    
    public Books getBookByIsbn(String isbn) {
        return bookRepo.findByIsbn(isbn);
    }
    
    public List<Books> getBooksByYear(String year) {
        return bookRepo.findByPyear(year);
    }
    
    public List<Books> searchBooks(String bookName, String author, String category) {
        return bookRepo.searchBooks(bookName, author, category);
    }
    public long getTotalBooks() {
        return bookRepo.count();
    }
    
    public long getAvailableBooksCount() {
        return bookRepo.countBooksByStatus("Available");
    }
    
    public long getBorrowedBooksCount() {
        return bookRepo.countBooksByStatus("BORROWED");
    }
}
