package vn.tqkhanhsn.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.tqkhanhsn.model.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query("SELECT u FROM UserEntity u WHERE (" +
            "lower(u.firstName) LIKE lower(CONCAT('%', :keyword, '%')) OR " +
            "lower(u.lastName) LIKE lower(CONCAT('%', :keyword, '%')) OR " +
            "lower(u.username) LIKE lower(CONCAT('%', :keyword, '%')) OR " +
            "lower(u.email) LIKE lower(CONCAT('%', :keyword, '%')) OR " +
            "lower(u.phone) LIKE lower(CONCAT('%', :keyword, '%')))")
    Page<UserEntity> searchByKeywords(String keyword, Pageable pageable);
}
