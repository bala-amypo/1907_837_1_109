
// package com.example.demo.repository;
// import com.example.demo.entity.CreditCardRecord;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import java.util.List;
// public interface CreditCardRecordRepository extends JpaRepository<CreditCardRecord, Long> {
//     List<CreditCardRecord> findByUserId(Long userId);
//     @Query("SELECT c FROM CreditCardRecord c WHERE c.userId = ?1 AND c.status = 'ACTIVE'")
//     List<CreditCardRecord> findActiveCardsByUser(Long userId);
// }

package com.example.demo.repository;
import com.example.demo.entity.CreditCardRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CreditCardRecordRepository extends JpaRepository<CreditCardRecord, Long> {
    List<CreditCardRecord> findByUserId(Long userId);
    
    @Query("SELECT c FROM CreditCardRecord c WHERE c.userId = ?1 AND c.status = 'ACTIVE'")
    List<CreditCardRecord> findActiveCardsByUser(Long userId);
}