package smartwater.api.pi.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);

    User getReferenceByEmail(String email);

    @Modifying
    @Query(value = "UPDATE USERS u SET u.email = :newEmail, u.password = :newPassword, u.user_role = :newUserRole WHERE u.id = :userId",
        nativeQuery = true,
        countQuery = "1"
    )
    User setUserInfoById(@Param("userId") Long userId, 
        @Param("newEmail") String newEmail, 
        @Param("newPassword") String newPassword,
        @Param("newUserRole") String newUserRole
    );
}
