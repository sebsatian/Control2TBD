CREATE DATABASE "Control2TBD";


-- Crear tabla users
CREATE TABLE IF NOT EXISTS public.users (
                                            id SERIAL PRIMARY KEY, -- Clave primaria
                                            username TEXT NOT NULL UNIQUE, -- El username debe ser único
                                            password TEXT NOT NULL
);

-- Crear tabla tasks
CREATE TABLE IF NOT EXISTS public.tasks (
                                            id SERIAL PRIMARY KEY, -- Clave primaria
                                            title TEXT NOT NULL,
                                            description TEXT,
                                            due_date DATE NOT NULL, -- Nombre ajustado
                                            completed BOOLEAN NOT NULL,
                                            user_id INT NOT NULL, -- Relación con users
                                            CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE
    );

-- Cambiar propietario de las tablas
ALTER TABLE IF EXISTS public.users OWNER TO postgres;
ALTER TABLE IF EXISTS public.tasks OWNER TO postgres;

-- Insertar datos en la tabla users
INSERT INTO public.users (id, username, password)
VALUES
    (1, 'postgres', '123')
    ON CONFLICT (id) DO NOTHING; -- Evitar duplicados si ya existe

-- Insertar datos en la tabla tasks
INSERT INTO public.tasks (id, title, description, due_date, completed, user_id)
VALUES
    (1, 'Reunion', 'Organizar Grupo', '2024-11-16', true, 1)
    ON CONFLICT (id) DO NOTHING; -- Evitar duplicados si ya existe

-- Crear índice único para username en la tabla users (opcional)
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_username ON public.users (username);
