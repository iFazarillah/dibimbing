package dibimbing.team2.controller;


import dibimbing.team2.dao.TransaksiRequest;
import dibimbing.team2.model.Transaksi;
import dibimbing.team2.repository.TransaksiRepository;
import dibimbing.team2.service.TransaksiService;
import dibimbing.team2.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/transaksi")
public class TransaksiController {

    @Autowired
    public TransaksiService transaksiService;

    @Autowired
    public TemplateResponse templateResponse;

    @Autowired
    public TransaksiRepository transaksiRepository;

    @PostMapping("")
    public ResponseEntity<Map> save(@RequestBody TransaksiRequest objModel) {
        Map obj = transaksiService.simpan(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Map> update(@RequestBody TransaksiRequest objModel ) {
        Map map = transaksiService.update(objModel);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = transaksiService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listByNama(
            @RequestParam() Integer page,
            @RequestParam() Integer size
    )
    {
        Map map = new HashMap();
        Page<Transaksi> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").descending());

        list = transaksiRepository.getAllData(show_data);
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getId(@PathVariable(value = "id") Long id){
        Map barang = transaksiService.getById(id);
        return new ResponseEntity<Map>(templateResponse.templateSukses(barang), HttpStatus.OK);
    }
}
