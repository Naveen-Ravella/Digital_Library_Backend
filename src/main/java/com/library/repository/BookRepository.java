package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entity.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer>{
	List<Books> findByBname(String bname);
	List<Books> findByBauthor(String bauthor);
	List<Books> findByBcategory(String bcategory);
	List<Books> findByStatus(String status);
	@Query("select b from Books b where b.status='Available'")
	List<Books> findAvailableBooks();
	List<Books> findByPyear(String pyear);
	Books findByIsbn(String isbn);
	
	@Query("select count(b) from Books b where b.status=:status")
	long countBooksByStatus(@Param("status") String status);
	
	@Query("SELECT b FROM Books b WHERE " +
	           "(:bookName IS NULL OR LOWER(b.bname) LIKE LOWER(CONCAT('%', :bookName, '%'))) AND " +
	           "(:author IS NULL OR LOWER(b.bauthor) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
	           "(:category IS NULL OR LOWER(b.bcategory) LIKE LOWER(CONCAT('%', :category, '%')))")
	    List<Books> searchBooks(@Param("bookName") String bookName, 
	                           @Param("author") String author, 
	                           @Param("category") String category);
}
