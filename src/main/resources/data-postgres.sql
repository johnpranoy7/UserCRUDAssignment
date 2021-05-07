insert into "user"(id,created_by,created_on,mail,phone_no,status,updated_by,updated_on,username,profile_pic)
values('abcaf2f0-ba58-4726-a84c-d35d7d8e1f5a', 'Admin', '19-04-2021','admin@abcd.com','1234567899',true,'admin','19-04-2021','testUser','');

insert into "user"(id,created_by,created_on,mail,phone_no,status,updated_by,updated_on,username,profile_pic)
values('bb443c02-fcdc-4fd8-b2b9-f932678f057f', 'John', '20-04-2021','john@abcd.com','7093091742',true,'admin','20-04-2021','john','https://www.google.com');

insert into blog(id,created_by,created_on,description,title,updated_by,updated_on,url,author_id)
values('207ce4bd-eaf0-4be1-bd64-ff7393f9dad5','Admin','20-04-2021','DS&Algoithms','DS','Admin','20-04-2021','https://www.geeksforgeeks.org/data-structures/','bb443c02-fcdc-4fd8-b2b9-f932678f057f');