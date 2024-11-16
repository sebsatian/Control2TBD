Create database Control2

CREATE TABLE IF NOT EXITS public.UserEntity(
    username text,
    password text
);
ALTER TABLE IF EXITS public.UserEntity



CREATE TABLE IF NOT EXITS public.TaskEntity(
    title text,
    description text,
    LocalDate date,
    completed boolean,
    userId int
);

ALTER TABLE IF EXISTS public.TaskEntity
    OWNER to postgres;





INSERT INTO public.UserEntity(username,password)
VALUES
    ('postgres','123');

INSERT INTO public.TaskEntity(title,description,LocalDate,completed,UserId)
VALUES
    ('Reunion','Organizar Grupo','2024-11-16','true',1);