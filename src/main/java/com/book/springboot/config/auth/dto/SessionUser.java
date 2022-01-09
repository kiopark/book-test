package com.book.springboot.config.auth.dto;

import com.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 인증된 사용자 정보를 담는 객체
 * 엔티티 Class 는 언제 다른 Entity 와 관계가 형성 될 지 모르기 떄문에
 * 성능 이슈 / 부수 효과를 방지 하기 위해 직렬화 기능을 가진 세션 Dto 를 생성
 */
@Getter
public class SessionUser implements Serializable {

  private final static long serialVersionUID = 1L;

  private String name;
  private String email;
  private String picture;

  public SessionUser(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.picture = user.getPicture();
  }
}
