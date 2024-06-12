package smartwater.api.pi.domain.users;

public record UserRegistrationData(
    Long id,
    String email,
    String password,
    String user_role
) {
    
    public UserRegistrationData(User user) {
        this(user.getId(), user.getEmail(), user.getPassword(), user.getUser_role());
    }
}
