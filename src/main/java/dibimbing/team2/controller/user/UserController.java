package dibimbing.team2.controller.user;

import dibimbing.team2.dao.request.RegisterModel;
import dibimbing.team2.model.Pembeli;
import dibimbing.team2.model.oauth.User;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.user.UserService;
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
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    TemplateResponse templateResponse;

    @PostMapping("/update/{idUser}")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> update(@PathVariable(value = "idUser") Long idUser,
                                      @Valid @RequestBody RegisterModel objModel){
        Map map = new HashMap();
        Map obj = userService.updateUser(objModel, idUser);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

//    @DeleteMapping("/delete/{idUser}")
//    public ResponseEntity<Map> delete(@PathVariable(value = "idUser") Long idUser){
//        Map map = new HashMap();
//        Map obj = userService.deactivateUser(idUser);
//        return new ResponseEntity<Map>(obj, HttpStatus.OK);
//
//    }


}
