package dibimbing.team2.controller;


import dibimbing.team2.model.Pembeli;
import dibimbing.team2.repository.PembeliRepository;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.PembeliService;
import dibimbing.team2.utils.TemplateResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/pembeli")
public class PembeliController {

    @Autowired
    public PembeliRepository pembeliRepository;

    @Autowired
    public PembeliService pembeliService;

    @Autowired
    public TemplateResponse templateResponse;

    @PostMapping("/save/{idUser}")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> save(@PathVariable(value = "idUser") Long idUser,
                                    @Valid @RequestBody Pembeli objModel){
        Map map = new HashMap();
        Map obj = pembeliService.insert(objModel, idUser);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @PostMapping("/update/{idUser}")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> update(@PathVariable(value = "idUser") Long idUser,
                                    @Valid @RequestBody Pembeli objModel){
        Map map = new HashMap();
        Map obj = pembeliService.update(objModel, idUser);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<Map> delete(@PathVariable(value = "idUser") Long idUser) {
        Map map = pembeliService.softDelete(idUser);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}
