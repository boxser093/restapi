CREATE TABLE file
(
    id        int primary key auto_increment not null,
    name      text,
    file_path text
);
CREATE TABLE user
(
    id       int primary key auto_increment not null,
    name     text,
    event_id int references event_table(id)
);
CREATE TABLE event_table
(
    id      int primary key auto_increment not null,
    user_id int,
    file_id int,
    foreign key event_user (user_id) references user (id) ON UPDATE CASCADE,
    foreign key event_file (file_id) references file (id) ON UPDATE CASCADE
);
