package com.book.springboot.domain.posts;

import com.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스 ex) SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity {

  @Id // 해당 테이블의 PK
  @GeneratedValue // PK의 생성규칙 (스프링 부트 2.x 부터는 GenerationType.IDENTITY 옵션을 추가해야 auto_increment 가능)
  private Long id;

  @Column(length = 500, nullable = false) // 컬럼의 옵션
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  private String author;

  @Builder
  public Posts(String title, String content, String author) {
    this.title = title;
    this.content = content;
    this.author = author;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
