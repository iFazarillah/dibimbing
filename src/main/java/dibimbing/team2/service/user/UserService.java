package dibimbing.team2.service.user;


import dibimbing.team2.dao.request.LoginModel;
import dibimbing.team2.dao.request.RegisterModel;

import java.security.Principal;
import java.util.Map;

public interface UserService {

    public Map login(LoginModel objLogin);

    public Map getDetailProfile(Principal principal);

    public Map getDetail(Principal principal, Long idUserDetail);

    Map registerManual(RegisterModel objModel);

    Map updateUser(RegisterModel objModel, Long idUser);

//    Map deactivateUser(Long idUser);


}
