package io.shirohoo.docs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.shirohoo.docs.domain.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {
    @Autowired
    ObjectMapper mapper;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        webTestClient = MockMvcWebTestClient.bindToApplicationContext(context)
                                            .configureClient()
                                            .filter(documentationConfiguration(restDocumentation))
                                            .build();
    }

    @Test
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
                .consumeWith(document("user",
                                      preprocessRequest(prettyPrint()),
                                      preprocessResponse(prettyPrint()),
                                      requestFields(
                                              fieldWithPath("id").description("식별자").type(Long.class),
                                              fieldWithPath("name").description("이름").type(String.class),
                                              fieldWithPath("email").description("이메일").type(String.class),
                                              fieldWithPath("phoneNumber").description("전화번호").type(String.class)
                                                   ),
                                      responseFields(
                                              fieldWithPath("id").description("식별자").type(Long.class),
                                              fieldWithPath("name").description("이름").type(String.class),
                                              fieldWithPath("email").description("이메일").type(String.class),
                                              fieldWithPath("phoneNumber").description("전화번호").type(String.class),
                                              fieldWithPath("createAt").description("등록일").type(LocalDateTime.class),
                                              fieldWithPath("updateAt").description("수정일").type(LocalDateTime.class)
                                                    )
                                     ));
    }
}
