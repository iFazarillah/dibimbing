package dibimbing.team2.controller.user;


import dibimbing.team2.config.Config;
import dibimbing.team2.dao.request.RegisterModel;
import dibimbing.team2.model.Barang;
import dibimbing.team2.model.oauth.User;
import dibimbing.team2.repository.BarangRepository;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.email.EmailSender;
import dibimbing.team2.service.email.email.EmailService;
import dibimbing.team2.service.email.email.MailRequest;
import dibimbing.team2.service.email.email.MailResponse;
import dibimbing.team2.service.user.UserService;
import dibimbing.team2.utils.EmailTemplate;
import dibimbing.team2.utils.SimpleStringUtils;
import dibimbing.team2.utils.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/user/register")
public class RegisterController {
    @Autowired
    public EmailTemplate emailTemplate;
    @Value("${expired.token.password.minute:}")//FILE_SHOW_RUL
    private int expiredToken;


    @Autowired
    public EmailSender emailSender;
    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService serviceReq;

    @Autowired
    public TemplateResponse templateCRUD;

    @PostMapping("/")
    public ResponseEntity<Map> saveRegisterManual(@RequestBody RegisterModel objModel) throws RuntimeException {
        Map map = new HashMap();

        User user = userRepository.checkExistingEmail(objModel.getEmail());
        if (null != user) {
            return new ResponseEntity<Map>(templateCRUD.notFound("Email is already registered"), HttpStatus.OK);
        }
        User checkUn = userRepository.checkExistingUsername(objModel.getUsername());
        if ( null != checkUn ){
            return new ResponseEntity<Map>(templateCRUD.notFound("Username is already registered"), HttpStatus.OK);
        }

        map = serviceReq.registerManual(objModel);
        Map sendOTP = sendEmailegister(objModel);

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }


    // Step 2: sendp OTP berupa URL: guna updeta enable agar bisa login:
    @PostMapping("/send-otp")//send OTP
    public Map sendEmailegister(@RequestBody RegisterModel user) {
        String message = "Thanks, please check your email for activation.";

        if (user.getEmail() == null) return templateCRUD.templateEror("No email provided");
        User found = userRepository.findOneByUsername(user.getEmail());
        if (found == null) return templateCRUD.notFound("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getRegisterTemplate();
        if (StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtils.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? found.getUsername() : found.getUsername()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",  otp);
            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? found.getUsername() : found.getUsername()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",  found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Register", template);
        return templateCRUD.templateSukses(message);
    }



    @GetMapping("/confirm-otp/{token}")
    public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp) throws RuntimeException {


        User user = userRepository.findOneByOTP(tokenOtp);
        if (null == user) {
            return new ResponseEntity<Map>(templateCRUD.templateEror("OTP tidak ditemukan"), HttpStatus.OK);
        }
        String today = config.convertDateToString(new Date());

        String dateToken = config.convertDateToString(user.getOtpExpiredDate());
        if(Long.parseLong(today) > Long.parseLong(dateToken)){
            return new ResponseEntity<Map>(templateCRUD.templateEror("Your token is expired. Please Get token again."), HttpStatus.OK);
        }
        //update user
        user.setEnabled(true);
        userRepository.save(user);

        return new ResponseEntity<Map>(templateCRUD.templateEror("Sukses, Silahkan Melakukan Login"), HttpStatus.OK);
    }

    @Autowired
    private EmailService service;
    @Autowired
    public BarangRepository barangRepository;

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();




}
