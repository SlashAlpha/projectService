create table game (game_id binary(255) not null, primary key (game_id)) engine=InnoDB DEFAULT CHARSET=utf8;
create table game_player (game_game_id binary(255) not null, player_player_id binary(255) not null) engine=InnoDB DEFAULT CHARSET=utf8;
create table player (player_id binary(255) not null, served bit, primary key (player_id)) engine=InnoDB DEFAULT CHARSET=utf8;
