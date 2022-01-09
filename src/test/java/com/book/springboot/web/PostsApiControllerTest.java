package com.book.springboot.web;

import com.book.springboot.domain.posts.Posts;
import com.book.springboot.domain.posts.PostsRepository;
import com.book.springboot.web.dto.PostsSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;

import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Rollback(value = false)
class PostsApiControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper objectMapper;
  @Autowired PostsRepository postsRepository;

  @AfterEach
  void tearDown() throws Exception {
    postsRepository.deleteAll();
  }

  @Test
  @WithMockUser(roles = "USER")
  void Posts_등록된다() throws Exception {
    // given
    String title = "title";
    String content = "content";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("author")
        .build();

    // when
    ResultActions resultActions = requestPostDto(requestDto);

    // then
    resultActions.andExpect(status().isOk());

    List<Posts> findAll = postsRepository.findAll();
    assertThat(findAll.get(0).getTitle()).isEqualTo(title);
    assertThat(findAll.get(0).getContent()).isEqualTo(content);
  }

  @Test
  @WithMockUser(roles = "USER")
  void Posts_수정된다() throws Exception {
    // given
    Posts savePosts = postsRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

    Long updateId = savePosts.getId();
    String expectedTitle = "updateTitle";
    String expectedContent = "updateContent";

    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    // when
    ResultActions resultActions = requestPutDto(updateId, requestDto);

    // then
    resultActions.andExpect(status().isOk());

    List<Posts> all = postsRepository.findAll();
    assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
    assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
  }

  private ResultActions requestPutDto(Long updateId, PostsSaveRequestDto requestDto) throws Exception {
    return mvc.perform(put("/api/v1/posts/{id}", updateId)
        .content(objectMapper.writeValueAsString(requestDto))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print());
  }

  private ResultActions requestPostDto(PostsSaveRequestDto requestDto) throws Exception {
    return mvc.perform(post("/api/v1/posts")
        .content(objectMapper.writeValueAsString(requestDto))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print());
  }

}