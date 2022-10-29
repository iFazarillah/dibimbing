package dibimbing.team2.service;


import dibimbing.team2.dao.PembayaranRequest;
import dibimbing.team2.model.Pembayaran;
import dibimbing.team2.model.Transaksi;

import java.util.Map;

public interface PembayaranService {

    public Map createOrder(PembayaranRequest pembayaranRequest);
    public Map uploadBuktiBayar(PembayaranRequest pembayaranRequest);
    public Map approvalBuktiBayar(Long idPembayaran);

}
