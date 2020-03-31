package atdd.station.docs;

import atdd.AbstractDocumentationTest;
import atdd.user.web.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.RequestHeadersSnippet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private atdd.path.application.FavoriteService favoriteService;
    private FieldsSnippet fieldsSnippet;

    @BeforeEach
    void setUp() {
        fieldsSnippet = new FieldsSnippet();
    }

    @DisplayName("즐겨찾기를 추가하는 Docs 이 생성되는지")
    @Test
    void create() throws Exception {
        //given
        atdd.path.domain.Favorite favorite = NEW_FAVORITAE;


        given(favoriteService.save(any())).willReturn(favorite);

       //when
        this.mockMvc.perform(post(USER_BASE_URL)
                .content(getContentWithView(createUserRequestView))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("users/create", getUserRequestFieldsSnippet(), getUserResponseFieldsSnippet()))
                .andDo(print());
    }


    private RequestHeadersSnippet getAuthorizationHeaderSnippet() {
        return fieldsSnippet.getAuthorizationHeaderSnippet("Bearer auth credentials");
    }
}
