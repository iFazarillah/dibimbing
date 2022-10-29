package dibimbing.team2.repository;

import dibimbing.team2.model.Pembayaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PembayaranRepository extends PagingAndSortingRepository<Pembayaran, Long> {

    @Query("select c from Pembayaran c")// nama class
    public Page<Pembayaran> getAllData(Pageable pageable);

    @Query("select c from Pembayaran c WHERE c.id = :id")
    public Pembayaran getbyID(@Param("id") Long id);

}
