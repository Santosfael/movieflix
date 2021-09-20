package com.rafaelrocha.backend.services;

import com.rafaelrocha.backend.dto.RoleDTO;
import com.rafaelrocha.backend.dto.UserDTO;
import com.rafaelrocha.backend.dto.UserInsertDTO;
import com.rafaelrocha.backend.dto.UserUpdateDTO;
import com.rafaelrocha.backend.entities.Role;
import com.rafaelrocha.backend.entities.User;
import com.rafaelrocha.backend.repositories.RoleRepository;
import com.rafaelrocha.backend.repositories.UserRepository;
import com.rafaelrocha.backend.services.exceptions.DataBaseException;
import com.rafaelrocha.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelrocha.backend.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        Page<User> users = userRepository.findAll(pageRequest);

        return users.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> userObj = userRepository.findById(id);
        User user = userObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userInsertDTO) {
        User user = new User();
        copyDtoToEntity(userInsertDTO, user);
        user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
        user = userRepository.save(user);

        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userUpdateDTO) {
        try {
            User user = userRepository.getOne(id);
            copyDtoToEntity(userUpdateDTO, user);
            user = userRepository.save(user);

            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found "+id);
        }
    }

    private void copyDtoToEntity(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.getRoles().clear();

        for(RoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepository.getOne(roleDTO.getId());
            user.getRoles().add(role);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return user;
    }
}
