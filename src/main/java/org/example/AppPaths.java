/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : AppPaths.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Centralizes application storage paths (users, sessions, and idea files).
 * FR: Centralise les chemins de stockage (utilisateurs, session, fichiers d'idées).
 */

package org.example;

import java.nio.file.Files;
import java.nio.file.Path;

public final class AppPaths {

    private AppPaths() {}

    /**
     * EN: Base directory for local app data (cross-platform).
     * FR: Dossier de base des données locales (multi-plateforme).
     */
    public static Path baseDir() {
        String home = System.getProperty("user.home");
        Path base = Path.of(home, ".idea-project-manager");
        ensureDir(base);
        return base;
    }

    public static Path usersFile(Path baseDir) {
        return baseDir.resolve("users.json");
    }

    public static Path sessionFile(Path baseDir) {
        return baseDir.resolve("session.json");
    }

    public static Path ideasFile(Path baseDir, String username) {
        // EN: Per-user file.
        // FR: Fichier par utilisateur.
        return baseDir.resolve("ideas_" + username + ".json");
    }

    private static void ensureDir(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create app directory: " + dir, e);
        }
    }
}
