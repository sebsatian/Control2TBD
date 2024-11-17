Create database Control2TBD

CREATE TABLE IF NOT EXITS public.UserEntity(
    id int,
    username text,
    password text
);
ALTER TABLE IF EXITS public.UserEntity

CREATE TABLE IF NOT EXITS public.TaskEntity(
    id int,
    title text,
    description text,
    LocalDate date,
    completed boolean,
    userId int
);

ALTER TABLE IF EXISTS public.TaskEntity
    OWNER to postgres;

INSERT INTO public.UserEntity(id,username,password)
VALUES
    (1,'postgres','123');

INSERT INTO public.TaskEntity(id,title,description,LocalDate,completed,UserId)
VALUES
    (1,'Reunion','Organizar Grupo','2024-11-16','true',1);