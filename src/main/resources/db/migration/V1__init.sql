create table users
(
    id         bigserial primary key,
    email      varchar(255) unique not null,
    nickname   varchar(255) unique not null,
    password   text                not null,
    created_at timestamptz         not null default now()
);

create unique index users_email_uindex on users (email);
create unique index users_nickname_uindex on users (nickname);

create table authorities
(
    id    serial primary key,
    value text not null
);

create unique index authorities_value_uindex on authorities (value);

create table user_authorities
(
    user_id      bigint not null,
    authority_id int    not null,
    primary key (user_id, authority_id),
    constraint user_authorities_users_fk foreign key (user_id)
        references users (id) on delete cascade,
    constraint user_authorities_authorities_fk foreign key (authority_id)
        references authorities (id) on delete cascade
);

insert into authorities (value)
values ('ROLE_USER');
insert into authorities (value)
values ('ROLE_ADMIN');

--

create table exercises
(
    id          serial primary key,
    name        text not null,
    description text
);

--

create table trainings
(
    id      serial primary key,
    date    timestamptz not null,
    user_id bigint      not null,
    constraint trainings_user_fk foreign key (user_id)
        references users (id) on delete cascade
);

--

create table training_exercises
(
    training_id bigint,
    exercise_id bigint,
    primary key (training_id, exercise_id),
    constraint training_exercises_trainings_fk foreign key (training_id)
        references trainings (id),
    constraint training_activities_exercises_fk foreign key (exercise_id)
        references exercises (id)
);

--

create table achievements
(
    id          serial primary key,
    description text   not null,
    user_id     bigint not null,
    constraint achievements_user_fk foreign key (user_id)
        references users (id) on delete cascade
);

--

create table groups
(
    id       serial primary key,
    name     varchar(255) not null,
    admin_id bigint       not null
);

--

create table user_groups
(
    user_id  bigint not null,
    group_id int    not null,
    primary key (user_id, group_id),
    constraint user_groups_users_fk foreign key (user_id)
        references users (id),
    constraint user_groups_groups_fk foreign key (group_id)
        references groups (id)
);

--

create table results
(
    id          bigserial primary key,
    user_id     bigint not null,
    training_id bigint not null,
    exercise_id bigint not null,
    weight      int    not null,
    reps        int    not null
);



