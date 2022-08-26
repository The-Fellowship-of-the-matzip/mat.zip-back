insert into member (github_id, username, profile_image, created_at) values ('87312401', 'jayjaehunchoi', 'https://avatars.githubusercontent.com/u/87312401?v=4', current_timestamp);
insert into member (github_id, username, profile_image, created_at) values ('43166681', 'uk960214', 'https://avatars.githubusercontent.com/u/43166681?v=4', current_timestamp);
insert into member (github_id, username, profile_image, created_at) values ('54002105', 'nan-noo', 'https://avatars.githubusercontent.com/u/54002105?v=4', current_timestamp);
insert into member (github_id, username, profile_image, created_at) values ('60773373', 'liswktjs', 'https://avatars.githubusercontent.com/u/60773373?v=4', current_timestamp);
insert into member (github_id, username, profile_image, created_at) values ('69106910', 'jinyoungchoi95', 'https://avatars.githubusercontent.com/u/69106910?v=4', current_timestamp);
insert into member (github_id, username, profile_image, created_at) values ('66253212', 'Ohzzi', 'https://avatars.githubusercontent.com/u/66253212?v=4', current_timestamp);

insert into category (name)
values ('한식');
insert into category (name)
values ('중식');
insert into category (name)
values ('일식');
insert into category (name)
values ('양식');
insert into category (name)
values ('카페/디저트');

insert into campus (name)
values ('잠실');
insert into campus (name)
values ('선릉');

insert into restaurant (category_id, campus_id, name, address, distance, kakao_map_url, image_url)
values (1, 2, '배가무닭볶음탕', '서울 강남구 선릉로86길 30 1층', 1, 'https://place.map.kakao.com/733513512', 'www.image.com');
insert into restaurant (category_id, campus_id, name, address, distance, kakao_map_url, image_url)
values (1, 2, '뽕나무쟁이 선릉본점', '서울 강남구 역삼로65길 31', 1, 'https://place.map.kakao.com/11190567', 'www.image.com');
insert into restaurant (category_id, campus_id, name, address, distance, kakao_map_url, image_url)
values (2, 2, '마담밍', '서울 강남구 선릉로86길 5-4 1층', 1, 'https://place.map.kakao.com/18283045', 'www.image.com');
insert into restaurant (category_id, campus_id, name, address, distance, kakao_map_url, image_url)
values (2, 2, '마담밍2', '서울 강남구 선릉로86길 5-4 2층', 1, 'https://place.map.kakao.com/18283045', 'www.image.com');
insert into restaurant (category_id, campus_id, name, address, distance, kakao_map_url, image_url)
values (2, 2, '마담밍3', '서울 강남구 선릉로86길 5-4 3층', 1, 'https://place.map.kakao.com/18283045', 'www.image.com');
