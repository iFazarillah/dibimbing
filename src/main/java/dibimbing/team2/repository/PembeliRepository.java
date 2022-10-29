package dibimbing.team2.repository;

import dibimbing.team2.model.Pembeli;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PembeliRepository extends
        PagingAndSortingRepository<Pembeli, Long> {

    @Query("select c from Pembeli c WHERE c.id = :id")
    public Pembeli getbyID(@Param("id") Long id);

    @Query("select c from Pembeli c ")// nama class
    public Page<Pembeli> getAllData(Pageable pageable);

//    @Query("select c from Pembeli c where c.nama like :nama")// nama class
//    public Page<Pembeli> findByNama(String nama, Pageable pageable);
}