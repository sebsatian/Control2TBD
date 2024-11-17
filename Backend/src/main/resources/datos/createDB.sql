Create database Control2TBD

-- Crear tabla UserEntity
CREATE TABLE IF NOT EXISTS public.UserEntity (
                                                 id SERIAL PRIMARY KEY, -- Clave primaria
                                                 username TEXT NOT NULL,
                                                 password TEXT NOT NULL
);

-- Crear tabla TaskEntity
CREATE TABLE IF NOT EXISTS public.TaskEntity (
                                                 id SERIAL PRIMARY KEY, -- Clave primaria
                                                 title TEXT NOT NULL,
                                                 description TEXT,
                                                 local_date DATE NOT NULL, -- Nombre corregido
                                                 completed BOOLEAN NOT NULL,
                                                 user_id INT NOT NULL, -- Relación con UserEntity
                                                 CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.UserEntity (id) ON DELETE CASCADE
    );

-- Cambiar propietario de la tabla TaskEntity a postgres
ALTER TABLE IF EXISTS public.TaskEntity
    OWNER TO postgres;

-- Insertar datos en UserEntity
INSERT INTO public.UserEntity (id, username, password)
VALUES
    (1, 'postgres', '123')
    ON CONFLICT (id) DO NOTHING; -- Evitar duplicados si ya existe

-- Insertar datos en TaskEntity
INSERT INTO public.TaskEntity (id, title, description, local_date, completed, user_id)
VALUES
    (1, 'Reunion', 'Organizar Grupo', '2024-11-16', true, 1)
    ON CONFLICT (id) DO NOTHING; -- Evitar duplicados si ya existe
