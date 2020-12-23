desc user;
-- 전체회원 보기: select
select * from user;
-- 회원가입: insert
insert into user values(null, '안대혁', 'kickscar@gmail.com', '1234', 'male', now());
-- 로그인: select 
select * from user where email='kickscar@gmail.com' and password='1234';
-- 회원정보: select
select no, name, email, gender, date_format(join_date, '%Y-%m-%d') 
from user 
where no=1;
-- 회원정보 수정: update 