package atdd.station.docs;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class FieldsSnippet {
    FieldDescriptor getUserFieldSnippet() {
        return writeNumberSnippetDescription("user", "The favorite`s user");
    }

    private RestDocumentationResultHandler writeResultDocument(String identifier
            , RequestFieldsSnippet requestFieldsSnippet, ResponseFieldsSnippet responseFieldsSnippet) {
        return document(identifier, requestFieldsSnippet, responseFieldsSnippet);
    }

    RestDocumentationResultHandler writeResultDocument(String identifier , RequestFieldsSnippet requestFieldsSnippet) {
        return document(identifier, requestFieldsSnippet);
    }

    private FieldDescriptor writeFieldSnippetDescription(String keyName, JsonFieldType type, String description) {
        return fieldWithPath(keyName).type(type).description(description);
    }

    FieldDescriptor writeStringSnippetDescription(String key, String description) {
        return fieldWithPath(key).type(JsonFieldType.STRING).description(description);
    }

    FieldDescriptor writeNumberSnippetDescription(String key, String description) {
        return fieldWithPath(key).type(JsonFieldType.NUMBER).description(description);
    }

    RequestHeadersSnippet getAuthorizationHeaderSnippet(String description) {
        return requestHeaders(headerWithName(HttpHeaders.AUTHORIZATION).description(description));
    }
}
