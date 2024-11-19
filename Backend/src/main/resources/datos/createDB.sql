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