create table auth.role
(
    id           varchar(36) not null,
    created_date timestamp,
    name         varchar(255),
    primary key (id)
);
create table auth.user
(
    id           varchar(36) not null,
    created_date timestamp,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    password     varchar(255),
    primary key (id)
);
create table user_roles
(
    user_id  varchar(36) not null,
    roles_id varchar(36) not null
);
create table user_user_players
(
    user_id         varchar(36) not null,
    user_players_id varchar(36) not null
);
create table auth.role
(
    id           varchar(36) not null,
    created_date timestamp,
    name         varchar(255),
    primary key (id)
)
create table auth.user
(
    id           varchar(36) not null,
    created_date timestamp,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    password     varchar(255),
    primary key (id)
)
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
)
create table game
(
    id           varchar(36) not null,
    created_date timestamp,
    bets_bank    int4,
    primary key (id)
)
create table game_cards
(
    game_id  varchar(36) not null,
    cards_id uuid        not null
)
create table play
(
    id           varchar(36) not null,
    created_date timestamp,
    small_blind  int4,
    primary key (id)
)
create table play_games
(
    play_id  varchar(36) not null,
    games_id varchar(36) not null
)
create table play_players
(
    play_id    varchar(36) not null,
    players_id varchar(36) not null
)
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
)
create table player_cards
(
    player_id varchar(36) not null,
    cards_id  uuid        not null
)
create table user_roles
(
    user_id  varchar(36) not null,
    roles_id varchar(36) not null
)
create table user_user_players
(
    user_id         varchar(36) not null,
    user_players_id varchar(36) not null
)
alter table game_cards
    add constraint UK_tm629o43ho236bm1b5x525gi4 unique (cards_id)
alter table play_games
    add constraint UK_7yoktoqbtj5pf2sle0pqpnxsd unique (games_id)
alter table play_players
    add constraint UK_9uq0mseb7lr9bbta70j9nly3a unique (players_id)
alter table player_cards
    add constraint UK_eu14jwktj3wqbooslor0xd0p0 unique (cards_id)
alter table user_user_players
    add constraint UK_tr5t3nrx5hfi107dv6h612tml unique (user_players_id)
alter table game_cards
    add constraint FKq22peprxub4q7s89a47vons27 foreign key (cards_id) references card
alter table game_cards
    add constraint FKk9xmcdg9wyducg55f1bs8agqd foreign key (game_id) references game
alter table play_games
    add constraint FK7pgl7kps2npfxqqjqcqgd4t05 foreign key (games_id) references game
alter table play_games
    add constraint FKkwbwty7645e19c21f7rx4lx78 foreign key (play_id) references play
alter table play_players
    add constraint FKcu3valyjwcf7gub8h3oc2n885 foreign key (players_id) references player
alter table play_players
    add constraint FKkxm5ef16ooevrs5lfvp66p8v1 foreign key (play_id) references play
alter table player
    add constraint FKt19pprt15qv01oucskndmh8r4 foreign key (plays_id) references play
alter table player_cards
    add constraint FKqaek75wpycyve467ss52ve2u8 foreign key (cards_id) references card
alter table player_cards
    add constraint FKde21dwj10h5g5bfd1m4sdxiw9 foreign key (player_id) references player
alter table user_roles
    add constraint FKj9553ass9uctjrmh0gkqsmv0d foreign key (roles_id) references auth.role
alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references auth.user
alter table user_user_players
    add constraint FKkgmmiexkhs51wtsyyuv6uaoy foreign key (user_players_id) references player
alter table user_user_players
    add constraint FKmwohssm7d6qavncma8rokbn72 foreign key (user_id) references auth.user
create table auth.role
(
    id           varchar(36) not null,
    created_date timestamp,
    name         varchar(255),
    primary key (id)
)
create table auth.user
(
    id           varchar(36) not null,
    created_date timestamp,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    password     varchar(255),
    primary key (id)
)
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
)
create table game
(
    id           varchar(36) not null,
    created_date timestamp,
    bets_bank    int4,
    primary key (id)
)
create table game_cards
(
    game_id  varchar(36) not null,
    cards_id uuid        not null
)
create table play
(
    id           varchar(36) not null,
    created_date timestamp,
    small_blind  int4,
    primary key (id)
)
create table play_games
(
    play_id  varchar(36) not null,
    games_id varchar(36) not null
)
create table play_players
(
    play_id    varchar(36) not null,
    players_id varchar(36) not null
)
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
)
create table player_cards
(
    player_id varchar(36) not null,
    cards_id  uuid        not null
)
create table user_roles
(
    user_id  varchar(36) not null,
    roles_id varchar(36) not null
)
create table user_user_players
(
    user_id         varchar(36) not null,
    user_players_id varchar(36) not null
)
alter table game_cards
    add constraint UK_tm629o43ho236bm1b5x525gi4 unique (cards_id)
alter table play_games
    add constraint UK_7yoktoqbtj5pf2sle0pqpnxsd unique (games_id)
