package atdd;

import atdd.config.WebMvcConfig;
import atdd.station.WebMvcTestConfig;
import atdd.user.web.LoginInterceptor;
import atdd.user.web.LoginUserMethodArgumentResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(WebMvcTestConfig.class)
public class AbstractDocumentationTest {
    @MockBean
    public LoginInterceptor loginInterceptor;
    @MockBean
    public LoginUserMethodArgumentResolver methodArgumentResolver;
    @MockBean
    public WebMvcConfig webMvcConfig;
    private ObjectMapper objectMapper;

    public MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    protected String getContentWithView(Object view) throws JsonProcessingException {
        return objectMapper.writeValueAsString(view);
    }
}
