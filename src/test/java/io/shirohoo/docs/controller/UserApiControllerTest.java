package io.shirohoo.docs.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shirohoo.docs.domain.UserRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@ExtendWith(RestDocumentationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    WebApplicationContext context;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = MockMvcWebTestClient.bindToApplicationContext(context)
                                            .configureClient()
                                            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
                                            .build();
    }

    @Test
    @Order(1)
    @Rollback(false)
    void create() throws Exception {
        // given
        Mono<String> request = Mono.just(mapper.writeValueAsString(UserRequest.builder()
                                                                              .name("홍길동")
                                                                              .email("hong@email.com")
                                                                              .phoneNumber("01012341234")
                                                                              .build())
                                        );

        String expected = mapper.writeValueAsString(UserRequest.builder()
                                                               .id(1L)
                                                               .name("홍길동")
                                                               .email("hong@email.com")
                                                               .phoneNumber("01012341234")
                                                               .build());

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.post()
                                                           .uri("/api/v1/user")
                                                           .contentType(MediaType.APPLICATION_JSON)
                                                           .accept(MediaType.APPLICATION_JSON)
                                                           .body(BodyInserters.fromProducer(request, String.class))
                                                           .exchange();

        // then
        exchange.expectStatus().isOk()
                .expectBody().json(expected)
                .consumeWith(document("create",
                                      preprocessRequest(prettyPrint()),
                                      preprocessResponse(prettyPrint()),
                                      resource(
                                              ResourceSnippetParameters.builder()
                                                                       .tag("User")
                                                                       .summary("사용자 정보 생성")
                                                                       .description("사용자 정보를 생성한다")
                                                                       .requestSchema(schema("UserRequest"))
                                                                       .responseSchema(schema("UserResponse"))
                                                                       .requestFields(
                                                                               fieldWithPath("id").description("식별자"),
                                                                               fieldWithPath("name").description("이름"),
                                                                               fieldWithPath("email").description("이메일"),
                                                                               fieldWithPath("phoneNumber").description("전화번호")
                                                                                     )
                                                                       .responseFields(
                                                                               fieldWithPath("id").description("식별자"),
                                                                               fieldWithPath("name").description("이름"),
                                                                               fieldWithPath("email").description("이메일"),
                                                                               fieldWithPath("phoneNumber").description("전화번호"),
                                                                               fieldWithPath("createAt").description("등록일"),
                                                                               fieldWithPath("updateAt").description("수정일")
                                                                                      )
                                                                       .build()
                                              )));
    }

    @Test
    @Order(2)
    void read() throws Exception {
        // given
        String expected = mapper.writeValueAsString(UserRequest.builder()
                                                               .id(1L)
                                                               .name("홍길동")
                                                               .email("hong@email.com")
                                                               .phoneNumber("01012341234")
                                                               .build());

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get()
                                                           .uri("/api/v1/user/{id}", 1)
                                                           .accept(MediaType.APPLICATION_JSON)
                                                           .exchange();

        // then
        exchange.expectStatus().isOk()
                .expectBody().json(expected)
                .consumeWith(document("read",
                                      preprocessRequest(prettyPrint()),
                                      preprocessResponse(prettyPrint()),
                                      resource(
                                              ResourceSnippetParameters.builder()
                                                                       .tag("User")
                                                                       .summary("사용자 정보 조회")
                                                                       .description("사용자 정보를 조회한다")
                                                                       .requestSchema(null)
                                                                       .responseSchema(schema("UserResponse"))
                                                                       .pathParameters(
                                                                               parameterWithName("id").description("식별자")
                                                                                      )
                                                                       .responseFields(
                                                                               fieldWithPath("id").description("식별자"),
                                                                               fieldWithPath("name").description("이름"),
                                                                               fieldWithPath("email").description("이메일"),
                                                                               fieldWithPath("phoneNumber").description("전화번호"),
                                                                               fieldWithPath("createAt").description("등록일"),
                                                                               fieldWithPath("updateAt").description("수정일")
                                                                                      )
                                                                       .build()
                                              )));
    }

    @Test
    @Order(3)
    void update() throws Exception {
        // given
        Mono<String> request = Mono.just(mapper.writeValueAsString(UserRequest.builder()
                                                                              .id(1L)
                                                                              .name("아무개")
                                                                              .email("hong@email.com")
                                                                              .phoneNumber("01012341234")
                                                                              .build())
                                        );

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.put()
                                                           .uri("/api/v1/user")
                                                           .contentType(MediaType.APPLICATION_JSON)
                                                           .accept(MediaType.APPLICATION_JSON)
                                                           .body(BodyInserters.fromProducer(request, String.class))
                                                           .exchange();

        // then
        exchange.expectStatus().isOk()
                .expectBody().json(request.block())
                .consumeWith(document("update",
                                      preprocessRequest(prettyPrint()),
                                      preprocessResponse(prettyPrint()),
                                      resource(
                                              ResourceSnippetParameters.builder()
                                                                       .tag("User")
                                                                       .summary("사용자 정보 수정")
                                                                       .description("사용자 정보를 수정한다")
                                                                       .requestSchema(schema("UserRequest"))
                                                                       .responseSchema(schema("UserResponse"))
                                                                       .requestFields(
                                                                               fieldWithPath("id").description("식별자"),
                                                                               fieldWithPath("name").description("이름"),
                                                                               fieldWithPath("email").description("이메일"),
                                                                               fieldWithPath("phoneNumber").description("전화번호")
                                                                                     )
                                                                       .responseFields(
                                                                               fieldWithPath("id").description("식별자"),
                                                                               fieldWithPath("name").description("이름"),
                                                                               fieldWithPath("email").description("이메일"),
                                                                               fieldWithPath("phoneNumber").description("전화번호"),
                                                                               fieldWithPath("createAt").description("등록일"),
                                                                               fieldWithPath("updateAt").description("수정일")
                                                                                      )
                                                                       .build()
                                              )));
    }

    @Test
    @Order(4)
    void delete() throws Exception {
        // when
        WebTestClient.ResponseSpec exchange = webTestClient.delete()
                                                           .uri("/api/v1/user/{id}", 1)
                                                           .exchange();

        // then
        exchange.expectStatus().isOk()
                .expectBody()
                .consumeWith(document("delete",
                                      preprocessRequest(prettyPrint()),
                                      preprocessResponse(prettyPrint()),
                                      resource(
                                              ResourceSnippetParameters.builder()
                                                                       .tag("User")
                                                                       .summary("사용자 정보 삭제")
                                                                       .description("사용자 정보를 삭제한다")
                                                                       .requestSchema(null)
                                                                       .responseSchema(null)
                                                                       .pathParameters(
                                                                               parameterWithName("id").description("식별자")
                                                                                      )
                                                                       .build()
                                              )));
    }
}