alter table play_players
    add constraint UK_9uq0mseb7lr9bbta70j9nly3a unique (players_id)
alter table player_cards
    add constraint UK_eu14jwktj3wqbooslor0xd0p0 unique (cards_id)
alter table user_user_players
    add constraint UK_tr5t3nrx5hfi107dv6h612tml unique (user_players_id)
alter table game_cards
    add constraint FKq22peprxub4q7s89a47vons27 foreign key (cards_id) references card
alter table game_cards
    add constraint FKk9xmcdg9wyducg55f1bs8agqd foreign key (game_id) references game
alter table play_games
    add constraint FK7pgl7kps2npfxqqjqcqgd4t05 foreign key (games_id) references game
alter table play_games
    add constraint FKkwbwty7645e19c21f7rx4lx78 foreign key (play_id) references play
alter table play_players
    add constraint FKcu3valyjwcf7gub8h3oc2n885 foreign key (players_id) references player
alter table play_players
    add constraint FKkxm5ef16ooevrs5lfvp66p8v1 foreign key (play_id) references play
alter table player
    add constraint FKt19pprt15qv01oucskndmh8r4 foreign key (plays_id) references play
alter table player_cards
    add constraint FKqaek75wpycyve467ss52ve2u8 foreign key (cards_id) references card
alter table player_cards
    add constraint FKde21dwj10h5g5bfd1m4sdxiw9 foreign key (player_id) references player
alter table user_roles
    add constraint FKj9553ass9uctjrmh0gkqsmv0d foreign key (roles_id) references auth.role
alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references auth.user
alter table user_user_players
    add constraint FKkgmmiexkhs51wtsyyuv6uaoy foreign key (user_players_id) references player
alter table user_user_players
    add constraint FKmwohssm7d6qavncma8rokbn72 foreign key (user_id) references auth.user
create table auth.role
(
    id           varchar(36) not null,
    created_date timestamp,
    name         varchar(255),
    primary key (id)
)
create table auth.user
(
    id           varchar(36) not null,
    created_date timestamp,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    password     varchar(255),
    primary key (id)
)
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
)
create table game
(
    id           varchar(36) not null,
    created_date timestamp,
    bets_bank    int4,
    primary key (id)
)
create table game_cards
(
    game_id  varchar(36) not null,
    cards_id uuid        not null
)
create table play
(
    id           varchar(36) not null,
    created_date timestamp,
    small_blind  int4,
    primary key (id)
)
create table play_games
(
    play_id  varchar(36) not null,
    games_id varchar(36) not null
)
create table play_players
(
    play_id    varchar(36) not null,
    players_id varchar(36) not null
)
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
)
create table player_cards
(
    player_id varchar(36) not null,
    cards_id  uuid        not null
)
create table user_roles
(
    user_id  varchar(36) not null,
    roles_id varchar(36) not null
)
create table user_user_players
(
    user_id         varchar(36) not null,
    user_players_id varchar(36) not null
)
alter table game_cards
    add constraint UK_tm629o43ho236bm1b5x525gi4 unique (cards_id)
alter table play_games
    add constraint UK_7yoktoqbtj5pf2sle0pqpnxsd unique (games_id)
alter table play_players
    add constraint UK_9uq0mseb7lr9bbta70j9nly3a unique (players_id)
alter table player_cards
    add constraint UK_eu14jwktj3wqbooslor0xd0p0 unique (cards_id)
alter table user_user_players
    add constraint UK_tr5t3nrx5hfi107dv6h612tml unique (user_players_id)
alter table game_cards
    add constraint FKq22peprxub4q7s89a47vons27 foreign key (cards_id) references card
alter table game_cards
    add constraint FKk9xmcdg9wyducg55f1bs8agqd foreign key (game_id) references game
alter table play_games
    add constraint FK7pgl7kps2npfxqqjqcqgd4t05 foreign key (games_id) references game
alter table play_games
    add constraint FKkwbwty7645e19c21f7rx4lx78 foreign key (play_id) references play
alter table play_players
    add constraint FKcu3valyjwcf7gub8h3oc2n885 foreign key (players_id) references player
alter table play_players
    add constraint FKkxm5ef16ooevrs5lfvp66p8v1 foreign key (play_id) references play
alter table player
    add constraint FKt19pprt15qv01oucskndmh8r4 foreign key (plays_id) references play
alter table player_cards
    add constraint FKqaek75wpycyve467ss52ve2u8 foreign key (cards_id) references card
alter table player_cards
    add constraint FKde21dwj10h5g5bfd1m4sdxiw9 foreign key (player_id) references player
alter table user_roles
    add constraint FKj9553ass9uctjrmh0gkqsmv0d foreign key (roles_id) references auth.role
alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references auth.user
alter table user_user_players
    add constraint FKkgmmiexkhs51wtsyyuv6uaoy foreign key (user_players_id) references player
alter table user_user_players
    add constraint FKmwohssm7d6qavncma8rokbn72 foreign key (user_id) references auth.user
