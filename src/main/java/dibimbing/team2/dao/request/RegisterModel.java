package dibimbing.team2.dao.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

//@PasswordValueMatch.List({
//        @PasswordValueMatch(
//                field = "password",
//                fieldMatch = "confirmPassword",
//                message = "Passwords do not match!"
//        )
//})
@Data
public class RegisterModel {
    public Long id;
//    @Size(
//            min = 5,
//            max = 50,
//            message = "The author email '${validatedValue}' must be between {min} and {max} characters long"
//    )
    public String email;

    public String username;

    public String fullname;


//    @ValidPassword
    @NotNull(message =  "Password is mandatory")
    public String password;

//    @ValidPassword
    @NotNull(message =  "Password is mandatory")
    private String confirmPassword;

}
