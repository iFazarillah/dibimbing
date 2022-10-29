package dibimbing.team2.service.impl;

import dibimbing.team2.dao.PembayaranRequest;
import dibimbing.team2.model.Pembayaran;
import dibimbing.team2.model.Transaksi;
import dibimbing.team2.repository.PembayaranRepository;
import dibimbing.team2.repository.TransaksiRepository;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.PembayaranService;
import dibimbing.team2.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class PembayaranImpl implements PembayaranService {

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public PembayaranRepository pembayaranRepository;

    @Autowired
    public  TransaksiRepository transaksiRepository;


    @Override
    public Map createOrder(PembayaranRequest pembayaran) {
        try{
            Transaksi obj = transaksiRepository.getbyID(pembayaran.getIdTransaksi());
            if(obj == null){
                return templateResponse.templateEror("Transaksi id tidak di temukan!");
            }
            Pembayaran create = new Pembayaran();
            create.setStatus("Waiting for payment");
            create.setTransaksi(obj);
            create.setNoOrder(String.valueOf(UUID.randomUUID()));
            create.setTotalBayar(obj.getTotalHarga());
            create.setUser(obj.getPembeli());

            Pembayaran doSave = pembayaranRepository.save(create);
            obj.setStatus("Waiting for payment");
            transaksiRepository.save(obj);

            return templateResponse.templateSukses(doSave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map uploadBuktiBayar(PembayaranRequest request) {
        try{
            Pembayaran obj = pembayaranRepository.getbyID(request.getId());
            if(obj == null){
                return templateResponse.templateEror("Payment not found");
            }
            obj.setBuktiTrf(request.getBuktiTrf());
            obj.setStatus("Waiting for confirmation");

            Pembayaran doSave = pembayaranRepository.save(obj);
            obj.getTransaksi().setStatus("Waiting for payment");
            transaksiRepository.save(obj.getTransaksi());

            return templateResponse.templateSukses(doSave);
        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }

    @Override
    public Map approvalBuktiBayar(Long idPembayaran) {
        try{
            Pembayaran approve = pembayaranRepository.getbyID(idPembayaran);
            if(approve == null) {
                return templateResponse.templateEror("Payment not found");
            }

            approve.setStatus("Payment completed");
            Pembayaran doSave = pembayaranRepository.save(approve);
            approve.getTransaksi().setStatus("Waiting for shipment");
            transaksiRepository.save(approve.getTransaksi());
            return templateResponse.templateSukses(doSave);

        } catch (Exception e) {
            return templateResponse.templateEror(e);
        }
    }
}
