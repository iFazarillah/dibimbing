package dibimbing.team2.service.impl;


import dibimbing.team2.model.Barang;
import dibimbing.team2.dao.TransaksiRequest;
import dibimbing.team2.model.Pembeli;
import dibimbing.team2.model.Transaksi;
import dibimbing.team2.model.oauth.User;
import dibimbing.team2.repository.BarangRepository;
import dibimbing.team2.repository.PembeliRepository;
import dibimbing.team2.repository.TransaksiRepository;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.TransaksiService;
import dibimbing.team2.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class TransaksiImpl implements TransaksiService {

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public TransaksiRepository transaksiRepository;

    @Autowired
    public BarangRepository barangRepository;

    @Autowired
    public PembeliRepository pembeliRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public Map simpan(TransaksiRequest request) {
        try {

            if (templateResponse.chekNull(request.getIdBarang())) {
                return templateResponse.templateEror("Id Barang Tidak boleh null");
            }
            if (templateResponse.chekNull(request.getIdPembeli())) {
                return templateResponse.templateEror("Id Pembeli Tidak boleh null");
            }
            Barang chekIdBarang = barangRepository.getbyID(request.getIdBarang());
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Barang Tidak ada di database");
            }
            User chekIdPembeli = userRepository.checkById(request.getIdPembeli());
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Pembeli Tidak ada di database");
            }
            //disimpan ke db: objek transaksi
            Transaksi objSave = new Transaksi();


            objSave.setQty(request.getQty());
            objSave.setHarga(chekIdBarang.getHarga());
            objSave.setTotalHarga(objSave.getHarga()*objSave.getQty());
            objSave.setStatus("dipesan");
            objSave.setBarang(chekIdBarang);
            objSave.setPembeli(chekIdPembeli);
            Transaksi doSave = transaksiRepository.save(objSave);

            return templateResponse.templateSukses(doSave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map update(TransaksiRequest request) {
        try {
            if (templateResponse.chekNull(request.getIdBarang())) {
                return templateResponse.templateEror("Id Barang Tidak boleh null");
            }

            if (templateResponse.chekNull(request.getIdPembeli())) {
                return templateResponse.templateEror("Id Pembeli Tidak boleh null");
            }
            if (templateResponse.chekNull(request.getId())) {
                return templateResponse.templateEror("Id Transaksi Tidak boleh null");
            }

            Barang chekIdBarang = barangRepository.getbyID(request.getIdBarang());
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Barang Tidak ada di database");
            }

            User chekIdPembeli = userRepository.checkById(request.getIdPembeli());
            if (templateResponse.chekNull(chekIdPembeli)) {
                return templateResponse.templateEror("Id Pembeli Tidak ada di database");
            }

            Transaksi chekIdTransaksi = transaksiRepository.getbyID(request.getId());
            if (templateResponse.chekNull(chekIdBarang)) {
                return templateResponse.templateEror("Id Transaksi Tidak ada di database");
            }
            //update disini
            chekIdTransaksi.setBarang(chekIdBarang);
            chekIdTransaksi.setHarga(chekIdBarang.getHarga());
            chekIdTransaksi.setQty(request.getQty());
            chekIdTransaksi.setTotalHarga(chekIdTransaksi.getHarga()*chekIdTransaksi.getQty());
            Transaksi doSave = transaksiRepository.save(chekIdTransaksi);
            return templateResponse.templateSukses(doSave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map delete(Long obj) {
      /*
        soft delete , bukan delete permanent
        1. chek id barang
        2. update , tanggal deleted saja
         */
        try {
            if (templateResponse.chekNull(obj)) {
                return templateResponse.templateEror("Id Transaksi is required");
            }
            //            1. chek id barang
            Transaksi chekIdTransaksi = transaksiRepository.getbyID(obj);
            if (templateResponse.chekNull(chekIdTransaksi)) {
                return templateResponse.templateEror("Id Transksi Not found");
            }

//            2. update , tanggal deleted saja
            chekIdTransaksi.setDeleted_date(new Date());//
            transaksiRepository.save(chekIdTransaksi);

            return templateResponse.templateSukses("sukses deleted");

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map cancel(Long obj) {
        try {
            if (templateResponse.chekNull(obj)) {
                return templateResponse.templateEror("Id Transaksi is required");
            }
            //            1. chek id barang
            Transaksi chekIdTransaksi = transaksiRepository.getbyID(obj);
            if (templateResponse.chekNull(chekIdTransaksi)) {
                return templateResponse.templateEror("Id Transksi Not found");
            }

//            2. update , tanggal deleted saja
            chekIdTransaksi.setDeleted_date(new Date());//
            transaksiRepository.save(chekIdTransaksi);

            return templateResponse.templateSukses("sukses canceled");

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map getById(Long id) {
        try {
            Optional<Transaksi> findTransaksi = transaksiRepository.findById(id);
            return templateResponse.templateSukses(findTransaksi);
        } catch ( Exception e ) {
            return templateResponse.templateEror(e);
        }
    }


}
