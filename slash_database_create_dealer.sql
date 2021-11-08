create table game
(
    game_id bytea not null,
    primary key (game_id)
);
create table game_player
(
    game_game_id     bytea not null,
    player_player_id bytea not null
);
create table player
(
    player_id bytea not null,
    served    bit,
    primary key (player_id)
);
