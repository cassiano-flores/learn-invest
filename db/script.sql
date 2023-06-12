-- DROP
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;


-- TABLES
CREATE TABLE usuario (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    current_icon_id BIGINT NOT NULL,
    create_in DATE NOT NULL,
    update_in DATE NOT NULL,
    league_points BIGINT NOT NULL,
    league_title VARCHAR(255) NOT NULL,
    coins BIGINT NOT NULL,
    xp BIGINT NOT NULL,
	active BOOLEAN NOT NULL,
	token VARCHAR(255)
);

CREATE TABLE permissao (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	name VARCHAR(100) NOT NULL,
	usuario_id BIGINT NOT NULL
);

CREATE TABLE course (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
	achievement_complete_course_id BIGINT NOT NULL,
	next_course_id BIGINT
);

CREATE TABLE activity (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	title VARCHAR(255) NOT NULL,
    lesson TEXT NOT NULL,
    course_id BIGINT NOT NULL,
	score BIGINT
);

CREATE TABLE question (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    question_text TEXT NOT NULL,
    alternative_a TEXT NOT NULL,
    alternative_b TEXT NOT NULL,
    alternative_c TEXT NOT NULL,
    alternative_d TEXT NOT NULL,
    answer VARCHAR(1) NOT NULL,
    activity_id BIGINT NOT NULL
);

CREATE TABLE achievement (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL,
	icon VARCHAR(255) NOT NULL
);

CREATE TABLE friend_request (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    usuario_sender_id BIGINT NOT NULL,
    usuario_receiver_id BIGINT NOT NULL
);

CREATE TABLE challenge (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    usuario_sender_score BIGINT NOT NULL,
    usuario_receiver_score BIGINT,
    challenged_in TIMESTAMP NOT NULL,
	is_complete BOOLEAN NOT NULL,
	usuario_challenge_id BIGINT
);

CREATE TABLE icon (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	name VARCHAR(255) NOT NULL,
	price BIGINT NOT NULL
);

CREATE TABLE usuario_icon (
    usuario_id BIGINT NOT NULL,
    icon_id BIGINT NOT NULL
);

CREATE TABLE friendship (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    usuario_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL
);

CREATE TABLE usuario_course (
    usuario_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL
);

CREATE TABLE usuario_activity (
    usuario_id BIGINT NOT NULL,
    activity_id BIGINT NOT NULL
);

CREATE TABLE usuario_achievement (
    usuario_id BIGINT NOT NULL,
    achievement_id BIGINT NOT NULL
);

CREATE TABLE usuario_challenge (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    usuario_sender_id BIGINT NOT NULL,
    usuario_receiver_id BIGINT NOT NULL,
    challenge_id BIGINT
);


-- ALTER TABLES
-- PKS
ALTER TABLE usuario ADD CONSTRAINT pk_usuario PRIMARY KEY (id);
ALTER TABLE permissao ADD CONSTRAINT pk_permissao PRIMARY KEY (id);
ALTER TABLE activity ADD CONSTRAINT pk_activity PRIMARY KEY (id);
ALTER TABLE course ADD CONSTRAINT pk_course PRIMARY KEY (id);
ALTER TABLE question ADD CONSTRAINT pk_question PRIMARY KEY (id);
ALTER TABLE achievement ADD CONSTRAINT pk_achievement PRIMARY KEY (id);
ALTER TABLE friend_request ADD CONSTRAINT pk_friend_request PRIMARY KEY (id);
ALTER TABLE challenge ADD CONSTRAINT pk_challenge PRIMARY KEY (id);
ALTER TABLE icon ADD CONSTRAINT pk_icon PRIMARY KEY (id);
ALTER TABLE usuario_icon ADD CONSTRAINT pk_usuario_icon PRIMARY KEY (usuario_id, icon_id);
ALTER TABLE friendship ADD CONSTRAINT pk_friendship PRIMARY KEY (id);
ALTER TABLE usuario_course ADD CONSTRAINT pk_usuario_course PRIMARY KEY (usuario_id, course_id);
ALTER TABLE usuario_activity ADD CONSTRAINT pk_usuario_activity PRIMARY KEY (usuario_id, activity_id);
ALTER TABLE usuario_achievement ADD CONSTRAINT pk_usuario_achievement PRIMARY KEY (usuario_id, achievement_id);
ALTER TABLE usuario_challenge ADD CONSTRAINT pk_usuario_challenge PRIMARY KEY (id);

