package home.hw.module18hw.sevice;

import home.hw.module18hw.model.User;
import home.hw.module18hw.model.request.SignupRequest;
import home.hw.module18hw.repository.RoleRepository;
import home.hw.module18hw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public String createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return "User with that name already exists";
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        roleRepository.findByName("USER").ifPresent(user::addRole);

        userRepository.save(user);

        return "User created";
    }

    public User findByName(String username) {
        System.out.println("username = " + username);
        Optional<User> byUsername = userRepository.findByUsername(username);
        System.out.println("byUserName = " + byUsername);

        return byUsername.orElseThrow();
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
