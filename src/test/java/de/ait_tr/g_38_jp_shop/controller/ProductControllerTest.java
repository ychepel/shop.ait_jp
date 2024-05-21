package de.ait_tr.g_38_jp_shop.controller;

import de.ait_tr.g_38_jp_shop.domain.dto.ProductDto;
import de.ait_tr.g_38_jp_shop.domain.entity.Product;
import de.ait_tr.g_38_jp_shop.domain.entity.Role;
import de.ait_tr.g_38_jp_shop.domain.entity.User;
import de.ait_tr.g_38_jp_shop.repository.ProductRepository;
import de.ait_tr.g_38_jp_shop.repository.RoleRepository;
import de.ait_tr.g_38_jp_shop.repository.UserRepository;
import de.ait_tr.g_38_jp_shop.security.dto.TokenResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
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
    private final String ADMIN_EMAIL = "admin@test.com.ua";
    private final String USER_EMAIL = "user@test.com.ua";
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
            admin.setEmail(ADMIN_EMAIL);

            userRepository.save(admin);
        }

        if (user == null) {
            encoder = new BCryptPasswordEncoder();

            user = new User();
            user.setUsername(USER_NAME);
            user.setPassword(encoder.encode(PASSWORD));
            user.setRoles(Set.of(roleUser == null ? roleRepository.findByTitle(USER_ROLE_NAME) : roleUser));
            user.setEmail(USER_EMAIL);

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
    @Order(0)
    public void testSaveProduct() {
        headers = new HttpHeaders();
        headers.add("Authorization", adminAccessToken);
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);
        String url = URL_PREFIX + port + "/products";
        template = new TestRestTemplate();
        ResponseEntity<ProductDto> response = template.exchange(url, HttpMethod.POST, request, ProductDto.class);
        ProductDto responseDto = response.getBody();
        assertNotNull(responseDto, "Product saving response body is null");
        assertNotNull(responseDto.getProductId(), "No product ID were returned");
        assertEquals(testProduct.getTitle(), responseDto.getTitle(), "Response product name is invalid");
    }

    @Test
    @Order(1)
    public void testGetProduct() {
        Product storedProduct = productRepository.findByTitle(testProduct.getTitle());
        headers = new HttpHeaders();
        headers.add("Authorization", adminAccessToken);
        String url = URL_PREFIX + port + "/products/" + storedProduct.getId();
        template = new TestRestTemplate();
        ResponseEntity<ProductDto> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ProductDto.class);
        ProductDto responseProductDto = response.getBody();
        assertEquals(storedProduct.getId(), responseProductDto.getProductId(), "Returned product ID is incorrect");
        responseProductDto.setProductId(null);
        assertEquals(testProduct, responseProductDto, "Returned product is invalid");
    }

    @Test
    @Order(2)
    public void testDeleteProductById() {
        Product storedProduct = productRepository.findByTitle(testProduct.getTitle());
        headers = new HttpHeaders();
        headers.add("Authorization", adminAccessToken);
        String url = URL_PREFIX + port + "/products?id=" + storedProduct.getId();
        template = new TestRestTemplate();
        ResponseEntity<Object> response = template.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Deleting response code is incorrect");
    }

    @Test
    @Order(3)
    public void testRestoreProduct() {
        Product storedProduct = productRepository.findByTitle(testProduct.getTitle());
        headers = new HttpHeaders();
        headers.add("Authorization", adminAccessToken);
        String url = URL_PREFIX + port + "/products/restore?id=" + storedProduct.getId();
        template = new TestRestTemplate();
        ResponseEntity<Object> response = template.exchange(url, HttpMethod.PUT, new HttpEntity<>(headers), Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Restoring response code is incorrect");
    }

    @Test
    @Order(4)
    public void testDeleteProductByTitle() {
        Product storedProduct = productRepository.findByTitle(testProduct.getTitle());
        headers = new HttpHeaders();
        headers.add("Authorization", adminAccessToken);
        String url = URL_PREFIX + port + "/products?title=" + storedProduct.getTitle();
        template = new TestRestTemplate();
        ResponseEntity<ProductDto> response = template.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), ProductDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Deleting response code is incorrect");
    }

    //TODO: clean DB after all tests
}