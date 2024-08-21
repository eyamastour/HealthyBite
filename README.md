# HealthyBite - Backend

## Description

**HealthyBite** est une application dédiée à la nutrition et au suivi de plans de repas personnalisés. Ce dépôt contient le backend du projet, développé avec **Spring Boot** et utilisant **MySQL** comme base de données. Le backend fournit des API RESTful robustes pour gérer les données relatives aux utilisateurs, aux plans de repas, et aux suivis nutritionnels.

## Fonctionnalités Principales

- **Gestion des Utilisateurs :** Création, mise à jour et suppression des profils utilisateurs, y compris les préférences alimentaires et les objectifs de santé.
- **Plans de Repas Personnalisés :** Génération de plans de repas en fonction des préférences des utilisateurs et des objectifs nutritionnels.
- **Suivi Nutritionnel :** Enregistrement et consultation des apports nutritionnels quotidiens des utilisateurs.
- **Gestion des Recettes :** Stockage et récupération des recettes saines correspondant aux plans de repas.
- **Sécurité :** Authentification et autorisation sécurisées via JWT (JSON Web Tokens).

## Technologies Utilisées

- **Framework Backend :** Spring Boot
- **Base de données :** MySQL
- **Authentification :** JWT (JSON Web Tokens)
- **Gestion des dépendances :** Maven

## Structure du Projet

- **`src/` :** Contient le code source Java de l'application.
- **`resources/` :** Contient les fichiers de configuration, y compris `application.properties`.
- **`pom.xml` :** Fichier de configuration Maven pour la gestion des dépendances et des plugins.

## Installation

### Prérequis

- **Java Development Kit (JDK) 11+**
- **MySQL Server**
- **Maven**

### Cloner le Dépôt

```bash
git clone https://github.com/your-username/HealthyBite.git
cd HealthyBite
