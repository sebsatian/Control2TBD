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
    (1, 'isaac', '123'),
    (2, 'seba', '1234'),
    (3, 'diego', '12345'),
    (4, 'cristian', '123456'),
    (5, 'seba', '1234567'),
    (6, 'joaquin', '123457')
    ON CONFLICT (id) DO NOTHING; -- Evitar duplicados si ya existe

-- Insertar datos en la tabla tasks
INSERT INTO public.tasks (id, title, description, due_date, completed, user_id)
VALUES
    (1, 'Reunion', 'Backend', '2024-11-16', true, 1),
    (2, 'Reunion', 'Backend', '2024-11-16', true, 2),
    (3, 'Reunion', 'Backend', '2024-11-16', true, 3),
    (4, 'Reunion', 'Frontend', '2024-11-16', true, 4),
    (5, 'Reunion', 'Frontend', '2024-11-16', true, 5),
    (6, 'Reunion', 'Frontend', '2024-11-16', true, 6)
    ON CONFLICT (id) DO NOTHING; -- Evitar duplicados si ya existe

-- Crear índice único para username en la tabla users (opcional)
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_username ON public.users (username);
