-- ADMIN
DROP TABLE admin CASCADE CONSTRAINTS;
CREATE TABLE admin (
  fname    varchar(15) not null, 
  lname    varchar(15) not null,
  username   varchar(15) not null,
  password   varchar(20) not null,
  primary key (username)
);


-- User
DROP TABLE regular_user CASCADE CONSTRAINTS;
CREATE TABLE regular_user (
  fname    varchar(15) not null, 
  lname    varchar(15) not null,
  username   varchar(15) not null,
  password   varchar(20) not null,
  activate char not null,
  primary key (username)
);

--Sale Posts
DROP TABLE sale_posts CASCADE CONSTRAINTS;
CREATE TABLE sale_posts (
  post_username   varchar(15) not null,
  post_name    varchar(15) not null,
  sale_location  varchar(15) not null,
  sale_description varchar(100),
  sale_time varchar(10) not null,
  price_range varchar(15),
  image varchar(50),
  primary key (post_username,post_name),
Constraint FK_sale_posts foreign key (post_username) references regular_user(username)
);
--Save Posts
--sale_post_username is the username of the person who posted about the sale
--save_post_username is the current user who chose to save the post for later 
DROP TABLE save_posts CASCADE CONSTRAINTS;
CREATE TABLE save_posts (
  sale_post_username   varchar(15) not null,
  save_post_username varchar(15) not null,
  post_name varchar(20) not null,
  sale_location  varchar(15) not null,
  sale_description varchar(100),
  sale_time varchar(10) not null,
  price_range varchar(15),
  image varchar(50),
  primary key (sale_post_username,post_name, save_post_username),
Constraint FK_save_posts1 foreign key (sale_post_username, post_name) references sale_posts(post_username, post_name),
Constraint FK_save_posts2 foreign key (save_post_username) references regular_user(username)
);
--Items
DROP TABLE items CASCADE CONSTRAINTS;
CREATE TABLE items (
    post_title varchar(20) not null,
    item_title varchar(20) not null,
    sale_post_username varchar(15) not null,
    item_category varchar(15),
    item_image varchar(50),
    item_description varchar(100),
    item_price varchar(15),
    item_quantity varchar(15),
    primary key (post_title, item_title, sale_post_username),
    Constraint FK_items foreign key (sale_post_username, post_title) references sale_posts(post_username, post_name)
);
DROP TABLE dates CASCADE CONSTRAINTS;
CREATE TABLE dates (
      sale_date date not null,
      post_title varchar(20) not null,
      sale_post_username varchar(15) not null,
      primary key (sale_date, post_title, sale_post_username),
      Constraint FK_dates foreign key (sale_post_username, post_title) references sale_posts(post_username, post_name)
);

--admins
INSERT INTO admin (FNAME, LNAME, USERNAME, PASSWORD) VALUES ('Imad','Rahal','iRahal', 'csciROCKS');
INSERT INTO admin (FNAME, LNAME, USERNAME, PASSWORD) VALUES ('Peter','Ohmann','pOhmann', 'csciREALLYrocks');
INSERT INTO admin (FNAME, LNAME, USERNAME, PASSWORD) VALUES ('Noreen','Herzfeld','paradox', 'pair_of_docks');

--regular user
INSERT INTO regular_user (FNAME, LNAME,USERNAME, PASSWORD, ACTIVATE) VALUES ('Martin','Short','mShort','#1comedian', 'Y');
INSERT INTO regular_user (FNAME, LNAME,USERNAME, PASSWORD, ACTIVATE) VALUES ('Steve','Martin','sMartin','#2comedian', 'Y');
INSERT INTO regular_user (FNAME, LNAME,USERNAME, PASSWORD, ACTIVATE) VALUES ('Selena','Gomez','sGomez','actress', 'Y');
INSERT INTO regular_user (FNAME, LNAME,USERNAME, PASSWORD, ACTIVATE) VALUES ('Dule','Hill','dHill','actor', 'Y');

