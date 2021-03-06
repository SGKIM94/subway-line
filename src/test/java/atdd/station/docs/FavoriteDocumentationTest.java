package atdd.station.docs;

import atdd.AbstractDocumentationTest;
import atdd.path.application.dto.favorite.FavoriteCreateRequestView;
import atdd.station.controller.FavoriteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static atdd.path.fixture.FavoriteFixture.FAVORITE_ID;
import static atdd.path.fixture.FavoriteFixture.STATION_FAVORITE_CREATE_REQUEST_VIEW;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoriteController.class)
public class FavoriteDocumentationTest extends AbstractDocumentationTest {
    public static final String FAVORITE_SNIPPET_ID = "id";
    public static final String FAVORITE_BASE_URL = "/favorites";

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
        FavoriteCreateRequestView favorite = STATION_FAVORITE_CREATE_REQUEST_VIEW;

        given(favoriteService.save(any(), any())).willReturn(favorite);

       //when
        this.mockMvc.perform(post(FAVORITE_BASE_URL)
                .content(getContentWithView(favorite))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("favorites/create", getAuthorizationHeaderSnippet(),
                        getFavoriteUserFieldSnippet(), getFavoriteResponseFieldsSnippet()))
                .andDo(print());
    }

    @DisplayName("사용자에 추가되어있는 Favorite 를 가져오는 Docs 가 생성되는지")
    @Test
    void findByUser() throws Exception {
        //given
        given(favoriteService.save(any(), any())).willReturn(FAVORITE_CREATE_RESPONSE_VIEW);

        //when
        this.mockMvc.perform(get(FAVORITE_BASE_URL)
                .content(getContentWithView(STATION_FAVORITE_CREATE_REQUEST_VIEW))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("favorites/findByUser", getAuthorizationHeaderSnippet(),
                        getFavoriteUserFieldSnippet(), getFavoriteResponseFieldsSnippet()))
                .andDo(print());
    }

    @DisplayName("사용자에 추가되어있는 Favorite 를 삭제하는 Docs 가 생성되는지")
    @Test
    void deleteItem() throws Exception {
        //given
        given(favoriteService.save(any(), any())).willReturn(FAVORITE_CREATE_RESPONSE_VIEW);

        //when
        this.mockMvc.perform(delete(FAVORITE_BASE_URL + FAVORITE_ID)
                .content(getContentWithView(STATION_FAVORITE_CREATE_REQUEST_VIEW))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("favorites/deleteItem", getAuthorizationHeaderSnippet(),
                        getFavoriteRequestFieldsSnippet(),
                        responseFields(
                fieldsSnippet.writeNumberSnippetDescription(FAVORITE_SNIPPET_ID, "favorite id"),
                fieldsSnippet.getUserFieldSnippet(),
                fieldsSnippet.writeNumberSnippetDescription("item", "The favorite`s item")
        )))
                .andDo(print());
    }


    private RequestFieldsSnippet getFavoriteRequestFieldsSnippet() {
        return requestFields(
                fieldsSnippet.writeNumberSnippetDescription("id", "The favorite's id"),
                fieldsSnippet.writeStringSnippetDescription("type", "The favorite`s type")
        );
    }

    private ResponseFieldsSnippet getFavoriteResponseFieldsSnippet() {
        return responseFields(
                fieldsSnippet.writeNumberSnippetDescription(FAVORITE_SNIPPET_ID, "favorite id"),
                fieldsSnippet.getUserFieldSnippet(),
                fieldsSnippet.writeNumberSnippetDescription("item", "The favorite`s item")
        );
    }

    private RequestFieldsSnippet getFavoriteUserFieldSnippet() {
        return requestFields(
                fieldsSnippet.getUserFieldSnippet()
        );
    }

    private RequestHeadersSnippet getAuthorizationHeaderSnippet() {
        return fieldsSnippet.getAuthorizationHeaderSnippet("Bearer auth credentials");
    }
}
