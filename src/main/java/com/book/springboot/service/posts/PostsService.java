package com.book.springboot.service.posts;


import com.book.springboot.domain.posts.Posts;
import com.book.springboot.domain.posts.PostsRepository;
import com.book.springboot.web.dto.PostsListResponseDto;
import com.book.springboot.web.dto.PostsResponseDto;
import com.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
public class PostsService {

  private final PostsRepository postsRepository;

  @Transactional
  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsSaveRequestDto requestDto) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

    posts.update(requestDto.getTitle(), requestDto.getContent());
    return id;
  }

  public PostsResponseDto findById(Long id) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

    return new PostsResponseDto(posts);
  }

  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository.findAllDesc().stream()
        .map(PostsListResponseDto::new)
        .collect(toList());
  }

  public void delete(Long id) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

    postsRepository.delete(posts);
  }
}
