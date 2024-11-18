
# Control2TBD

Control 2 Taller Base de Datos.



## BACKEND

- Crear la base de datos en postgreSQL (pgAdmin 4) con el nombre de "Control2TBD".
- El proyecto se encuentra separado tanto su BACKEND y FRONTEND.
- Una vez ingresado al BACKEND se debe ir al archivo aplication.propertiers, debe asegurarse de que el puerto que se este ocupando este libre para su uso.

    - El puerto donde se esta ejecutando es el 8070.

- Luego ingresar a la carpeta config y al archivo "DataBaseContext.java", en el se debe cambiar el nombre de usuario y la contraseña según se disponga en su configuración. 
- De ejemplo esta puesto colocado lo siguiente:
    - Usuario:  postgres (cambiar).
    - Contraseña: admin  (cambiar).
    - localhost: 5432 por defecto.

- Luego de realizar los cambios, se debe dar a ejecutar el BACKEND.

Nota: Las tablas y la población inicial se crean al momento de insertar el script y correrlo en pgadmin 4.

## FRONTEND

### Requisitos:

- Herramientas como visual studio code.
- Tener instalado algun package manager.
    - Instalar node.js.

### Ir a la carpeta del frontend.

```bash
  cd ./Frontend
```
### Instalación de paquetes.

```bash
  npm install
```
### Compilación y recarga en host para desarrollo.

```bash
  npm run serve
```

### Compilación y minimización de producción.

```bash
  npm run build
```

### Lints y archivos de arreglos.

```bash
  npm run lint
```

## Versiones

- Project: Maven
- Language: Java
- Spring Boot: 3.3.2
- Packaging: Jar
- Java: 17


