package atdd.station.docs;

import atdd.AbstractDocumentationTest;
import atdd.user.application.UserService;
import atdd.user.domain.User;
import atdd.user.web.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static atdd.TestConstant.*;
import static atdd.TestConstant.TEST_USER_EMAIL;
import static atdd.path.fixture.UserFixture.NEW_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserDocumentationTest extends AbstractDocumentationTest {
    public static final String TEST_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib29yd29uaWVAZW1haWwuY29tIiwiaWF0IjoxNTgxOTg1NjYzLCJleHAiOjE1ODE5ODkyNjN9.nL07LEhgTVzpUdQrOMbJq-oIce_idEdPS62hB2ou2hg";
    public static final String USER_BASE_URL = "/users";

    @MockBean
    private UserService userService;


    @DisplayName("유저를 등록하는 API 의 문서가 생성되는지")
    @Test
    void create() throws Exception {
        String inputJson = saveUser();

        this.mockMvc.perform(post(USER_BASE_URL)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(
                        document("users/create",
//                                getDocsLink(),
                                getRequestFieldsSnippet(),
                                getResponseFieldsSnippet()
                        )
                )
                .andDo(print());
    }

    @DisplayName("유저를 조회하는 API 의 문서가 생성되는지")
    @Test
    void me() throws Exception {
        String retrieveUserUrl = USER_BASE_URL + "/me";
        given(userService.findUserByEmail(anyString())).willReturn(NEW_USER);

        this.mockMvc.perform(get(retrieveUserUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TEST_USER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(TEST_USER_EMAIL))
                .andDo(
                        document("users/me",
//                                getDocsLink(),
                                requestHeaders(
                                        headerWithName("Authorization").description(
                                                "Bearer auth credentials")
                                ),
                                getResponseFieldsSnippet()
                        ))
                .andDo(print());
    }

    @DisplayName("로그인하는 API 의 문서가 생성되는지")
    @Test
    void login() throws Exception {
        String inputJson = saveUser();
        String loginUserUrl = USER_BASE_URL + "/login";

        this.mockMvc.perform(post(loginUserUrl)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("users/login",
//                                getDocsLink(),
                                getRequestFieldsSnippet(),
                                getResponseFieldsSnippet()
                        )
                )
                .andDo(print());
    }

    private LinksSnippet getDocsLink() {
        return links(linkWithRel("profile").description("Link to the profile resource"));
    }

    private ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("The user's id"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("The user's email address"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("The user's password"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("The user's name")
        );
    }

    private RequestFieldsSnippet getRequestFieldsSnippet() {
        return requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("The user's email address"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("The user's password"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("The user's name")
        );
    }

    private String saveUser() {
        User user = NEW_USER;
        given(userService.save(any())).willReturn(user);

        return "{\"email\":\"" + user.getEmail() + "\"," +
                "\"password\":\"" + user.getPassword() + "\"," +
                "\"name\":\"" + user.getName() + "\"}";
    }
}
