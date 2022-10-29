package dibimbing.team2.service.impl;


import dibimbing.team2.config.Config;
import dibimbing.team2.dao.request.LoginModel;
import dibimbing.team2.dao.request.RegisterModel;
import dibimbing.team2.model.Pembeli;
import dibimbing.team2.model.oauth.Role;
import dibimbing.team2.model.oauth.User;
import dibimbing.team2.repository.oauth.RoleRepository;
import dibimbing.team2.repository.oauth.UserRepository;
import dibimbing.team2.service.oauth.Oauth2UserDetailsService;
import dibimbing.team2.service.user.UserService;
import dibimbing.team2.utils.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;

import java.security.Principal;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    Config config = new Config();
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    RoleRepository repoRole;

    @Autowired
    UserRepository repoUser;
    @Autowired
    private Oauth2UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public TemplateResponse response;

    @Autowired
    public TemplateResponse responses,templateResponse;

    @Value("${BASEURL}")
    private String baseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Map login(LoginModel loginModel) {
        /**
         * bussines logic for login here
         * **/
        try {

            if (loginModel.getEmail() == "") {
                return response.Error("Fill the email");
            }

            if (loginModel.getPassword() == "") {
                return response.Error("Fill the password");
            }

            if(response.chekNull(loginModel.getEmail())) {
                return response.Error("Email is required");
            }

            if(response.chekNull(loginModel.getPassword())) {
                return response.Error("Password is required");
            }

            Map<String, Object> map = new HashMap<>();

            User checkUser = repoUser.findOneByUsername(loginModel.getEmail());

//            if (checkUser.isBlocked()) {
//                return response.Error("Your account is blocked, please contact Admin!");
//            }

            if ((checkUser != null) && (encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                if (!checkUser.isEnabled()) {
                    map.put("is_enabled", checkUser.isEnabled());
                    return response.Error(map);
                }
            }
            if (checkUser == null) {
                return response.notFound("User not found");
            }
            if (!(encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                return response.Error("Wrong Password");
            }
            String url = baseUrl + "/oauth/token?username=" + loginModel.getEmail() +
                    "&password=" + loginModel.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";
            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
                    ParameterizedTypeReference<Map>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                User user = repoUser.findOneByUsername(loginModel.getEmail());
                List<String> roles = new ArrayList<>();

                for ( Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                //save token
//                checkUser.setAccessToken(response.getBody().get("access_token").toString());
//                checkUser.setRefreshToken(response.getBody().get("refresh_token").toString());
//                userRepository.save(checkUser);

                repoUser.save(user);


                map.put("access_token", response.getBody().get("access_token"));
                map.put("token_type", response.getBody().get("token_type"));
                map.put("refresh_token", response.getBody().get("refresh_token"));
                map.put("expires_in", response.getBody().get("expires_in"));
                map.put("scope", response.getBody().get("scope"));
                map.put("jti", response.getBody().get("jti"));
                map.put("user", user);


                return responses.Sukses(map);
            } else {
                return null;
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return response.Error("invalid login");
            }
            return response.Error(e);
        } catch (Exception e) {
            e.printStackTrace();

            return response.Error(e);
        }
    }

    @Override
    public Map getDetailProfile(Principal principal) {
        User idUser = getUserIdToken(principal, userDetailsService);
        try {
            User obj = repoUser.save(idUser);
            return response.Sukses(obj);
        } catch (Exception e){
            return response.Error(e);
        }
    }

    @Override
    public Map getDetail(Principal principal, Long idUserDetail) {
        User idUser = getUserIdToken(principal, userDetailsService);

        try {
            if (idUser == null) {
                return response.notFound("user not found");
            }
            return response.Sukses(idUser);
        } catch (Exception e){
            return response.Error(e);
        }
    }

    private User getUserIdToken(Principal principal, Oauth2UserDetailsService userDetailsService) {
        UserDetails user = null;
        String username = principal.getName();
        if (!StringUtils.isEmpty(username)) {
            user = userDetailsService.loadUserByUsername(username);
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        User idUser = repoUser.findOneByUsername(user.getUsername());
        if (null == idUser) {
            throw new UsernameNotFoundException("User name not found");
        }
        return idUser;
    }

    public Map registerManual(RegisterModel objModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // admin
            User user = new User();
            user.setUsername(objModel.getEmail().toLowerCase());
            user.setUsername1(objModel.getUsername().toLowerCase());
            user.setFullname(objModel.getFullname());

            //step 1 :
            user.setEnabled(false); // matikan user

            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = repoRole.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);
            User obj = repoUser.save(user);

            return templateResponse.templateSukses(obj);

        } catch (Exception e) {
            logger.error("Eror registerManual=", e);
            return templateResponse.templateEror("eror:"+e);
        }

    }

    @Override
    public Map updateUser(RegisterModel objModel, Long idUser) {
        try {
            User update = repoUser.checkExistingEmail(objModel.getEmail());
            if ( templateResponse.chekNull(update) ){
                return templateResponse.templateEror("User not found");
            }
            if ( !templateResponse.chekNull(objModel.getFullname()) ){
                update.setFullname(objModel.getFullname());
            }
            if ( !templateResponse.chekNull(objModel.getUsername() )){
                update.setUsername1(objModel.getUsername());
            }

            User updateUser = repoUser.save(update);

            return templateResponse.templateSukses(update);
        } catch ( Exception e ){
            return templateResponse.templateEror(e);
        }
    }

//    @Override
//    public Map deactivateUser(Long idUser) {
//        try {
//            User delete = repoUser.checkById(idUser);
//            if ( templateResponse.chekNull(delete) ){
//                return templateResponse.templateEror("User not found");
//            }
//            repoUse(delete);
//            return templateResponse.templateSukses("Your account has been deactivated, Thank you");
//        } catch ( Exception e ){
//            return templateResponse.templateEror(e);
//        }
//    }


}


