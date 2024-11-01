CREATE TABLE IF NOT EXISTS member (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    social_id BIGINT NOT NULL,
    nickname VARCHAR(255),
    profile_image TEXT,
    solve_count INT DEFAULT 0 NOT NULL,
    score BIGINT DEFAULT 0 NOT NULL,
    baekjoon_id VARCHAR(255),
    member_status VARCHAR(20) DEFAULT 'YET' NOT NULL,
    refresh_token VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS board (
   board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
   member_id BIGINT NOT NULL,
   title VARCHAR(255) NOT NULL,
   content TEXT NOT NULL,
   board_type VARCHAR(255) NOT NULL,
   board_status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
   CONSTRAINT board__member_id_fk FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE TABLE IF NOT EXISTS board_like (
    board_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    board_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT board_like__board_id_fk FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
    CONSTRAINT board_like__member_id_fk FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment (
     comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     board_id BIGINT NOT NULL,
     member_id BIGINT NOT NULL,
     parent_id BIGINT NOT NULL,
     content VARCHAR(255) NOT NULL,
     comment_status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     CONSTRAINT comment__board_id_fk FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE,
     CONSTRAINT comment__member_id_fk FOREIGN KEY (member_id) REFERENCES member (member_id),
     CONSTRAINT comment__parent_id_fk FOREIGN KEY (parent_id) REFERENCES comment (comment_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment_like (
  comment_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  member_id BIGINT NOT NULL,
  comment_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT comment_like__comment_id_fk FOREIGN KEY (comment_id) REFERENCES comment (comment_id) ON DELETE CASCADE,
  CONSTRAINT comment_like__member_id_fk FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS solve_problem (
   solve_problem_id BIGINT AUTO_INCREMENT PRIMARY KEY,
   member_id BIGINT NOT NULL,
   level INT NOT NULL,
   solve_count BIGINT DEFAULT 0 NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
   CONSTRAINT solve_problem__member_id_fk FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);
