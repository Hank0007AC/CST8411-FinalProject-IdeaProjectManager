/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : UserStore.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Manages user registration and authentication using local JSON storage.
 * FR: Gère l'inscription et l'authentification via un stockage JSON local.
 */

package org.example;

import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UserStore {

    private final Path usersFile;

    public UserStore(Path baseDir) {
        this.usersFile = AppPaths.usersFile(baseDir);
    }

    /**
     * EN: Register a new user.
     * FR: Inscrire un nouvel utilisateur.
     *
     * @return true if created, false if username already exists or invalid.
     */
    public boolean register(String username, char[] password) {
        String u = ValidationUtil.normalizeUsername(username);
        if (u == null) return false;
        if (!ValidationUtil.isStrongEnough(password)) return false;

        List<User> users = loadAll();
        if (users.stream().anyMatch(x -> x.getUsername().equalsIgnoreCase(u))) return false;

        String hash = PasswordUtil.hashPassword(password);
        users.add(new User(u, hash));
        saveAll(users);
        return true;
    }

    /**
     * EN: Authenticate and return true if credentials are correct.
     * FR: Authentifie et retourne true si les identifiants sont corrects.
     */
    public boolean authenticate(String username, char[] password) {
        String u = ValidationUtil.normalizeUsername(username);
        if (u == null) return false;

        List<User> users = loadAll();
        User found = users.stream()
                .filter(x -> x.getUsername().equalsIgnoreCase(u))
                .findFirst().orElse(null);
        if (found == null) return false;

        return PasswordUtil.verifyPassword(password, found.getPasswordHash());
    }

    public boolean exists(String username) {
        String u = ValidationUtil.normalizeUsername(username);
        if (u == null) return false;
        return loadAll().stream().anyMatch(x -> x.getUsername().equalsIgnoreCase(u));
    }

    private List<User> loadAll() {
        try {
            if (!Files.exists(usersFile)) return new ArrayList<>();
            byte[] json = Files.readAllBytes(usersFile);
            if (json.length == 0) return new ArrayList<>();
            return JsonUtil.mapper().readValue(json, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            // EN: If file is corrupted, we fail safely with empty list.
            // FR: Si le fichier est corrompu, on échoue en sécurité (liste vide).
            return new ArrayList<>();
        }
    }

    private void saveAll(List<User> users) {
        try {
            Files.createDirectories(usersFile.getParent());
            JsonUtil.mapper().writeValue(usersFile.toFile(), users);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save users", e);
        }
    }
}
