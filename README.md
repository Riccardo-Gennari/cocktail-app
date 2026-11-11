# CocktailApp

[![Android CI](https://github.com/Riccardo-Gennari/cocktail-app/actions/workflows/ci.yml/badge.svg)](https://github.com/Riccardo-Gennari/cocktail-app/actions/workflows/ci.yml)

The app presents a **search screen**, allowing users to browse cocktails by name or ingredient.

It also includes a **detail page**, offering additional info and instructions for cocktail preparation.

<img width="2170" height="2270" alt="preview" src="https://github.com/user-attachments/assets/e7df2de8-a991-4da8-b29f-2e007a8435ce" />

## Tech Features
- **Compose UI** using Material 3 and Navigation 3.
- **Data persistence** using Room 3, for fast retrieval and offline access.
- **Dependency Injection** using Hilt.
- **Network Error Handling** with appropriate UI feedback.
- **Unit tests** using Kotest, MockK, Turbine and Kover.
- **Ui tests** for Compose, using Robolectric and JUnit

---

## Architecture and patterns

The app follows **Clean Architecture**, **UDF** and **MVVM**, in order to keep the codebase easy to understand, maintain and expand.

The codebase is structured as such:

```mermaid
graph TD
    subgraph UI["Presentation Layer"]
        Screens["Compose Screens<br/>(List & Detail)"]
        VMs["ViewModels & UI State"]
        Nav["Navigation 3"]
    end

    subgraph Domain["Domain Layer"]
        UseCases["Use Cases<br/>(Search, Get Detail, Get Last)"]
        RepoInterfaces["Repository Interfaces"]
        Entities["Domain Entities"]
    end

    subgraph Data["Data Layer"]
        Repos["Default Repositories"]
        Room["Room 3 (Local DB)"]
        Ktor["Ktor 3 (Network Client)"]
    end

    API[("TheCocktailDB API")]

    %% Relationships
    Screens --> VMs
    Screens --> Nav
    VMs --> UseCases
    UseCases --> RepoInterfaces
    UseCases --> Entities
    Repos -->|"implements"| RepoInterfaces
    Repos --> Room
    Repos --> Ktor
    Ktor --> API

    %% Styling
    classDef presStyle fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#0d47a1
    classDef domainStyle fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#1b5e20
    classDef dataStyle fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#e65100
    classDef extStyle fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#b71c1c

    class UI,Screens,VMs,Nav presStyle
    class Domain,UseCases,RepoInterfaces,Entities domainStyle
    class Data,Repos,Room,Ktor dataStyle
    class API extStyle
```