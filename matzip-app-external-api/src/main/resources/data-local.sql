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

insert into member (id, github_id, username, profile_image, created_at)
values (1, '1', '테스트이름', 'image.com', CURRENT_DATE);

