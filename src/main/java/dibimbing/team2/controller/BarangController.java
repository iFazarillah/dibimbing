package dibimbing.team2.controller;

import dibimbing.team2.config.Config;
import dibimbing.team2.model.Barang;
import dibimbing.team2.repository.BarangRepository;
import dibimbing.team2.service.BarangService;
import dibimbing.team2.utils.SimpleStringUtils;
import dibimbing.team2.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/v1/barang")
public class BarangController {


    @Autowired
    public BarangService barangService;

    Config config = new Config();
    @Autowired
    public BarangRepository barangRepository;

    @Autowired
    public TemplateResponse templateResponse;

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();

    @PostMapping("/save")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> save(@Valid @RequestBody Barang objModel) {
        Map obj = barangService.insert(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Map> update(@RequestBody Barang objModel ) {
        Map map = barangService.update(objModel);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = barangService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listByBama(
            @RequestParam() Integer page,
            @RequestParam() Integer size) {
        Map list = barangService.getAll(size, page);
        return new ResponseEntity<Map>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("list-barang")
    public ResponseEntity<Map> listNotif(
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String nama,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype,
            Principal principal) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);
        Page<Barang> list = null;


        if (nama != null && priceMin !=null && priceMax != null ) {
            list = barangRepository.getDataByPriceAndNama(priceMin,priceMax, "'%" + nama + "%'",show_data);
        } else if ( priceMin !=null && priceMax != null ) {
            list = barangRepository.getDataByPrice(priceMin,priceMax, show_data);
        } else if (nama != null ) {
            list = barangRepository.findByNamaLike("%" + nama + "%", show_data);
        } else {
            list = barangRepository.getAllData(show_data);
        }
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getId(@PathVariable(value = "id") Long id){
        Map barang = barangService.findById(id);
        return new ResponseEntity<Map>(templateResponse.templateSukses(barang), HttpStatus.OK);
    }
}
