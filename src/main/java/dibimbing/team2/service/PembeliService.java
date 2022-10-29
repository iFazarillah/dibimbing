package dibimbing.team2.service;

import dibimbing.team2.model.Barang;
import dibimbing.team2.model.Pembeli;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PembeliService {

    public Map insert(Pembeli pembeli, Long idUser);

    public Map update(Pembeli pembeli, Long idBuyer);

    public Map softDelete(Long idBuyer);

    public Map getAll(int size, int Page);

    public Map findByNama(String nama, Integer page, Integer size);

    Page<Pembeli> findByNamaLike(String nama, Pageable pageable);


}
