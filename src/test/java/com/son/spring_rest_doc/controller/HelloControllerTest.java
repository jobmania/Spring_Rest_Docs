package com.son.spring_rest_doc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.son.spring_rest_doc.User;
import org.apache.tomcat.util.http.parser.Host;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// SpringBootTest ->  모든 빈을 등록하여 진행하는 테스트 !

@WebMvcTest(HelloControllerTest.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ExtendWith(MockitoExtension.class) // @Mock
@ContextConfiguration(classes = HelloControllerTest.RestDocsConfiguration.class)
@SpringBootTest
class HelloControllerTest {
    private MockMvc mockMvc;

    @Autowired
    HelloController helloController;

    @Autowired
    protected RestDocumentationResultHandler restDocs; // write 재지정 !

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider
            ){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(restDocs)
                .build();
    }

    @DisplayName("기본 컨트롤러 로드 테스트")
    @Test
    void basicLoad() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk());
    }

    @DisplayName("기본 컨트롤러 로드 테스트")
    @Test
    void basicLoad2() throws Exception {
        User hello = helloController.hello();
        User hello2 = new User(1, "엄준식", "ppap@naver.com");
        Assertions.assertEquals(hello,hello2);

    }


    @DisplayName("테스트!")
    @Test
    void ppap() throws Exception{
        mockMvc.perform(
                get("/api/hello"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(

                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("번호"),
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                )
                        )
                );
    }


    @DisplayName("테스트222")
    @Test
    void ABC() throws Exception{
        mockMvc.perform(
                         get("/api/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("번호"),
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                                )
                        )
                );
    }


    @TestConfiguration
    static class RestDocsConfiguration {

        @Bean
        public RestDocumentationResultHandler write(){
            return MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}", // 클래스명 - 메소드명
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
            );
        }
    }

}