package com.green.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.dto.CommentDto;
import com.green.repository.ArticleRepository;
import com.green.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ArticleRepository articleRepository;

	
	// 1. 댓글 조회
	public List<CommentDto> comments(Long articleId) {
	
		/*
		// 1. 댓글을 DB 에서 조회 → Entity 에 담는다
		// 댓글 DB 에서 조회 시 articleId 로 조회함
		// Comments = Entity table → db 와 같은 구조
		List<Comments> commentList = commentRepository.findByArticleId(articleId);
		
		// 2. Entity 에 담겨있는 조회된 댓글 → Dto 에 담음 : Dto 로 변환
		// List<CommentDto> dtos = commentList
		// dtos : CommentDto 타입 ↔ commentList : Comments 타입 → 타입이 달라서 바로 못담음 → new ArrayList<>() 작업 필요
		List<CommentDto> dtos = new ArrayList<CommentDto>();
		for (int i = 0; i < commentList.size(); i++) {
			Comments c = commentList.get(i);
			CommentDto dto = CommentDto.createCommentDto(c);
			dtos.add(dto);
		}
		
		// 3. 결과를 반환
		
		return dtos;
		*/
		
		// 위 1~3번 한줄로 줄이는 문법
		return commentRepository.findByArticleId( articleId )	
				.stream()	// stream 으로 전환
				.map( comment -> CommentDto.createCommentDto( comment ) )		// map 으로 전환
				.collect( Collectors.toList() );
		// ArrayList 같은 배열을 한번에 처리하기 위한 문법
		// 문법이 독특하고 생소해서 아직까지 잘 안쓰이고 있지만, C# 에서 stream 문법을 먼저 도입했고 자바 1.8 버전에서 도입함
		
		// .map( comment -> CommentDto.createCommentDto( comment ) )  :  Comments = Entity → Dto 전환
		// .collect( Collectors.toList() );  :  ArrayList 전환
	}	
	
	
}
