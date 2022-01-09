package com.book.springboot.web;

import com.book.springboot.config.auth.LoginUser;
import com.book.springboot.config.auth.dto.SessionUser;
import com.book.springboot.service.posts.PostsService;
import com.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private final PostsService postsService;
  private final HttpSession httpSession;

  @GetMapping("/")
  public String index(Model model, @LoginUser SessionUser user) {
    model.addAttribute("posts", postsService.findAllDesc());

    // CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser를 저장
    // User user = (User) httpSession.getAttribute("user");

    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    return "index";
  }

  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }

  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model model) {
    PostsResponseDto findDto = postsService.findById(id);
    model.addAttribute("posts", findDto);

    return "posts-update";
  }

  @DeleteMapping("/posts/delete/{id}")
  public Long postsDelete(@PathVariable Long id) {
    postsService.delete(id);
    return id;
  }

}
