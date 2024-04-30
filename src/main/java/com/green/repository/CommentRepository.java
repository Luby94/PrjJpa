package com.green.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.green.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {	// Long : id 의 Type

	// @Query 어노테이션 통해서 findByArticleId() 함수 실행한다
	// Native Query Method - oracle 문법으로 작성된 쿼리를 입력하여 조회
	// nativeQuery = true : 오라클 전용함수
	// nativeQuery = false : JPA 함수
	// :[콜론] = 파라미터  →  :articleId (넘어온 파라미터)로 조회
	@Query(value="SELECT * FROM comments WHERE article_id = :articleId",
			nativeQuery=true)
	List<Comments> findByArticleId( Long articleId );
	
	// native query xml : 
	// orm.xml 에 sql 을 저장해서 findByNickname() 함수 호출
	// src/main/resources/META-INF/orm.xml  →  폴더와 파일 생성해야 한다
	List<Comments> findByNickname( String nickname );
	
}
