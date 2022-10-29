package dibimbing.team2.repository;

import dibimbing.team2.model.Barang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BarangRepository extends PagingAndSortingRepository<Barang, Long> {

    @Query("select c from Barang c")// nama class
    public Page<Barang> getAllData(Pageable pageable);

    @Query("select c from Barang c WHERE c.id = :idbarang")
    public Barang getbyID(@Param("idbarang") Long idbebas);

    @Query("select c from Barang c where c.nama= :nama")
    public Page<Barang> findByNama(String nama, Pageable pageable);

    public Page<Barang> findByNamaLike(String nama, Pageable pageable);

    public Barang findBySatuan(String satuan);

    public Barang findBySatuanAndHarga(String satuan, int harga);

    public Barang findBySatuanAndHargaOrNama(String satuan, int harga, String nama);

    public Barang findBySatuanAndHargaOrNamaLike(String satuan, int harga, String nama);// "'%%'"

    @Query("select c from Barang c where  c.harga BETWEEN :priceMin and :priceMax")
    Page<Barang> getDataByPrice(Double priceMin, Double priceMax, Pageable pageable);

    @Query("select c from Barang c where  c.harga BETWEEN :priceMin and :priceMax and c.nama like :nama")
    Page<Barang> getDataByPriceAndNama(Double priceMin, Double priceMax, String nama, Pageable pageable);

}
