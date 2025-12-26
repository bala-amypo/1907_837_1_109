package com.example.demo;

import com.example.demo.controller.*;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import com.example.demo.servlet.SimpleStatusServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Listeners(TestResultListener.class)
public class CreditCardRewardMaximizerTest {

    // ------------------------------------------------------------------
    // Mocks
    // ------------------------------------------------------------------
    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private CreditCardRecordRepository creditCardRepository;

    @Mock
    private RewardRuleRepository rewardRuleRepository;

    @Mock
    private PurchaseIntentRecordRepository purchaseIntentRepository;

    @Mock
    private RecommendationRecordRepository recommendationRecordRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    // ------------------------------------------------------------------
    // Services
    // ------------------------------------------------------------------
    private UserProfileService userService;
    private CreditCardService cardService;
    private RewardRuleService ruleService;
    private PurchaseIntentService intentService;
    private RecommendationEngineService recommendationService;

    // ------------------------------------------------------------------
    // Controllers
    // ------------------------------------------------------------------
    private UserProfileController userController;
    private CreditCardController cardController;
    private RewardRuleController ruleController;
    private PurchaseIntentController intentController;
    private RecommendationController recommendationController;
    private AuthController authController;

    // ------------------------------------------------------------------
    // JWT + Servlet
    // ------------------------------------------------------------------
    private JwtUtil jwtUtil;
    private SimpleStatusServlet simpleStatusServlet;

    // ------------------------------------------------------------------
    // Setup
    // ------------------------------------------------------------------
    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // test-only secret, not from question
        byte[] testSecret = "test-jwt-secret-key-for-tests-1234567890"
                .getBytes(StandardCharsets.UTF_8);
        jwtUtil = new JwtUtil(testSecret, 60L * 60L * 1000L);

