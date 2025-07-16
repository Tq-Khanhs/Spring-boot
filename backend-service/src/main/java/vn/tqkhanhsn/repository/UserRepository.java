package vn.tqkhanhsn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tqkhanhsn.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

}
