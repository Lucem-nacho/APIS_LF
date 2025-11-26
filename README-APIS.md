# Documentación de las APIs - Legacy Frames

Este proyecto está compuesto por 4 microservicios desarrollados en **Spring Boot** que gestionan diferentes funcionalidades del sistema de e-commerce Legacy Frames.

---

## API de Autenticación (Auth)
**Puerto:** `8082`  
**Base URL:** `http://localhost:8082`  
**Base de datos:** `legacyframes_auth_db`

### Descripción
Microservicio encargado de la **autenticación y gestión de usuarios** del sistema. Implementa seguridad con JWT (JSON Web Tokens) para proteger los endpoints.

### Funcionalidades principales:
- Registro de nuevos usuarios
- Login con generación de tokens JWT
- Validación de tokens
- Gestión de perfiles de usuario
- Actualización de datos personales
- Listado de usuarios (admin)

### Endpoints principales:

| Método | Endpoint | Descripción | Auth requerida |
|--------|----------|-------------|----------------|
| `POST` | `/auth/register` | Registrar nuevo usuario | No |
| `POST` | `/auth/login` | Iniciar sesión y obtener token | No |
| `POST` | `/auth/validate` | Validar token JWT | No |
| `GET` | `/auth/perfil?email={email}` | Ver perfil de usuario | Sí |
| `GET` | `/auth/list` | Listar todos los usuarios | Sí (Admin) |
| `PUT` | `/auth/update?email={email}` | Actualizar usuario (admin) | Sí (Admin) |
| `PUT` | `/auth/profile` | Actualizar mi propio perfil | Sí |

### Configuración JWT:
- **Secret Key:** Configurada en `application.properties`
- **Expiración:** 24 horas (86400000 ms)
- **Algoritmo:** HS256

---

## API de Contacto
**Puerto:** `8081`  
**Base URL:** `http://localhost:8081`  
**Base de datos:** `legacyframes_db`

### Descripción
Microservicio que gestiona los **mensajes de contacto** enviados por los usuarios a través del formulario de contacto del sitio web.

### Funcionalidades principales:
- Recibir y almacenar mensajes de contacto
- Listar todos los mensajes
- Ver detalles de un mensaje específico
- Actualizar estado de mensajes
- Eliminar mensajes

### Endpoints principales:

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/contactos` | Crear nuevo mensaje de contacto |
| `GET` | `/api/contactos` | Obtener todos los contactos |
| `GET` | `/api/contactos/{id}` | Obtener contacto por ID |
| `PUT` | `/api/contactos/{id}` | Actualizar contacto |
| `DELETE` | `/api/contactos/{id}` | Eliminar contacto |

### Modelo de datos:
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "telefono": "+56912345678",
  "mensaje": "Consulta sobre productos",
  "fechaCreacion": "2025-11-26T10:30:00"
}
```

---

## API de Productos
**Puerto:** `8083`  
**Base URL:** `http://localhost:8083`  
**Base de datos:** `legacyframes_productos_db` (o compartida)

### Descripción
Microservicio que gestiona el **catálogo de productos y categorías**. Incluye funcionalidades para gestionar el inventario, imágenes y clasificación por categorías.

### Funcionalidades principales:
- CRUD completo de productos
- Gestión de categorías
- Upload de imágenes de productos
- Filtrado por categoría
- Control de stock
- Actualización de inventario

### Endpoints principales:

#### Productos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/catalog/productos` | Listar todos los productos |
| `GET` | `/api/catalog/productos?categoria={nombre}` | Filtrar por categoría |
| `GET` | `/api/catalog/productos/{id}` | Obtener producto por ID |
| `POST` | `/api/catalog/productos` | Crear nuevo producto |
| `PUT` | `/api/catalog/productos/{id}` | Actualizar producto |
| `PUT` | `/api/catalog/productos/{id}/stock?cantidad={n}` | Actualizar stock |
| `DELETE` | `/api/catalog/{id}` | Eliminar producto |

#### Categorías
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/catalog/categorias` | Listar todas las categorías |
| `POST` | `/api/catalog/categorias` | Crear nueva categoría |

#### Imágenes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/catalog/upload` | Subir imagen de producto |

### Categorías disponibles:
- **Cuadros** - Marcos y cuadros decorativos
- **Grecas** - Molduras con diseños clásicos
- **Rústicas** - Estilo madera envejecida
- **Nativas** - Maderas nobles chilenas
- **Finger Joint** - Unión dentada resistente
- **Naturales** - Molduras naturales con textura de madera

