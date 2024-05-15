package de.ait_tr.g_38_jp_shop.service;

import de.ait_tr.g_38_jp_shop.domain.dto.UserDto;
import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.repository.UserRepository;
import de.ait_tr.g_38_jp_shop.service.interfaces.EmailService;
import de.ait_tr.g_38_jp_shop.service.interfaces.RoleService;
import de.ait_tr.g_38_jp_shop.service.interfaces.UserService;
import de.ait_tr.g_38_jp_shop.service.mapping.UserMappingService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;
    private RoleService roleService;
    private EmailService emailService;
    private UserMappingService mappingService;

    public UserServiceImpl(
            UserRepository repository,
            BCryptPasswordEncoder encoder,
            RoleService roleService,
            EmailService emailService,
            UserMappingService mappingService
    ) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
        this.mappingService = mappingService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserDto register(UserDto userDto) {
        User user = mappingService.mapDtoToEntity(userDto);
        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleService.getRoleUser()));
        user.setActive(false);

        repository.save(user);
        emailService.sendConfirmationEmail(user);
        return mappingService.mapEntityToDto(user);
    }
}
