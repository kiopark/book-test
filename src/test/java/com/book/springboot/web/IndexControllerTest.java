package com.book.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class IndexControllerTest {

  @Autowired
  MockMvc mvc;

  @Test
  void 메인페이지_로딩() throws Exception {
    // when
    ResultActions perform = mvc.perform(get("/"));
    String body = perform.andReturn().getResponse().getContentAsString();

    // then
    Assertions.assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
  }

}