package com.green.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 실제 database 의 table 구조를 만든다 : Create Table
// import : jakarta 로 -> import jakarta.persistence.
// persistence : 영속성 -> 영구적으로 보관, pk(primary key) 를 영구적으로 만들어라
// 그냥 참고) @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_SEQ_GENERATOR")
@Entity
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator( name="ARTICLE_SEQ_GENERATOR",
					sequenceName   = "ARTICLE_SEQ",
					initialValue   = 1,			// 초기값
					allocationSize = 1)		// 증가치
public class Article {

	// primary key : @Id
	// 값을 자동으로 채워라 : @GeneratedValue
	// @GeneratedValue() 에 ctrl+space : strategy= + 선택가능
	@Id
	// @GeneratedValue		// 번호 자동 증가
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "ARTICLE_SEQ_GENERATOR" )
	// create sequence article_seq start with 1 increment by 50
	private Long id;
	@Column
	private String title;
	@Column
	private String content;
	
	public Article(Long id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	public void patch(Article article) {
	      if(article.title   != null)
	         this.title   = article.title;
	      if(article.content != null)
	         this.content = article.content;
	      
	   }
	
	
}

