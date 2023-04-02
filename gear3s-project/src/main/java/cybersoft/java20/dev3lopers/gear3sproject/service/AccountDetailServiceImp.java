package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountDetailServiceImp implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        return AccountDetailsImp.build(user);
    }
}
