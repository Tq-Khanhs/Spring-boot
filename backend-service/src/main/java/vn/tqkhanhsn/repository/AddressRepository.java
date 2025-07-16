package vn.tqkhanhsn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tqkhanhsn.model.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
}
