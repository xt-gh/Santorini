# Santorini Web Migration Instructions

## Goal

Convert the existing Java Santorini game into a web application that can be deployed reliably on Render and used as a portfolio project.

The existing repository contains multiple folders.

The main project that should be modified is:

```text
Sprint3Implementation/
```

The main application entry point is:

```text
Sprint3Implementation/.../Application.java
```

Before doing anything, locate and inspect `Application.java` and use it to understand how the current Santorini application starts and how the existing classes are connected.

Do not modify unrelated folders in the repository unless they are required for the migration.

---

# Target Architecture

The final application should use:

```text
React frontend
      ↓ REST API
Java Spring Boot backend
      ↓
Existing Santorini Java game logic
```

Deployment target:

```text
Render Static Site
    React frontend

Render Web Service
    Java Spring Boot backend
```

Do not use:

* VNC
* remote desktop
* CheerpJ
* browser-based Swing emulation
* Python
* unnecessary database services

No database is required unless the current Santorini implementation genuinely depends on one.

---

# Important Priority

Preserve the existing Java Santorini game logic as much as possible.

Do NOT unnecessarily rewrite:

* Board logic
* Player logic
* Worker logic
* Turn logic
* Move validation
* Build validation
* Win conditions
* God powers
* Existing game rules

The goal is to replace the desktop presentation layer with React, not rewrite the game engine.

If an existing Java class can work unchanged, leave it unchanged.

Before significantly modifying an existing Java class, determine why the change is necessary.

Prefer creating new Spring Boot classes instead of heavily modifying existing domain classes.

---

# Repository Scope

Focus primarily on:

```text
Sprint3Implementation/
```

First inspect the complete contents of this folder.

Identify:

1. `Application.java`
2. Core Santorini game/domain classes
3. GUI-related classes
4. Controllers or game coordinators
5. Board implementation
6. Player and worker classes
7. God power classes
8. Win-condition logic
9. Move/build validation
10. Any resources such as images or assets

Do not make changes to other sprint folders or unrelated coursework folders just because similar Java classes exist there.

Treat `Sprint3Implementation` as the authoritative version of the game unless clearly proven otherwise.

---

# Phase 1 — Analyse Before Editing

Before modifying any code:

1. Read the entire `Sprint3Implementation` project structure.
2. Locate `Application.java`.
3. Understand how the program starts.
4. Identify which classes contain core game logic.
5. Identify which classes contain Swing, JavaFX, terminal UI, or other presentation-specific code.
6. Identify dependencies between GUI code and game logic.
7. Determine which Java classes can be reused unchanged.
8. Determine which classes need small refactoring to remove GUI dependencies.
9. Determine which GUI classes will eventually be replaced by React.
10. Propose the migration structure.

Do not modify files during this analysis phase.

Provide a short migration plan before implementation.

---

# Phase 2 — Create the Web Project Structure

Create the web implementation within or alongside `Sprint3Implementation` in a clean structure such as:

```text
Sprint3Implementation/
├── backend/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── ...
│
├── frontend/
│   ├── package.json
│   ├── src/
│   └── ...
│
└── existing Java source if needed during migration
```

If a different structure is clearly better for the existing repository, explain the reason before using it.

Do not delete the existing desktop implementation during the migration.

Keep the original version available until the web version has been verified.

---

# Phase 3 — Java Spring Boot Backend

Use:

* Java
* Spring Boot
* Maven

Create a Spring Boot backend.

Reuse the existing Santorini Java classes wherever possible.

The Java backend must remain the source of truth for all game rules.

Do not duplicate game-rule logic in React.

Suggested backend layers:

```text
controller/
service/
dto/
game/
```

Example responsibilities:

```text
controller/
    HTTP REST endpoints

service/
    game session orchestration

dto/
    request and response objects

game/
    existing Santorini rules and domain logic
```

Keep controllers thin.

Game rules should remain inside the existing Java domain/game classes where possible.

---

# Backend REST API

Create REST endpoints appropriate for the current implementation.

At minimum, support functionality equivalent to the existing game.

Suggested endpoints:

```text
POST /api/game
GET  /api/game

POST /api/game/move
POST /api/game/build

POST /api/game/power
POST /api/game/restart

GET /api/health
```

Only implement endpoints that make sense for the existing game flow.

If worker selection, god selection, player setup, or other stages need dedicated endpoints, add them appropriately.

Use DTOs rather than exposing complicated internal Java objects directly.

---

# Game State Response

The frontend should receive enough information to render the complete current game state.

This may include:

* Board size
* Tile positions
* Building levels
* Domes
* Worker positions
* Worker owners
* Current player
* Current phase
* Selected worker
* Available valid moves
* Available valid builds
* God powers
* Player names
* Winner
* Game-over status
* Error or status messages

Do not calculate authoritative legal moves independently in React if the Java backend already provides or can determine them.

---

# Phase 4 — React Frontend

Create a React frontend using JavaScript.

Do not use Python.

