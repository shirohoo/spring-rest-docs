package io.github.shirohoo.springrestdocs.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import io.github.shirohoo.springrestdocs.api.ApiController.User;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ApiControllerTest extends AbstractControllerTests {

    @Test
    void get() throws Exception {
        // ...given
        String request = "010-1234-5678";
        String response = objectMapper.writeValueAsString(
            new User("user1", 11, "010-1234-5678", LocalDate.of(2000, 1, 1))
        );

        // ...when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/?phoneNumber=" + request));

        // ...then
        actions.andExpect(status().isOk())
            .andExpect(content().json(response))
            .andDo(document("user-find",
                documentRequest(),
                documentResponse(),
                requestParameters(
                    parameterWithName("phoneNumber").description("휴대폰 번호")
                ),
                responseFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                    fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호"),
                    fieldWithPath("birthDay").type(JsonFieldType.STRING).description("생일")
                )
            ));
    }

    @Test
    void post() throws Exception {
        // ...given
        String request = objectMapper.writeValueAsString(
            new User("user4", 44, "010-5678-5678", LocalDate.of(2000, 1, 1))
        );

        // ...when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        );

        // ...then
        actions.andExpect(status().isCreated())
            .andExpect(content().json(request))
            .andDo(document("user-create",
                documentRequest(),
                documentResponse(),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                    fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호"),
                    fieldWithPath("birthDay").type(JsonFieldType.STRING).description("생일")
                ),
                responseFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                    fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호"),
                    fieldWithPath("birthDay").type(JsonFieldType.STRING).description("생일")
                )
            ));
    }

}
