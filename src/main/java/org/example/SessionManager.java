/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : SessionManager.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Creates, loads, and clears the user session on disk.
 * FR: Crée, charge et supprime la session utilisateur sur disque.
 */

package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;

public class SessionManager {

    private final Path sessionFile;

    public SessionManager(Path baseDir) {
        this.sessionFile = AppPaths.sessionFile(baseDir);
    }

    public void saveSession(String username) {
        try {
            Session s = new Session(username, OffsetDateTime.now().toString());
            Files.createDirectories(sessionFile.getParent());
            JsonUtil.mapper().writeValue(sessionFile.toFile(), s);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save session", e);
        }
    }

    public Session loadSession() {
        try {
            if (!Files.exists(sessionFile)) return null;
            return JsonUtil.mapper().readValue(sessionFile.toFile(), Session.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void clearSession() {
        try {
            Files.deleteIfExists(sessionFile);
        } catch (Exception e) {
            // ignore
        }
    }
}
