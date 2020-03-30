package atdd.station.docs;

import atdd.AbstractDocumentationTest;
import atdd.user.application.UserService;
import atdd.user.application.dto.CreateUserRequestView;
import atdd.user.application.dto.LoginUserRequestView;
import atdd.user.domain.User;
import atdd.user.web.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static atdd.security.BearerTokenExtractor.BEARER_TYPE;
import static atdd.user.fixture.TestConstant.FIRST_TEST_USER;
import static atdd.user.fixture.TestConstant.TEST_USER_EMAIL;
import static atdd.user.fixture.UserFixture.getCreateUserRequestView;
import static atdd.user.fixture.UserFixture.getLoginUserRequestView;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class FavoriteDocumentationTest extends AbstractDocumentationTest {
    public static final String TEST_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib29yd29uaWVAZW1haWwuY29tIiwiaWF0IjoxNTgxOTg1NjYzLCJleHAiOjE1ODE5ODkyNjN9.nL07LEhgTVzpUdQrOMbJq-oIce_idEdPS62hB2ou2hg";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_BASE_URL = "/users";
    public static final String EMAIL_PATH = "$.email";

    @MockBean
    private UserService userService;
    private FieldsSnippet fieldsSnippet;

    @BeforeEach
    void setUp() {
        fieldsSnippet = new FieldsSnippet();
    }

    @DisplayName("사용자를 생성하는 API 의 Rest Docs 를 생성 하는지")
    @Test
    void create() throws Exception {
        //given
        User user = FIRST_TEST_USER;
        CreateUserRequestView createUserRequestView = getCreateUserRequestView(user);

        given(userService.save(any())).willReturn(user);

       //when
        this.mockMvc.perform(post(USER_BASE_URL)
                .content(getContentWithView(createUserRequestView))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("users/create", getUserRequestFieldsSnippet(), getUserResponseFieldsSnippet()))
                .andDo(print());
    }

    @DisplayName("사용자를 조회하는 API 의 Rest Docs 를 생성 하는지")
    @Test
    void retrieveUser() throws Exception {
        //given
        given(userService.findUserByEmail(anyString())).willReturn(FIRST_TEST_USER);

        //when
        this.mockMvc.perform(get(USER_BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " + TEST_USER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMAIL_PATH).value(TEST_USER_EMAIL))
                .andDo(document("users/me", getAuthorizationHeaderSnippet(), getUserResponseFieldsSnippet()))
                .andDo(print());
    }

    @DisplayName("사용자 login API 의 Rest Docs 를 생성 하는지")
    @Test
    void login() throws Exception {
        //given
        User user = FIRST_TEST_USER;
        LoginUserRequestView loginUserRequestView = getLoginUserRequestView(user);

        given(userService.save(any())).willReturn(user);

        //when
        this.mockMvc.perform(post(USER_BASE_URL)
                .content(getContentWithView(loginUserRequestView))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(fieldsSnippet.writeResultDocument("users/login", getLoginRequestFieldsSnippet()))
                .andDo(print());
    }

    private RequestFieldsSnippet getLoginRequestFieldsSnippet() {
        return requestFields(
            fieldsSnippet.writeStringSnippetDescription(USER_EMAIL, "The user's email address"),
            fieldsSnippet.writeStringSnippetDescription(USER_PASSWORD, "The user's password"));
    }

    private RequestFieldsSnippet getUserRequestFieldsSnippet() {
        return requestFields(
                fieldsSnippet.writeNumberSnippetDescription(USER_ID, "The user's id"),
                fieldsSnippet.writeStringSnippetDescription(USER_EMAIL, "The user's email address"),
                fieldsSnippet.writeStringSnippetDescription(USER_PASSWORD, "The user's password"),
                fieldsSnippet.writeStringSnippetDescription(USER_NAME, "The user's name")
        );
    }

    private ResponseFieldsSnippet getUserResponseFieldsSnippet() {
        return responseFields(
                fieldsSnippet.writeNumberSnippetDescription(USER_ID, "The user's id"),
                fieldsSnippet.writeStringSnippetDescription(USER_EMAIL, "The user's email address"),
                fieldsSnippet.writeStringSnippetDescription(USER_PASSWORD, "The user's password"),
                fieldsSnippet.writeStringSnippetDescription(USER_NAME, "The user's name")
        );
    }

    private RequestHeadersSnippet getAuthorizationHeaderSnippet() {
        return fieldsSnippet.getAuthorizationHeaderSnippet("Bearer auth credentials");
    }
}
