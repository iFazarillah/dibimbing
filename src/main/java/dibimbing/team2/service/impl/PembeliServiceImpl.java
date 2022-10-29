package dibimbing.team2.service.impl;

import dibimbing.team2.model.Pembeli;
import dibimbing.team2.model.oauth.User;
import dibimbing.team2.repository.PembeliRepository;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.PembeliService;
import dibimbing.team2.utils.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PembeliServiceImpl implements PembeliService {

    public static final Logger log = LoggerFactory.getLogger(PembeliServiceImpl.class);

    @Autowired
    public UserRepository userRepository;

    @Autowired
    PembeliRepository pembeliRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @Override
    public Map insert(Pembeli pembeli, Long idUser) {
        try {

            if ( templateResponse.chekNull(idUser) ) {
                return templateResponse.templateEror("User ID is requiered");
            }
            User checkId = userRepository.checkById(idUser);
            if ( templateResponse.chekNull(checkId) ) {
                return templateResponse.templateEror("User data Not found");
            }
            if (templateResponse.chekNull(pembeli.getAlamat())){
                return templateResponse.templateEror("Address is required");
            }
            if ( templateResponse.chekNull(pembeli.getNoHp()) ){
                return templateResponse.templateEror("Phone number is required");
            }

            Pembeli pembeliData = new Pembeli();

            pembeliData.setUser(checkId);
            pembeliData.setAlamat(pembeli.getAlamat());
            pembeliData.setKota(pembeli.getKota());
            pembeliData.setPropinsi(pembeli.getPropinsi());
            pembeliData.setNegara(pembeli.getNegara());
            pembeliData.setKodepos(pembeli.getKodepos());
            pembeliData.setNoHp(pembeli.getNoHp());
            Pembeli savePembeli = pembeliRepository.save(pembeliData);

            return templateResponse.templateSukses(pembeliData);
        } catch ( Exception e  ){
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map update(Pembeli pembeli, Long idBuyer) {
        try {
            if ( templateResponse.chekNull(pembeli.getId()) ){
                return templateResponse.templateEror("Buyer ID is required");
            }
            Pembeli update = pembeliRepository.getbyID(pembeli.getId());
            if ( templateResponse.chekNull(update) ) {
                return templateResponse.templateEror("Buyer Not found");
            }

            update.setAlamat(pembeli.getAlamat());
            update.setKota(pembeli.getKota());
            update.setPropinsi(pembeli.getPropinsi());
            update.setNegara(pembeli.getNegara());
            update.setKodepos(pembeli.getKodepos());
            update.setNoHp(pembeli.getNoHp());
            update.setUpdated_date(new Date());
            Pembeli updatePembeli = pembeliRepository.save(update);

            return templateResponse.templateSukses(update);
        } catch ( Exception e ){
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map softDelete(Long idBuyer) {
        try {
            if ( templateResponse.chekNull(idBuyer) ){
                return templateResponse.templateEror("Buyer ID is required");
            }
            Pembeli delete = pembeliRepository.getbyID(idBuyer);
            if ( templateResponse.chekNull(delete) ) {
                return templateResponse.templateEror("Buyer Not found");
            }
            delete.setDeleted_date(new Date());
            Pembeli deletePembeli = pembeliRepository.save(delete);

            return  templateResponse.templateSukses("Data deletion completed");
        } catch ( Exception e ){
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map getAll(int size, int Page) {
        return null;
    }

    @Override
    public Map findByNama(String nama, Integer page, Integer size) {
        return null;
    }

    @Override
    public Page<Pembeli> findByNamaLike(String nama, Pageable pageable) {
        return null;
    }
}
