package dibimbing.team2.repository.oauth;



import dibimbing.team2.model.oauth.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("FROM User u WHERE LOWER(u.username) = LOWER(?1)")
    User findOneByUsername(String username);

    @Query("FROM User u WHERE u.otp = ?1")
    User findOneByOTP(String otp);

    @Query("FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    User checkExistingEmail(String username);

    @Query("FROM User u WHERE LOWER(u.username1) = LOWER(:username)")
    User checkExistingUsername(String username);

    @Query("From User u WHERE u.id = :id")
    User checkById(Long id);


}
