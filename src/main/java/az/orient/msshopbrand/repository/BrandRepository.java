package az.orient.msshopbrand.repository;

import az.orient.msshopbrand.entity.BrandEntity;
import az.orient.msshopbrand.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    List<BrandEntity> findAllByStatus(Status status);
    Optional<BrandEntity> findByIdAndStatus(Long id, Status status);

}