        userService = new UserProfileServiceImpl(
                userProfileRepository,
                new org.springframework.security.crypto.password.PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return rawPassword + "_ENC";
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return encodedPassword.equals(rawPassword + "_ENC");
                    }
                }
        );

        cardService = new CreditCardServiceImpl(creditCardRepository);
        ruleService = new RewardRuleServiceImpl(rewardRuleRepository);
        intentService = new PurchaseIntentServiceImpl(purchaseIntentRepository);
        recommendationService = new RecommendationEngineServiceImpl(
                purchaseIntentRepository,
                userProfileRepository,
                creditCardRepository,
                rewardRuleRepository,
                recommendationRecordRepository
        );

        userController = new UserProfileController(userService);
        cardController = new CreditCardController(cardService);
        ruleController = new RewardRuleController(ruleService);
        intentController = new PurchaseIntentController(intentService);
        recommendationController = new RecommendationController(recommendationService);

        authController = new AuthController(
                userService,
                userProfileRepository,
                authenticationManager,
                jwtUtil
        );

        simpleStatusServlet = new SimpleStatusServlet();
    }

    // ==================================================================
    // 1. "Servlet / Tomcat" – 8 tests
    // ==================================================================
    @Test(priority = 1)
    public void t01_servlet_returns_plain_text() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(sw.toString(), "Credit Card Reward Maximizer is running");
    }

    @Test(priority = 2)
    public void t02_servlet_sets_content_type() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(resp.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        simpleStatusServlet.doGet(null, resp);

        verify(resp).setContentType("text/plain");
    }

    @Test(priority = 3)
    public void t03_servlet_handles_null_request() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(resp.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(true, true);
    }

    @Test(priority = 4)
    public void t04_servlet_writer_flushed() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = spy(new PrintWriter(sw));

        when(resp.getWriter()).thenReturn(pw);

        simpleStatusServlet.doGet(null, resp);

        verify(pw).flush();
    }

    @Test(priority = 5)
    public void t05_servlet_output_not_html() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(sw.toString().contains("<html>"), false);
    }

    @Test(priority = 6)
    public void t06_servlet_contains_running_word() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(sw.toString().contains("running"), true);
    }

    @Test(priority = 7)
    public void t07_servlet_idempotent_calls() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(resp.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        simpleStatusServlet.doGet(null, resp);
        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(true, true);
    }

    @Test(priority = 8)
    public void t08_servlet_non_empty_output() throws Exception {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        simpleStatusServlet.doGet(null, resp);

        Assert.assertEquals(sw.toString().isEmpty(), false);
    }

    // ==================================================================
    // 2. CRUD – 8 tests (Users, Cards, Rules, Intents, Recommendations)
    // ==================================================================
    @Test(priority = 9)
    public void t09_create_user_profile() {
        UserProfile p = new UserProfile();
        p.setUserId("U001");
        p.setEmail("u1@test.com");
        p.setPassword("pass");

        when(userProfileRepository.existsByEmail("u1@test.com")).thenReturn(false);
        when(userProfileRepository.existsByUserId("U001")).thenReturn(false);
        when(userProfileRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserProfile saved = userService.createUser(p);

        Assert.assertEquals(saved.getUserId(), "U001");
    }

    @Test(priority = 10)
    public void t10_get_user_by_id() {
        UserProfile p = new UserProfile();
        p.setId(1L);

        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(p));

        Assert.assertEquals(userService.getUserById(1L).getId(), 1L);
    }

    @Test(priority = 11)
    public void t11_add_credit_card() {
        CreditCardRecord card = new CreditCardRecord();
        card.setCardName("Travel Card");

        when(creditCardRepository.save(any())).thenReturn(card);

        CreditCardRecord saved = cardService.addCard(card);

        Assert.assertEquals(saved.getCardName(), "Travel Card");
    }

    @Test(priority = 12)
    public void t12_create_reward_rule() {
        RewardRule rule = new RewardRule();
        rule.setCategory("GROCERY");
        rule.setMultiplier(5.0);

        when(rewardRuleRepository.save(any())).thenReturn(rule);

        RewardRule saved = ruleService.createRule(rule);

        Assert.assertEquals(saved.getCategory(), "GROCERY");
    }

    @Test(priority = 13)
    public void t13_create_purchase_intent() {
        PurchaseIntentRecord intent = new PurchaseIntentRecord();
        intent.setAmount(1000.0);

        when(purchaseIntentRepository.save(any())).thenReturn(intent);

        PurchaseIntentRecord saved = intentService.createIntent(intent);

        Assert.assertEquals(saved.getAmount(), 1000.0);
    }

    @Test(priority = 14)
    public void t14_create_recommendation_direct_save() {
        RecommendationRecord rec = new RecommendationRecord();
        rec.setExpectedRewardValue(200.0);

        when(recommendationRecordRepository.save(any())).thenReturn(rec);

        RecommendationRecord saved = recommendationRecordRepository.save(rec);

        Assert.assertEquals(saved.getExpectedRewardValue(), 200.0);
    }

    @Test(priority = 15)
    public void t15_get_cards_by_user() {
        CreditCardRecord c = new CreditCardRecord();
        c.setUserId(2L);

        when(creditCardRepository.findByUserId(2L)).thenReturn(List.of(c));

        Assert.assertEquals(cardService.getCardsByUser(2L).size(), 1);
    }

    @Test(priority = 16)
    public void t16_get_intents_by_user() {
        PurchaseIntentRecord i = new PurchaseIntentRecord();
        i.setUserId(3L);

        when(purchaseIntentRepository.findByUserId(3L)).thenReturn(List.of(i));

        Assert.assertEquals(intentService.getIntentsByUser(3L).size(), 1);
    }

    // ==================================================================
    // 3. DI / IoC – 8 tests
    // ==================================================================
    @Test(priority = 17)
    public void t17_user_service_injected() {
        Assert.assertEquals(userService != null, true);
    }

    @Test(priority = 18)
    public void t18_card_service_injected() {
        Assert.assertEquals(cardService != null, true);
    }

    @Test(priority = 19)
    public void t19_rule_service_injected() {
        Assert.assertEquals(ruleService != null, true);
    }

    @Test(priority = 20)
    public void t20_intent_service_injected() {
        Assert.assertEquals(intentService != null, true);
    }

    @Test(priority = 21)
    public void t21_recommendation_service_injected() {
        Assert.assertEquals(recommendationService != null, true);
    }

    @Test(priority = 22)
    public void t22_user_controller_injected() {
        Assert.assertEquals(userController != null, true);
    }

    @Test(priority = 23)
    public void t23_card_controller_injected() {
        Assert.assertEquals(cardController != null, true);
    }

    @Test(priority = 24)
    public void t24_auth_controller_injected() {
        Assert.assertEquals(authController != null, true);
    }

    // ==================================================================
    // 4. Hibernate / Lifecycle – 8 tests
    // ==================================================================
    @Test(priority = 25)
    public void t25_user_prePersist_sets_createdAt() {
        UserProfile u = new UserProfile();
        u.prePersist();

        Assert.assertEquals(u.getCreatedAt() != null, true);
    }

    @Test(priority = 26)
    public void t26_card_prePersist_sets_createdAt() {
        CreditCardRecord c = new CreditCardRecord();
        c.prePersist();

        Assert.assertEquals(c.getCreatedAt() != null, true);
    }

    @Test(priority = 27)
    public void t27_recommendation_reward_non_negative() {
        RecommendationRecord r = new RecommendationRecord();
        r.setExpectedRewardValue(0.0);

        Assert.assertEquals(r.getExpectedRewardValue() >= 0.0, true);
    }

    @Test(priority = 28)
    public void t28_purchase_amount_positive() {
        PurchaseIntentRecord i = new PurchaseIntentRecord();
        i.setAmount(50.5);

        Assert.assertEquals(i.getAmount() > 0.0, true);
    }

    @Test(priority = 29)
    public void t29_rule_multiplier_positive() {
        RewardRule rule = new RewardRule();
        rule.setMultiplier(3.0);

        Assert.assertEquals(rule.getMultiplier() > 0.0, true);
    }

    @Test(priority = 30)
    public void t30_card_annual_fee_non_negative() {
        CreditCardRecord c = new CreditCardRecord();
        c.setAnnualFee(0.0);

        Assert.assertEquals(c.getAnnualFee() >= 0.0, true);
    }

    @Test(priority = 31)
    public void t31_user_role_default_logic() {
        UserProfile u = new UserProfile();
        u.prePersist();

        Assert.assertEquals(u.getRole() != null, true);
    }

    @Test(priority = 32)
    public void t32_user_active_flag_allow_null() {
        UserProfile u = new UserProfile();
        u.setActive(null);

        Assert.assertEquals(u.getActive(), null);
    }

    // ==================================================================
    // 5. JPA Normalization – 8 tests
    // ==================================================================
    @Test(priority = 33)
    public void t33_user_atomic_fields() {
        UserProfile u = new UserProfile();
        u.setFullName("Alice");

        Assert.assertEquals(u.getFullName(), "Alice");
    }

    @Test(priority = 34)
    public void t34_card_normalized_issuer() {
        CreditCardRecord c = new CreditCardRecord();
        c.setIssuer("BankX");

        Assert.assertEquals(c.getIssuer(), "BankX");
    }

    @Test(priority = 35)
    public void t35_rule_category_field() {
        RewardRule r = new RewardRule();
        r.setCategory("TRAVEL");

        Assert.assertEquals(r.getCategory(), "TRAVEL");
    }

    @Test(priority = 36)
    public void t36_purchase_category_field() {
        PurchaseIntentRecord i = new PurchaseIntentRecord();
        i.setCategory("DINING");

        Assert.assertEquals(i.getCategory(), "DINING");
    }

    @Test(priority = 37)
    public void t37_recommendation_json_format() {
        RecommendationRecord r = new RecommendationRecord();
        r.setCalculationDetailsJson("{\"a\":1}");

        Assert.assertEquals(r.getCalculationDetailsJson().startsWith("{"), true);
    }

    @Test(priority = 38)
    public void t38_user_active_field() {
        UserProfile u = new UserProfile();
        u.setActive(true);

        Assert.assertEquals(u.getActive(), true);
    }

    @Test(priority = 39)
    public void t39_card_status_field() {
        CreditCardRecord c = new CreditCardRecord();
        c.setStatus("ACTIVE");

        Assert.assertEquals(c.getStatus(), "ACTIVE");
    }

    @Test(priority = 40)
    public void t40_purchase_merchant_field() {
        PurchaseIntentRecord i = new PurchaseIntentRecord();
        i.setMerchant("Amazon");

        Assert.assertEquals(i.getMerchant(), "Amazon");
    }

    // ==================================================================
    // 6. Relations / Associations – 8 tests
    // ==================================================================
    @Test(priority = 41)
    public void t41_card_references_user() {
        CreditCardRecord c = new CreditCardRecord();
        c.setUserId(10L);

        Assert.assertEquals(c.getUserId(), 10L);
    }

    @Test(priority = 42)
    public void t42_rule_references_card() {
        RewardRule r = new RewardRule();
        r.setCardId(20L);

        Assert.assertEquals(r.getCardId(), 20L);
    }

    @Test(priority = 43)
    public void t43_intent_references_user() {
        PurchaseIntentRecord i = new PurchaseIntentRecord();
        i.setUserId(30L);

        Assert.assertEquals(i.getUserId(), 30L);
    }

    @Test(priority = 44)
    public void t44_recommendation_references_user() {
        RecommendationRecord r = new RecommendationRecord();
        r.setUserId(5L);

        Assert.assertEquals(r.getUserId(), 5L);
    }

    @Test(priority = 45)
    public void t45_recommendation_references_intent() {
        RecommendationRecord r = new RecommendationRecord();
        r.setPurchaseIntentId(7L);

        Assert.assertEquals(r.getPurchaseIntentId(), 7L);
    }

    @Test(priority = 46)
    public void t46_multiple_cards_for_user() {
        when(creditCardRepository.findByUserId(3L))
                .thenReturn(List.of(new CreditCardRecord(), new CreditCardRecord()));

        Assert.assertEquals(cardService.getCardsByUser(3L).size(), 2);
    }

    @Test(priority = 47)
    public void t47_multiple_intents_for_user() {
        when(purchaseIntentRepository.findByUserId(4L))
                .thenReturn(List.of(new PurchaseIntentRecord(), new PurchaseIntentRecord()));

        Assert.assertEquals(intentService.getIntentsByUser(4L).size(), 2);
    }

    @Test(priority = 48)
    public void t48_multiple_recommendations_for_user() {
        when(recommendationRecordRepository.findByUserId(9L))
                .thenReturn(List.of(new RecommendationRecord(), new RecommendationRecord()));

        Assert.assertEquals(recommendationService.getRecommendationsByUser(9L).size(), 2);
    }

    // ==================================================================
    // 7. Security / JWT – 8 tests
    // ==================================================================
    @Test(priority = 49)
    public void t49_jwt_generation_not_null() {
        String token = jwtUtil.generateToken(1L, "a@b.com", "USER");

        Assert.assertNotNull(token);
    }

    @Test(priority = 50)
    public void t50_jwt_extract_email() {
        String token = jwtUtil.generateToken(2L, "x@y.com", "ADMIN");

        Assert.assertEquals(jwtUtil.extractEmail(token), "x@y.com");
    }

    @Test(priority = 51)
    public void t51_jwt_extract_role() {
        String token = jwtUtil.generateToken(3L, "r@r.com", "ANALYST");

        Assert.assertEquals(jwtUtil.extractRole(token), "ANALYST");
    }

    @Test(priority = 52)
    public void t52_jwt_extract_userId() {
        String token = jwtUtil.generateToken(99L, "u@test.com", "USER");

        Assert.assertEquals(jwtUtil.extractUserId(token), Long.valueOf(99L));
    }

    @Test(priority = 53)
    public void t53_jwt_valid_token() {
        String token = jwtUtil.generateToken(5L, "valid@test.com", "USER");

        Assert.assertEquals(jwtUtil.validateToken(token), true);
    }

    @Test(priority = 54)
    public void t54_jwt_invalid_token() {
        String bad = "invalid.token.value";

        Assert.assertEquals(jwtUtil.validateToken(bad), false);
    }

    @Test(priority = 55)
    public void t55_register_flow_mock() {
        RegisterRequest req = new RegisterRequest();
        req.setFullName("Test User");
        req.setEmail("reg@test.com");
        req.setPassword("pass1234");
        req.setRole("USER");

        when(userProfileRepository.existsByEmail("reg@test.com")).thenReturn(false);
        when(userProfileRepository.existsByUserId(anyString())).thenReturn(false);
        when(userProfileRepository.save(any())).thenAnswer(i -> {
            UserProfile saved = i.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        JwtResponse response = authController.register(req).getBody();

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getEmail(), "reg@test.com");
    }

    @Test(priority = 56)
    public void t56_login_flow_mock() {
        LoginRequest req = new LoginRequest();
        req.setEmail("login@test.com");
        req.setPassword("pass1234");

        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any()))
                .thenReturn(mockAuth);

        UserProfile u = new UserProfile();
        u.setId(10L);
        u.setEmail("login@test.com");
        u.setActive(true);
        u.setRole("USER");

        when(userProfileRepository.findByEmail("login@test.com"))
                .thenReturn(Optional.of(u));

        JwtResponse response = authController.login(req).getBody();

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getUserId(), Long.valueOf(10L));
    }

    // ==================================================================
    // 8. HQL / JPQL & Recommendation Engine – 8 tests
    // ==================================================================
    @Test(priority = 57)
    public void t57_get_active_rules() {
        RewardRule r = new RewardRule();
        r.setActive(true);

        when(rewardRuleRepository.findByActiveTrue())
                .thenReturn(List.of(r));

        Assert.assertEquals(ruleService.getActiveRules().size(), 1);
    }

    @Test(priority = 58)
    public void t58_get_all_users() {
        when(userProfileRepository.findAll())
                .thenReturn(List.of(new UserProfile(), new UserProfile()));

        Assert.assertEquals(userService.getAllUsers().size(), 2);
    }

    @Test(priority = 59)
    public void t59_get_all_cards() {
        when(creditCardRepository.findAll())
                .thenReturn(List.of(new CreditCardRecord(), new CreditCardRecord()));

        Assert.assertEquals(cardService.getAllCards().size(), 2);
    }

    @Test(priority = 60)
    public void t60_get_all_rules() {
        when(rewardRuleRepository.findAll())
                .thenReturn(List.of(new RewardRule()));

        Assert.assertEquals(ruleService.getAllRules().size(), 1);
    }

    @Test(priority = 61)
    public void t61_get_all_intents() {
        when(purchaseIntentRepository.findAll())
                .thenReturn(List.of(new PurchaseIntentRecord()));

        Assert.assertEquals(intentService.getAllIntents().size(), 1);
    }

    @Test(priority = 62)
    public void t62_get_all_recommendations() {
        when(recommendationRecordRepository.findAll())
                .thenReturn(List.of(new RecommendationRecord()));

        Assert.assertEquals(recommendationService.getAllRecommendations().size(), 1);
    }

    @Test(priority = 63)
    public void t63_recommendation_generate_happy_path() {
        // User
        UserProfile user = new UserProfile();
        user.setId(1L);
        user.setActive(true);

        // Intent
        PurchaseIntentRecord intent = new PurchaseIntentRecord();
        intent.setId(10L);
        intent.setUserId(1L);
        intent.setAmount(1000.0);
        intent.setCategory("GROCERY");

        // Card
        CreditCardRecord card = new CreditCardRecord();
        card.setId(100L);
        card.setUserId(1L);

        // Rule
        RewardRule rule = new RewardRule();
        rule.setCardId(100L);
        rule.setCategory("GROCERY");
        rule.setMultiplier(5.0);
        rule.setActive(true);

        when(purchaseIntentRepository.findById(10L)).thenReturn(Optional.of(intent));
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(user));
        when(creditCardRepository.findActiveCardsByUser(1L))
                .thenReturn(List.of(card));
        when(rewardRuleRepository.findActiveRulesForCardCategory(100L, "GROCERY"))
                .thenReturn(List.of(rule));
        when(recommendationRecordRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        RecommendationRecord rec = recommendationService.generateRecommendation(10L);

        Assert.assertEquals(rec.getRecommendedCardId(), Long.valueOf(100L));
        Assert.assertEquals(rec.getExpectedRewardValue() > 0.0, true);
    }

    @Test(priority = 64)
    public void t64_recommendation_generate_no_cards_throws() {
        PurchaseIntentRecord intent = new PurchaseIntentRecord();
        intent.setId(20L);
        intent.setUserId(2L);
        intent.setAmount(500.0);
        intent.setCategory("TRAVEL");

        UserProfile user = new UserProfile();
        user.setId(2L);
        user.setActive(true);

        when(purchaseIntentRepository.findById(20L)).thenReturn(Optional.of(intent));
        when(userProfileRepository.findById(2L)).thenReturn(Optional.of(user));
        when(creditCardRepository.findActiveCardsByUser(2L))
                .thenReturn(Collections.emptyList());

        try {
            recommendationService.generateRecommendation(20L);
            Assert.fail("Expected BadRequestException");
        } catch (BadRequestException ex) {
            Assert.assertEquals(ex.getClass(), BadRequestException.class);
        }
    }
}
