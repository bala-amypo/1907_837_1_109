// package com.example.demo.repository;

// import com.example.demo.entity.RecommendationRecord;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface RecommendationRecordRepository extends JpaRepository<RecommendationRecord, Long> {
//     List<RecommendationRecord> findByUserId(Long userId);
// }

// package com.example.demo.repository;

// import com.example.demo.entity.RecommendationRecord;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.List;

// public interface RecommendationRecordRepository extends JpaRepository<RecommendationRecord, Long> {
//     List<RecommendationRecord> findByUserId(Long userId);
// }
package com.example.demo.repository;
import com.example.demo.entity.RecommendationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RecommendationRecordRepository extends JpaRepository<RecommendationRecord, Long> {
    List<RecommendationRecord> findByUserId(Long userId);
}