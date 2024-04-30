-- oracle 전용 데이터
INSERT INTO article(id, title, content) VALUES(article_seq.nextval, '가가가가', '1111');
INSERT INTO article(id, title, content) VALUES(article_seq.nextval, '나나나나', '2222');
INSERT INTO article(id, title, content) VALUES(article_seq.nextval, '다다다다', '3333');

-- article 테이블에 데이터 추가
INSERT INTO article(id, title, content) VALUES(article_seq.nextval, '당신의 인생 영화는?', '댓글 고');
INSERT INTO article(id, title, content) VALUES(article_seq.nextval, '당신의 소울 푸드는?', '댓글 고고');
INSERT INTO article(id, title, content) VALUES(article_seq.nextval, '당신의 취미는?', '댓글 고고고');

-- 4번 게시글의 댓글 추가
INSERT INTO comments(id, article_id, nickname, body) VALUES(comments_seq.nextval, 4, 'Park', '굿 윌 헌팅');
INSERT INTO comments(id, article_id, nickname, body) VALUES(comments_seq.nextval, 4, 'Kim', '아이 엠 샘');
INSERT INTO comments(id, article_id, nickname, body) VALUES(comments_seq.nextval, 4, 'Choi', '쇼생크 탈출');

-- 5번 게시글의 댓글 추가
INSERT INTO comments(id,article_id, nickname, body) VALUES(comments_seq.nextval, 5, 'Park', '치킨');
INSERT INTO comments(id,article_id, nickname, body) VALUES(comments_seq.nextval, 5, 'Kim', '샤브샤브');
INSERT INTO comments(id,article_id, nickname, body) VALUES(comments_seq.nextval, 5, 'Choi', '초밥');

-- 6번 게시글의 댓글 추가
INSERT INTO comments(id,article_id, nickname, body) VALUES(comments_seq.nextval, 6, 'Park', '조깅');
INSERT INTO comments(id,article_id, nickname, body) VALUES(comments_seq.nextval, 6, 'Kim', '유튜브 시청');
INSERT INTO comments(id,article_id, nickname, body) VALUES(comments_seq.nextval, 6, 'Choi', '독서');