--UKS
ALTER TABLE usuario ADD CONSTRAINT uk_usuario_email UNIQUE (email);
ALTER TABLE permissao ADD CONSTRAINT uk_permissao UNIQUE (name, usuario_id);
ALTER TABLE course ADD CONSTRAINT uk_course_name UNIQUE (name);
ALTER TABLE icon ADD CONSTRAINT uk_icon_name UNIQUE (name);
ALTER TABLE achievement ADD CONSTRAINT uk_achievement_name UNIQUE (name);
ALTER TABLE friend_request ADD CONSTRAINT uk_friend_request UNIQUE (usuario_sender_id, usuario_receiver_id);
ALTER TABLE usuario_challenge ADD CONSTRAINT uk_usuario_challenge UNIQUE (usuario_sender_id, usuario_receiver_id, challenge_id);

-- FKS usuario
ALTER TABLE usuario ADD CONSTRAINT fk_usuario_icon FOREIGN KEY (current_icon_id) REFERENCES icon(id);
ALTER TABLE usuario ADD CONSTRAINT ck_usuario_league_title CHECK (league_title IN ('GASTADOR', 'POUPADOR', 'INVESTIDOR', 'INVESTIDOR_MESTRE'));

-- FKS permissao
ALTER TABLE permissao ADD CONSTRAINT fk_permissao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE permissao ADD CONSTRAINT ck_permissao_name CHECK (name IN ('USER', 'ADMIN'));

-- FKS course
ALTER TABLE course ADD CONSTRAINT fk_course_achievement_complete_course 
	FOREIGN KEY (achievement_complete_course_id) REFERENCES achievement(id);
ALTER TABLE course ADD CONSTRAINT fk_course_next_course
	FOREIGN KEY (next_course_id) REFERENCES course(id);

-- FKS activity
ALTER TABLE activity ADD CONSTRAINT fk_activity_course FOREIGN KEY (course_id) REFERENCES course(id);

-- FKS (CK) question
ALTER TABLE question ADD CONSTRAINT fk_question_activity FOREIGN KEY (activity_id) REFERENCES activity(id);
ALTER TABLE question ADD CONSTRAINT ck_question CHECK (answer IN ('A', 'B', 'C', 'D'));

-- FKS friend_request
ALTER TABLE friend_request ADD CONSTRAINT fk_friend_request_sender FOREIGN KEY (usuario_sender_id) REFERENCES usuario(id);
ALTER TABLE friend_request ADD CONSTRAINT fk_friend_request_receiver FOREIGN KEY (usuario_receiver_id) REFERENCES usuario(id);

-- FKS challenge
ALTER TABLE challenge ADD CONSTRAINT fk_challenge_usuario_challenge FOREIGN KEY (usuario_challenge_id) REFERENCES usuario_challenge(id);

-- FKS usuario_icon
ALTER TABLE usuario_icon ADD CONSTRAINT fk_usuario_icon_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE usuario_icon ADD CONSTRAINT fk_usuario_icon_icon FOREIGN KEY (icon_id) REFERENCES icon(id);

-- FKS friendship
ALTER TABLE friendship ADD CONSTRAINT fk_friendship_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE friendship ADD CONSTRAINT fk_friendship_friend FOREIGN KEY (friend_id) REFERENCES usuario(id);

-- FKS usuario_course
ALTER TABLE usuario_course ADD CONSTRAINT fk_usuario_course_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE usuario_course ADD CONSTRAINT fk_usuario_course_course FOREIGN KEY (course_id) REFERENCES course(id);

-- FKS usuario_activity
ALTER TABLE usuario_activity ADD CONSTRAINT fk_usuario_activity_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE usuario_activity ADD CONSTRAINT fk_usuario_activity_activity FOREIGN KEY (activity_id) REFERENCES activity(id);

-- FKS usuario_achievement
ALTER TABLE usuario_achievement ADD CONSTRAINT fk_usuario_achievement_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE usuario_achievement ADD CONSTRAINT fk_usuario_achievement_achievement FOREIGN KEY (achievement_id) REFERENCES achievement(id);

-- FKS usuario_challenge
ALTER TABLE usuario_challenge ADD CONSTRAINT fk_usuario_challenge_sender FOREIGN KEY (usuario_sender_id) REFERENCES usuario(id);
ALTER TABLE usuario_challenge ADD CONSTRAINT fk_usuario_challenge_receiver FOREIGN KEY (usuario_receiver_id) REFERENCES usuario(id);
ALTER TABLE usuario_challenge ADD CONSTRAINT fk_usuario_challenge_challenge FOREIGN KEY (challenge_id) REFERENCES challenge(id);
