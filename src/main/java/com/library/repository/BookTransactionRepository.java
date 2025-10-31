package com.library.repository;

import java.time.LocalDate;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entity.BookTransaction;
import com.library.entity.BookTransaction.TransactionStatus;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction, Integer>{
	
	List<BookTransaction> findByUser_Userid(int userid);
	
	List<BookTransaction> findByBook_Bid(int bid);
	List<BookTransaction> findByStatus(TransactionStatus status);
	
	@Query("SELECT b FROM BookTransaction b WHERE b.status = 'BORROWED'")
    List<BookTransaction> findCurrentBorrowedBooks();
    
    @Query("SELECT b FROM BookTransaction b WHERE b.dueDate < :currentDate AND b.status = 'BORROWED'")
    List<BookTransaction> findOverdueBooks(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT b FROM BookTransaction b WHERE b.user.userid = :userid AND b.status = 'BORROWED'")
    List<BookTransaction> findUserCurrentBorrowedBooks(@Param("userid") int userid);
    
    @Query("SELECT COUNT(b) FROM BookTransaction b WHERE b.user.userid = :userid AND b.status = 'BORROWED'")
    long countUserBorrowedBooks(@Param("userid") int userid);
    
    @Query("SELECT b FROM BookTransaction b WHERE b.book.bid = :bid AND b.status = 'BORROWED'")
    BookTransaction findCurrentBorrowedBookTransaction(@Param("bid") int bid);
	
}
