package dibimbing.team2.service.impl;

import dibimbing.team2.model.Barang;
import dibimbing.team2.repository.BarangRepository;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.BarangService;
import dibimbing.team2.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class BarangImpl implements BarangService {

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public BarangRepository barangRepository;

    @Autowired
    public UserRepository userRepository;


    @Override
    public Map insert(Barang barang) {
        try {
            if (templateResponse.chekNull(barang.getNama())) {
                return templateResponse.templateEror("Nama is Requiered");
            }

            if (templateResponse.chekNull(barang.getHarga())) {
                return templateResponse.templateEror("Harga is requiered");
            }
            Barang barangSave = barangRepository.save(barang);
            return templateResponse.templateSukses(barangSave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map getAll(int size, int page) {
        try {
            Pageable show_data = PageRequest.of(page, size);
            Page<Barang> list = barangRepository.getAllData(show_data);
            return templateResponse.templateSukses(list);
        } catch (Exception e) {
//            log.error("ada eror di method getAll:" + e);
            return templateResponse.templateEror(e);
        }
    }


    @Override
    public Map update(Barang barang) {
        try {

            if (templateResponse.chekNull(barang.getId())) {
                return templateResponse.templateEror("Id Barang is required");
            }
            Barang chekIdBarang = barangRepository.getbyID(barang.getId());
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Barang Not found");
            }

            chekIdBarang.setNama(barang.getNama());
            chekIdBarang.setHarga(barang.getHarga());
            chekIdBarang.setStok(barang.getStok());
            chekIdBarang.setSatuan(barang.getSatuan());
            Barang dosave = barangRepository.save(chekIdBarang);

            return templateResponse.templateSukses(dosave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }

    }

    @Override
    public Map delete(Long barang) {
        try {
            if (templateResponse.chekNull(barang)) {
                return templateResponse.templateEror("Id Barang is required");
            }

            Barang chekIdBarang = barangRepository.getbyID(barang);
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Barang Not found");
            }

            chekIdBarang.setDeleted_date(new Date());//
            barangRepository.save(chekIdBarang);

            return templateResponse.templateSukses("sukses deleted");

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map findByNama(String nama, Integer page, Integer size) {
        try {
           /*
           1. buat query dulu where nama barang
            */
            Pageable show_data = PageRequest.of(page, size);
            Page<Barang> list = barangRepository.findByNama(nama, show_data);
            return templateResponse.templateSukses(list);
        } catch (Exception e) {

//            log.error("eror disini findByNama : " + e);
            //menampilkan responose
            return templateResponse.templateEror(e);
        }
    }


    @Override
    public Page<Barang> findByNamaLike(String nama, Pageable pageable) {
        try {
           /*
           1. buat query dulu where nama barang = like
            */
            //tidak pake kutip
            //perhatikan tolowercasenya
            Page<Barang> list = barangRepository.findByNamaLike("%" + nama + "%", pageable);
//             public Page<Barang> findByNamaLike(String nama , Pageable pageable);
            return list;
        } catch (Exception e) {
            // manampilkan di terminal saja
//            log.error("ada eror di method findByNamaLike:" + e);
            return null;
        }
    }

    @Override
    public Map findById(Long id) {
        try {
            Optional<Barang> findBarang = barangRepository.findById(id);
            return templateResponse.templateSukses(findBarang);
        } catch ( Exception e ) {
            return templateResponse.templateEror(e);
        }
    }

}
