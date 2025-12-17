/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : IdeaStore.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Persists ideas and tasks per user using JSON files.
 * FR: Persiste les idées et tâches par utilisateur via des fichiers JSON.
 */

package org.example;

import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IdeaStore {

    private final Path baseDir;

    public IdeaStore(Path baseDir) {
        this.baseDir = baseDir;
    }

    public List<Idea> loadIdeas(String username) {
        try {
            Path f = AppPaths.ideasFile(baseDir, username);
            if (!Files.exists(f)) return new ArrayList<>();
            return JsonUtil.mapper().readValue(f.toFile(), new TypeReference<List<Idea>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveIdeas(String username, List<Idea> ideas) {
        try {
            Path f = AppPaths.ideasFile(baseDir, username);
            Files.createDirectories(f.getParent());
            JsonUtil.mapper().writeValue(f.toFile(), ideas);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save ideas", e);
        }
    }
}