--sale_posts
INSERT INTO sale_posts (POST_USERNAME, POST_NAME, SALE_LOCATION, SALE_DESCRIPTION, SALE_TIME, PRICE_RANGE, IMAGE) VALUES ('mShort','Monster Sale','St. Joseph','This garage sale will meet your every need. Come check it out!','10-4','$2-$20',null);
INSERT INTO sale_posts (POST_USERNAME, POST_NAME, SALE_LOCATION, SALE_DESCRIPTION, SALE_TIME, PRICE_RANGE, IMAGE) VALUES ('sMartin','Junk Sale','Sartell','This garage sale is for anyone who thinks they may need just a little more junk!', '11-6','$.01-$5',null);
INSERT INTO sale_posts (POST_USERNAME, POST_NAME, SALE_LOCATION, SALE_DESCRIPTION, SALE_TIME, PRICE_RANGE, IMAGE) VALUES ('sGomez','A Big Sale','Sartell','I am selling a lot of old cothes', '8-4','$1-$10',null);
INSERT INTO sale_posts (POST_USERNAME, POST_NAME, SALE_LOCATION, SALE_DESCRIPTION, SALE_TIME, PRICE_RANGE, IMAGE) VALUES ('mShort','Antique Sale','St. Joseph','I am old therefore my stuff is antique and I am rid to part with it.','1-5','$2-$50',null);

--save posts
INSERT INTO save_posts (SALE_POST_USERNAME, SAVE_POST_USERNAME, POST_NAME, SALE_LOCATION, SALE_DESCRIPTION, SALE_TIME, PRICE_RANGE, IMAGE) VALUES ('mShort','dHill','Monster Sale','St. Joseph','This garage sale will meet your every need. Come check it out!','10-4','$2-$20',null);
INSERT INTO save_posts (SALE_POST_USERNAME, SAVE_POST_USERNAME, POST_NAME, SALE_LOCATION, SALE_DESCRIPTION, SALE_TIME, PRICE_RANGE, IMAGE) VALUES ('mShort','dHill','Antique Sale','St. Joseph','I am old therefore my stuff is antique and I am ready to part with it.','1-5','$2-$50',null);

--items
INSERT INTO items (POST_TITLE, ITEM_TITLE, SALE_POST_USERNAME, ITEM_CATEGORY, ITEM_IMAGE, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_QUANTITY) VALUES ('Antique Sale', 'grandfather clock', 'mShort', 'Antique', null, 'It is 4 feet by 10 feet clock in perfect condition', '$30', '1');
INSERT INTO items (POST_TITLE, ITEM_TITLE, SALE_POST_USERNAME, ITEM_CATEGORY, ITEM_IMAGE, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_QUANTITY) VALUES ('Antique Sale', 'vinyl records', 'mShort', 'Antique', null, 'A wide assortment of vinyls', '$8', '30');
INSERT INTO items (POST_TITLE, ITEM_TITLE, SALE_POST_USERNAME, ITEM_CATEGORY, ITEM_IMAGE, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_QUANTITY) VALUES ('Antique Sale', 'vinyl turntable', 'mShort', 'Antique', null, 'Music sounds great played on this turntable', '$20', '1');
INSERT INTO items (POST_TITLE, ITEM_TITLE, SALE_POST_USERNAME, ITEM_CATEGORY, ITEM_IMAGE, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_QUANTITY) VALUES ('Junk Sale', 'broken crayons', 'sMartin', 'Arts and Crafts', null, 'These broken crayons are perfect for coloring with', '$.10', '50');
INSERT INTO items (POST_TITLE, ITEM_TITLE, SALE_POST_USERNAME, ITEM_CATEGORY, ITEM_IMAGE, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_QUANTITY) VALUES ('Junk Sale', 'old rags', 'sMartin', 'Towels', null, 'These rags are perfect for really dirty messes that you do not want to use your nice rags with', '$.25', '10');


--dates
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2023-05-21', 'RRRR-MM-DD'), 'Monster Sale', 'mShort');
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2023-05-22', 'RRRR-MM-DD'), 'Monster Sale', 'mShort');
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2023-05-23', 'RRRR-MM-DD'), 'Monster Sale', 'mShort');
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2023-05-21', 'RRRR-MM-DD'), 'Junk Sale', 'sMartin');
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2023-06-08', 'RRRR-MM-DD'), 'A Big Sale', 'sGomez');
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2023-06-09', 'RRRR-MM-DD'), 'A Big Sale', 'sGomez');
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2024-07-10', 'RRRR-MM-DD'), 'Antique Sale', 'mShort');
INSERT INTO dates (SALE_DATE, POST_TITLE, SALE_POST_USERNAME) VALUES (to_date('2024-07-11', 'RRRR-MM-DD'), 'Antique Sale', 'mShort');

