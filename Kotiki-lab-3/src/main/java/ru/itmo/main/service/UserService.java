package ru.itmo.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.main.dao.model.Role;
import ru.itmo.main.dao.model.UserEntity;
import ru.itmo.main.dao.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = userRepository.findByUserName(username);
        if(userDetails == null) throw new UsernameNotFoundException("User " + username + " not found!");
        return userDetails;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUserById(int id){
        return userRepository.getById(id);
    }

    public UserEntity saveUser(String username, String password, Role role){
        UserEntity user = new UserEntity();
        user.setUserName(username);
        user.setUserPassword(passwordEncoder.encode(password));
        user.setUserRole(role);

        userRepository.save(user);
        return user;
    }
}
