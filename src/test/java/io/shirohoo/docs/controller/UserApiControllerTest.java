package io.shirohoo.docs.controller;


import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shirohoo.docs.domain.UserRequest;
import io.shirohoo.docs.model.User;
import io.shirohoo.docs.repository.UserRepository;
import io.shirohoo.docs.service.UserService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(UserApiController.class)
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class UserApiControllerTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void create() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .name("홍길동")
                .email("hong@email.com")
                .phoneNumber("01012341234")
                .build();

        User expected = User.builder()
                .id(1L)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .name("홍길동")
                .email("hong@email.com")
                .phoneNumber("01012341234")
                .build();

        BDDMockito.given(service.create(any())).willReturn(expected);

        String request = mapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/v1/user")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("User",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .description("사용자 정보를 생성한다")
                                        .summary("사용자 정보 생성")
                                        .requestFields(
                                                fieldWithPath("id").description("식별자"),
                                                fieldWithPath("name").description("이름"),
                                                fieldWithPath("email").description("이메일"),
                                                fieldWithPath("phoneNumber").description("전화번호")
                                        )
                                        .tag("User")
                                        .description("사용자 정보 생성 요청")
                                        .requestSchema(schema("UserRequest"))
                                        .responseFields(
                                                fieldWithPath("id").description("식별자"),
                                                fieldWithPath("name").description("이름"),
                                                fieldWithPath("email").description("이메일"),
                                                fieldWithPath("phoneNumber").description("전화번호"),
                                                fieldWithPath("createAt").description("등록일"),
                                                fieldWithPath("updateAt").description("수정일")
                                        )
                                        .tag("User")
                                        .description("사용자 정보 생성 응답")
                                        .responseSchema(schema("User"))
                                        .build()
                        )));
    }

    @Test
    void read() throws Exception {
        Optional<User> expected = Optional.ofNullable(User.builder()
                .id(1L)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .name("홍길동")
                .email("hong@email.com")
                .phoneNumber("01012341234")
                .build());

        BDDMockito.given(repository.findById(1L)).willReturn(expected);

        mockMvc.perform(get("/api/v1/user/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("User",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .description("사용자 정보를 조회한다")
                                        .summary("사용자 정보 조회")
                                        .requestParameters(
                                                parameterWithName("id").description("식별자")
                                        )
                                        .tag("User")
                                        .description("사용자 정보 조회 요청")
                                        .requestSchema(schema("UserRequest"))
                                        .responseFields(
                                                fieldWithPath("id").description("식별자"),
                                                fieldWithPath("name").description("이름"),
                                                fieldWithPath("email").description("이메일"),
                                                fieldWithPath("phoneNumber").description("전화번호"),
                                                fieldWithPath("createAt").description("등록일"),
                                                fieldWithPath("updateAt").description("수정일")
                                        )
                                        .tag("User")
                                        .description("사용자 정보 조회 응답")
                                        .responseSchema(schema("User"))
                                        .build()
                        )));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}