CREATE TABLE member
(
    id            bigint       NOT NULL AUTO_INCREMENT,
    github_id     varchar(255) NOT NULL UNIQUE,
    username      varchar(255) NOT NULL,
    profile_image varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE restaurant
(
    id            bigint       NOT NULL AUTO_INCREMENT,
    category_id   bigint       NOT NULL,
    campus_id     bigint       NOT NULL,
    name          varchar(20)  NOT NULL,
    address       varchar(255) NOT NULL UNIQUE,
    distance      bigint       NOT NULL,
    kakao_map_url varchar(255) NOT NULL,
    image_url     varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE review
(
    id            bigint       NOT NULL AUTO_INCREMENT,
    member_id     bigint       NOT NULL,
    restaurant_id bigint       NOT NULL,
    content       varchar(255) NULL,
    score         int          NOT NULL,
    menu          varchar(20)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE category
(
    id   bigint      NOT NULL AUTO_INCREMENT,
    name varchar(10) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE campus
(
    id   bigint      NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
