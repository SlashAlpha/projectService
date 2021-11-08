create table card
(
    id           uuid not null,
    color        varchar(255),
    created_date timestamp,
    description  varchar(255),
    face_val     int4,
    number       int4,
    value        int4,
    primary key (id)
);
create table game
(
    id           varchar(36) not null,
    created_date timestamp,
    bets_bank    int4,
    primary key (id)
);
create table game_cards
(
    game_id  varchar(36) not null,
    cards_id uuid        not null
);
create table play
(
    id           varchar(36) not null,
    created_date timestamp,
    small_blind  int4,
    primary key (id)
);
create table play_games
(
    play_id  varchar(36) not null,
    games_id varchar(36) not null
);
create table play_players
(
    play_id    varchar(36) not null,
    players_id varchar(36) not null
);
create table player
(
    id           varchar(36) not null,
    created_date timestamp,
    age          int4,
    bank         int4,
    bb           boolean     not null,
    bet          int4,
    dealer       boolean     not null,
    game_played  int4,
    name         varchar(255),
    number       int4,
    sb           boolean     not null,
    score        int4,
    success      int4,
    plays_id     varchar(36),
    primary key (id)
);
create table player_cards
(
    player_id varchar(36) not null,
    cards_id  uuid        not null
);
