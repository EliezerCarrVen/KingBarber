# 📘 Guía de Sincronización y Flujo de Trabajo en Git — Proyecto Barbería

**Objetivo:** mantener la rama `main` siempre estable y asegurar que todo el equipo trabaje con la última versión del código, sin sobrescribir el trabajo de los demás.

**Stack del proyecto:** Backend/Admin en TypeScript (Node.js) · Apps de Cliente y Barbero en Kotlin · MongoDB (local y Atlas) · PayPal

---

## 1. Configuración inicial (una sola vez por persona)

```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu@correo.com"
```

Clona el repositorio y entra a la carpeta:

```bash
git clone <URL_DEL_REPOSITORIO>
cd nombre-del-proyecto
```

Si el backend es Node/TypeScript, instala dependencias:

```bash
npm install
```

> Usa autenticación con SSH o un Token de Acceso Personal (PAT) para no escribir tu contraseña en cada `push`.

---

## 2. Flujo de trabajo diario (Scrum)

Nunca se trabaja directamente sobre `main`. Cada tarea del sprint sigue estos 4 pasos:

**A. Sincronizar antes de empezar**
```bash
git checkout main
git pull
```

**B. Crear una rama para tu tarea**
```bash
git checkout -b feature/nombre-de-tu-tarea
# ej: git checkout -b feature/login-barberos
```

**C. Guardar tu progreso**
```bash
git status
git add .
git commit -m "feat: agrega validación de contraseña en login"
```

**D. Subir tu rama a la nube**
```bash
git push origin feature/nombre-de-tu-tarea
```

---

## 3. Reglas de oro del equipo

- 🚫 **Prohibido hacer push directo a `main`.** Todo cambio entra por Pull Request (PR).
- 👀 **Revisión entre compañeros:** al abrir un PR, avisa en la Daily para que alguien lo revise antes de fusionarlo.
- 🧹 **`.gitignore` configurado desde el día uno** (ver abajo) para no subir archivos generados.
- 🔄 **`git pull` cada día antes de programar**, para evitar conflictos.
- 🗣️ **Comunicación:** si vas a tocar un modelo compartido (ej. esquema de `Usuarios` en MongoDB), avisa al equipo antes.

---

## 4. Nomenclatura de ramas sugerida

| Tipo | Prefijo | Ejemplo |
|---|---|---|
| Nueva funcionalidad | `feature/` | `feature/geolocalizacion-sucursales` |
| Corrección de bug | `fix/` | `fix/cruce-horarios-citas` |
| Tarea técnica / spike | `chore/` o `spike/` | `spike/repositorios-git` |

---

## 5. `.gitignore` recomendado (monorepo TypeScript + Kotlin)

```gitignore
# --- Node / TypeScript ---
node_modules/
dist/
.env
*.log
npm-debug.log*

# --- Kotlin / Android ---
.gradle/
build/
*.apk
*.aab
local.properties
.idea/
*.iml

# --- General ---
.DS_Store
```

---

## 6. Comandos usuales ("cheat sheet")

**Sincronizar**
```bash
git pull          # baja los últimos cambios de la nube
git fetch         # revisa si hay cambios sin descargarlos
```

**Guardar y subir**
```bash
git status
git add .
git commit -m "mensaje descriptivo"
git push
```

**Ramas**
```bash
git branch                      # ver en qué rama estás
git checkout -b <nombre_rama>   # crear y cambiar a una rama nueva
git checkout <nombre_rama>      # cambiar a una rama existente
git merge <nombre_rama>         # fusionar una rama a la actual (normalmente vía PR)
```

---

## 7. Pasos para dejar el Spike resuelto esta semana

1. Elegir plataforma de alojamiento (GitHub / GitLab / Bitbucket).
2. Crear el repositorio remoto vacío.
3. Inicializar el proyecto local, añadir este `.gitignore` y este `README.md`.
4. Hacer el primer commit y `push -u origin main`.
5. Compartir la URL con el equipo para que hagan `git clone`.
6. Proteger la rama `main` (requerir PR antes de merge) en la configuración del repositorio.
