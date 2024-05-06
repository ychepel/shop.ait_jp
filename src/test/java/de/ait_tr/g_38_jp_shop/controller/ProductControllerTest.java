package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Role;
import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.repository.ProductRepository;
import de.ait_tr.g_38_jp_shop.repository.RoleRepository;
import de.ait_tr.g_38_jp_shop.repository.UserRepository;
import de.ait_tr.g_38_jp_shop.security.dto.TokenResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    private TestRestTemplate template;
    private HttpHeaders headers;
    private ProductDto testProduct;

    private String adminAccessToken;
    private String userAccessToken;

    private final String PRODUCT_TITLE = "Super-nano-5D pencil";
    private final BigDecimal PRODUCT_PRICE = new BigDecimal(987);
    private final String ADMIN_NAME = "TAdmin";
    private final String USER_NAME = "TUser";
    private final String PASSWORD = "security";
    private final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
    private final String USER_ROLE_NAME = "ROLE_USER";

    private final String URL_PREFIX = "http://localhost:";
    private final String AUTH_RESOURCE_NAME = "/auth";
    private final String LOGIN_ENDPOINT = "/login";
    private final String ACCESS_ENDPOINT = "/access";
    private final String BEARER_PREFIX = "Bearer ";


    @BeforeEach
    public void setUp() {
        template = new TestRestTemplate();
        headers = new HttpHeaders();

        testProduct = new ProductDto();
        testProduct.setTitle(PRODUCT_TITLE);
        testProduct.setPrice(PRODUCT_PRICE);

        BCryptPasswordEncoder encoder;
        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(ADMIN_NAME);
        User user = userRepository.findByUsername(USER_NAME);

        if (admin == null) {
            encoder = new BCryptPasswordEncoder();
            roleAdmin = roleRepository.findByTitle(ADMIN_ROLE_NAME);
            roleUser = roleRepository.findByTitle(USER_ROLE_NAME);

            admin = new User();
            admin.setUsername(ADMIN_NAME);
            admin.setPassword(encoder.encode(PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userRepository.save(admin);
        }

        if (user == null) {
            encoder = new BCryptPasswordEncoder();

            user = new User();
            user.setUsername(USER_NAME);
            user.setPassword(encoder.encode(PASSWORD));
            user.setRoles(Set.of(roleUser == null ? roleRepository.findByTitle(USER_ROLE_NAME) : roleUser));

            userRepository.save(user);
        }

        admin.setPassword(PASSWORD);
        admin.setRoles(null);

        user.setPassword(PASSWORD);
        user.setRoles(null);

        String url = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;
        HttpEntity<User> request = new HttpEntity<>(admin, headers);

        ResponseEntity<TokenResponseDto> response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

        assertNotNull(response.getBody(), "Authorization response body is null");
        adminAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();

        request = new HttpEntity<>(user, headers);

        response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

        assertNotNull(response.getBody(), "Authorization response body is null");
        userAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();
    }

    @Test
    public void test() {

    }

}