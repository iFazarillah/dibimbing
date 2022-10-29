package dibimbing.team2.controller;

import dibimbing.team2.dao.PembayaranRequest;
import dibimbing.team2.repository.PembayaranRepository;
import dibimbing.team2.service.PembayaranService;
import dibimbing.team2.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/v1/pembayaran")
public class PembayaranController {

    @Autowired
    public PembayaranService pembayaranService;

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public PembayaranRepository pembayaranRepository;

    @PostMapping("/create")
    public ResponseEntity<Map> createOrder(  @RequestBody PembayaranRequest pembayaranRequest) {
        Map obj = pembayaranService.createOrder(pembayaranRequest);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadBuktiTransfer(@RequestBody PembayaranRequest pembayaranRequest) {
        Map obj = pembayaranService.uploadBuktiBayar(pembayaranRequest);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<Map> ApprovalBuktiTransfer(@PathVariable(value = "id") Long id) {
        Map obj = pembayaranService.approvalBuktiBayar(id);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }
}
