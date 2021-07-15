package io.shirohoo.docs.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.shirohoo.docs.domain.UserDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
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
        Mono<String> request = Mono.just(mapper.writeValueAsString(UserDto.builder()
                                                                          .name("홍길동")
                                                                          .email("hong@email.com")
                                                                          .phoneNumber("01012341234")
                                                                          .build())
                                        );

        String expected = mapper.writeValueAsString(UserDto.builder()
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
                                                                       .requestFields(
                                                                               PayloadDocumentation.fieldWithPath("id").description("식별자"),
                                                                               PayloadDocumentation.fieldWithPath("name").description("이름"),
                                                                               PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                                                               PayloadDocumentation.fieldWithPath("phoneNumber").description("전화번호")
                                                                                     )
                                                                       .requestSchema(schema("UserDto"))
                                                                       .responseFields(
                                                                               PayloadDocumentation.fieldWithPath("id").description("식별자"),
                                                                               PayloadDocumentation.fieldWithPath("name").description("이름"),
                                                                               PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                                                               PayloadDocumentation.fieldWithPath("phoneNumber").description("전화번호")
                                                                                      )
                                                                       .responseSchema(schema("UserDto"))
                                                                       .build()
                                              )));
    }

    @Test
    @Order(2)
    void read() throws Exception {
        // given
        String expected = mapper.writeValueAsString(UserDto.builder()
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
                                                                       .pathParameters(
                                                                               parameterWithName("id").description("식별자")
                                                                                      )
                                                                       .requestSchema(schema("UserDto"))
                                                                       .responseFields(
                                                                               PayloadDocumentation.fieldWithPath("id").description("식별자"),
                                                                               PayloadDocumentation.fieldWithPath("name").description("이름"),
                                                                               PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                                                               PayloadDocumentation.fieldWithPath("phoneNumber").description("전화번호")
                                                                                      )
                                                                       .responseSchema(schema("UserDto"))
                                                                       .build()
                                              )));
    }

    @Test
    @Order(3)
    void update() throws Exception {
        // given
        Mono<String> request = Mono.just(mapper.writeValueAsString(UserDto.builder()
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
                                                                       .requestFields(
                                                                               PayloadDocumentation.fieldWithPath("id").description("식별자"),
                                                                               PayloadDocumentation.fieldWithPath("name").description("이름"),
                                                                               PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                                                               PayloadDocumentation.fieldWithPath("phoneNumber").description("전화번호")
                                                                                     )
                                                                       .requestSchema(schema("UserDto"))
                                                                       .responseFields(
                                                                               PayloadDocumentation.fieldWithPath("id").description("식별자"),
                                                                               PayloadDocumentation.fieldWithPath("name").description("이름"),
                                                                               PayloadDocumentation.fieldWithPath("email").description("이메일"),
                                                                               PayloadDocumentation.fieldWithPath("phoneNumber").description("전화번호")
                                                                                      )
                                                                       .responseSchema(schema("UserDto"))
                                                                       .build()
                                              )));
    }

    @Test
    @Order(4)
    void delete() throws Exception {
        // given
        String expected = "done";

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.delete()
                                                           .uri("/api/v1/user/{id}", 1)
                                                           .exchange();

        // then
        exchange.expectStatus().isOk()
                .expectBody(String.class).isEqualTo(expected)
                .consumeWith(document("delete",
                                      preprocessRequest(prettyPrint()),
                                      preprocessResponse(prettyPrint()),
                                      resource(
                                              ResourceSnippetParameters.builder()
                                                                       .tag("User")
                                                                       .summary("사용자 정보 삭제")
                                                                       .description("사용자 정보를 삭제한다")
                                                                       .pathParameters(
                                                                               parameterWithName("id").description("식별자")
                                                                                      )
                                                                       .requestSchema(schema("UserDto"))
                                                                       .responseSchema(schema("String"))
                                                                       .build()
                                              )));
    }
}