The frontend should reproduce the functionality of the current Santorini desktop interface.

Suggested React components may include:

```text
App
GameBoard
BoardCell
Worker
PlayerPanel
GodPowerPanel
GameStatus
GameControls
RestartButton
```

The exact component structure can be adjusted based on the current game.

React should handle:

* Rendering
* User interaction
* Sending API requests
* Displaying backend responses
* Local UI state when appropriate

React should NOT become the source of truth for:

* movement legality
* build legality
* win conditions
* god-power rules
* turn validation

Those rules must continue to be handled by Java.

---

# Frontend API Configuration

Do not hard-code production backend URLs.

Use an environment variable such as:

```text
VITE_API_URL
```

For local development, support something like:

```text
http://localhost:8080
```

For production, allow Render to provide the backend URL.

Add an example environment file:

```text
frontend/.env.example
```

Example:

```text
VITE_API_URL=http://localhost:8080
```

Do not commit secrets.

---

# CORS

Configure Spring Boot CORS so that:

* Local React development works.
* The deployed Render frontend can call the deployed Render backend.

Do not simply allow every origin in production unless necessary.

Use environment-based configuration if practical.

---

# Render Deployment

The final project should be deployable as:

## Backend

Render Web Service

Expected characteristics:

```text
Root Directory:
Sprint3Implementation/backend

Build Command:
appropriate Maven build command

Start Command:
appropriate Java/Spring Boot start command
```

Configure the Spring Boot application correctly for Render.

The application must listen on Render's provided port.

Support:

```text
PORT
```

through Spring Boot configuration.

Do not hard-code the production port.

---

## Frontend

Render Static Site

Expected characteristics:

```text
Root Directory:
Sprint3Implementation/frontend

Build Command:
npm install && npm run build

Publish Directory:
dist
```

If the chosen React tooling produces a different build directory, use the correct one.

The frontend must use the deployed backend URL through `VITE_API_URL`.

---

# Render Configuration

If useful, add:

```text
render.yaml
```

to simplify deployment.

Do not add unnecessary infrastructure.

At the end, provide the exact Render settings required for both services:

### Backend

* Service type
* Root Directory
* Build Command
* Start Command
* Required environment variables

### Frontend

* Service type
* Root Directory
* Build Command
* Publish Directory
* Required environment variables

---

# Existing Java GUI

Do not delete the current GUI immediately.

First:

1. Separate GUI dependencies from game logic.
2. Build the Spring Boot API.
3. Verify the Java backend.
4. Build the React frontend.
5. Verify the complete web game.

Only after the web version works should obsolete GUI code be considered for removal.

If possible, keep the existing desktop version functional during migration.

---

# Code Modification Rules

When modifying existing Java code:

1. Keep changes minimal.
2. Preserve existing behaviour.
3. Avoid rewriting working algorithms.
4. Avoid renaming large numbers of classes unnecessarily.
5. Avoid changing public APIs unless required.
6. Do not duplicate existing rules.
7. Prefer adapters, services, DTOs, or wrapper classes.
8. Keep commits or changes logically separated where possible.

Do not modify unrelated repository folders.

---

# Validation After Each Major Stage

After backend setup:

```text
mvn clean test
```

or the appropriate Maven build command.

Fix compilation errors before continuing.

After frontend setup:

```text
npm install
npm run build
```

Fix all build errors before continuing.

Then verify frontend-to-backend communication.

---

# Functional Testing

Verify that the web version preserves the existing Santorini functionality.

Test at least:

1. Application/game creation
2. Player setup
3. Worker selection
4. Worker movement
5. Invalid movement rejection
6. Building
7. Invalid build rejection
8. Turn switching
9. God powers
10. Win-condition detection
11. Game-over behaviour
12. Restart/new game behaviour

Only test features that exist in the original implementation.

Do not silently remove existing functionality.

---

# Final Deliverables

After implementation, provide:

## 1. Architecture summary

Explain the final structure:

```text
React
→ REST API
→ Spring Boot
→ Existing Santorini game engine
```

## 2. Changed Java files

List every existing Java file that was modified.

For each one, briefly state why the modification was necessary.

## 3. New files

List the important new backend and frontend files.

## 4. Preserved logic

Identify which original Santorini classes were reused without major changes.

## 5. Build instructions

Provide exact commands for:

```text
local backend
local frontend
production build
```

## 6. Render instructions

Provide the exact settings needed to deploy:

```text
React → Render Static Site
Spring Boot → Render Web Service
```

## 7. Remaining issues

Clearly identify any functionality from the original desktop game that has not yet been reproduced in the web version.

Do not claim migration is complete if any important existing functionality is missing.

---

# Final Priority

The most important requirement is:

> Preserve the existing working Santorini Java game logic while replacing the desktop UI with a React frontend and exposing the Java logic through a Spring Boot REST API.

Do not perform a full rewrite unless the existing architecture makes reuse technically impossible.

Focus on `Sprint3Implementation` and `Application.java` as the starting point.
