package com.green.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.dto.ArticleForm;
import com.green.entity.Article;
import com.green.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	//------------------------------------------
	// Service 는 Controller 가 아니기 때문에 RequestMapping, GetMapping 할 필요 없음
	
	// article 목록 조회
	public List<Article> index() {
		
		// DB 저장하기 전 작업할 코딩 넣는다
		return articleRepository.findAll();
	}

	//------------------------------------------	

	// article id 로 조회
	public Article show(Long id) {
		
		Article article = articleRepository.findById(id).orElse(null);
		
		return article;
	}

	//------------------------------------------
	
	public Article create(ArticleForm dto) {
		
		// 입력 data dto : {"id":1, "title":"새글", "content":"새글 내용"}
		Article article = dto.toEntity();
		
		// create 는 생성요청이고 번호 자동증가이므로 번호 필요없다
		// 그래서 id 가 존재하면 안된다
		if( article.getId() != null )	// id 가 존재하면
			return null;				// error 를 발생시켜라
		
		// Article saved = articleRepository.save(dto);	// error : ArticleForm is not Entity
		Article saved = articleRepository.save(article);
		
		return saved;
	}
	
	//------------------------------------------

	public Article update(Long id, ArticleForm dto) {	// 기존 {id=1,title=aaa,content=aaa} 수정 {id=1,title=eee,content=eee}
		
		// 1. DTO → Entity 로 변환 (저장하기 위함)
		Article article = dto.toEntity();	// 수정할 데이터(dto.toEntity())를 article 에 담음
		log.info( "id: {}, article: {}", id, article.toString() );	// {} : %처럼 쓰임 -> 파라미터
		
		// 2. target(기존글) 을 id 로 조회
		Article target = articleRepository.findById(id).orElse(null);	// .orElse(null) : 객체만 넘긴다(Optional 타입 type mismatch error)
		
		// 3. 잘못된 요청을 처리
		if ( target == null || id != article.getId() ) {
			log.info( "id: {}, article: {}", id, article.toString() );
			return null;	// 조회한 자료가 없거나 id 가 틀리면
		}
		
		// 4. 업데이트 및 정상응답(ok) 수행
		target.patch( article );	// target : Article Type -> @Entity 가서 method 추가
		Article updated = articleRepository.save( target );
		
		return updated;
	}

	//------------------------------------------

	public Article delete(Long id) {
		
		// 1. target(삭제할 글)을 id 로 조회하기
		Article target = articleRepository.findById(id).orElse(null);	// .orElse(null) : 객체만 넘긴다(Optional 타입 type mismatch error)
		
		// 2. 잘못된 요청을 처리
		if ( target == null ) {
			return null;	// 조회한 자료가 없거나 id 가 틀리면
		}
				
		// 3. 업데이트 및 정상응답(ok) 수행
		articleRepository.delete( target );		// return 할 필요 없음 -> Article type 의 변수에 안담아도됨
				
		return target;
		
	}

	//------------------------------------------

	public List<Article> createArticles(List<ArticleForm> dtos) {
		
		// 1. DTO 묶음을 Entity 묶음(= articleList)으로 변환
		List<Article> articleList = new ArrayList<Article>();
		for(ArticleForm dto : dtos) {
			
			Article article = dto.toEntity();
			articleList.add( article );
			
		}
		
		// 2. Entity 묶음(= articleList)을 db 에 저장한다
		for (int i = 0; i < articleList.size(); i++) {
			
			Article article = articleList.get(i);
			articleRepository.save( article );
			
		}
		
		// 3. 강제 에러 발생시키기
		articleRepository.findById(-1L).orElseThrow(	// 글번호 1부터 시작, 무조건 없음
				() -> new IllegalArgumentException("결재 실패!!!") );
	
		return articleList;
	}

	//------------------------------------------

	// transactional 기능을 활성화한다
	@Transactional		// 얘만 추가
	public List<Article> createArticles2(List<ArticleForm> dtos) {
		
		// 1. DTO 묶음을 Entity 묶음(= articleList)으로 변환
		List<Article> articleList = new ArrayList<Article>();
		for(ArticleForm dto : dtos) {
					
			Article article = dto.toEntity();
			articleList.add( article );
					
		}
				
		// 2. Entity 묶음(= articleList)을 db 에 저장한다
		for (int i = 0; i < articleList.size(); i++) {
			
			Article article = articleList.get(i);
			articleRepository.save( article );
					
		}
				
			// 3. 강제 에러 발생시키기
			articleRepository.findById(-1L).orElseThrow(	// 글번호 1부터 시작, 무조건 없음
					() -> new IllegalArgumentException("결재 실패!!!") );
		
			return articleList;
		
	}

}