### Modelo de producto:
```json
{
  "id": 1,
  "nombre": "Marco Clásico",
  "descripcion": "Marco elegante para cuadros",
  "precio": 20000.0,
  "stock": 50,
  "imagenUrl": "/assets/imagen.jpg",
  "categoriaId": 1
}
```

---

## API de Pedidos
**Puerto:** `8084`  
**Base URL:** `http://localhost:8084`  
**Base de datos:** `legacyframes_pedidos_db`

### Descripción
Microservicio que gestiona los **pedidos de compra** realizados por los clientes. Mantiene el historial de compras y permite el seguimiento de órdenes.

### Funcionalidades principales:
- Creación de pedidos
- Historial de pedidos por usuario
- Listado completo de pedidos (admin)
- Gestión del estado de pedidos
- Seguimiento de órdenes

### Endpoints principales:

| Método | Endpoint | Descripción | Rol requerido |
|--------|----------|-------------|---------------|
| `POST` | `/api/orders?email={email}` | Crear nuevo pedido | Cliente |
| `GET` | `/api/orders/my-orders?email={email}` | Ver mis pedidos | Cliente |
| `GET` | `/api/orders/admin/all` | Listar todos los pedidos | Admin |

### Modelo de pedido:
```json
{
  "usuarioEmail": "cliente@example.com",
  "productos": [
    {
      "productoId": 1,
      "cantidad": 2,
      "precioUnitario": 20000.0
    }
  ],
  "total": 40000.0,
  "estado": "PENDIENTE",
  "fechaPedido": "2025-11-26T14:30:00"
}
```

---

## Configuración General

### Requisitos del sistema:
- Java JDK 17
- MySQL 8.0+
- Maven 3.6+
- Node.js (para el frontend en React)

### Puertos utilizados:
- **Auth:** 8082
- **Contacto:** 8081
- **Productos:** 8083
- **Pedidos:** 8084
- **Frontend React:** 5173

### CORS:
Todos los microservicios están configurados para aceptar peticiones desde:
```
http://localhost:5173
```

### Bases de datos:
Cada microservicio utiliza su propia base de datos MySQL:
- `legacyframes_auth_db`
- `legacyframes_db` (contacto)
- `legacyframes_productos_db` (o compartida)
- `legacyframes_pedidos_db`

---

## Cómo ejecutar las APIs

### Opción 1: Desde VS Code
1. Abrir el proyecto en VS Code
2. Ejecutar cada Application desde los terminales ya configurados:
   - `AuthApplication`
   - `ContactoApplication`
   - `ProductosApplication`
   - `PedidosApplication`

### Opción 2: Desde Maven
```bash
# En cada carpeta de microservicio
cd auth
./mvnw spring-boot:run

cd ../contacto
./mvnw spring-boot:run

cd ../productos
./mvnw spring-boot:run

cd ../pedidos
./mvnw spring-boot:run
```

### Opción 3: Compilar y ejecutar JAR
```bash
# En cada carpeta
./mvnw clean package
java -jar target/nombre-del-microservicio.jar
```

---

## Documentación adicional

Para ver la documentación detallada de los endpoints de productos, consulta:
- [`productos/API-ENDPOINTS.md`](productos/API-ENDPOINTS.md)

---

## Seguridad

- El microservicio de **Auth** implementa JWT para autenticación
- Los endpoints protegidos requieren el header:
  ```
  Authorization: Bearer {token}
  ```
- Los roles de usuario determinan el acceso a endpoints administrativos

---

## Stack Tecnológico

- **Framework:** Spring Boot 3.x
- **Base de datos:** MySQL 8.0
- **ORM:** Hibernate/JPA
- **Seguridad:** Spring Security + JWT
- **Frontend:** React + Vite
- **Gestión de dependencias:** Maven

---

## Notas importantes

1. Asegúrate de que MySQL esté corriendo antes de iniciar los microservicios
2. Verifica que los puertos no estén ocupados
3. Las bases de datos se crean automáticamente con `ddl-auto=update`
4. El secret de JWT debe ser seguro en producción
5. Configura las credenciales de MySQL en cada `application.properties`

---

**Proyecto:** Legacy Frames E-commerce  
**Arquitectura:** Microservicios  
**Última actualización:** Noviembre 2025
