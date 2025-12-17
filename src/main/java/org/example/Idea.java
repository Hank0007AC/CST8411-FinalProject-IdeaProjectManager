/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : Idea.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Model representing a project idea (title, category) and its to-do tasks.
 * FR: Modèle représentant une idée de projet (titre, catégorie) et ses tâches.
 */

package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * EN: Plain Java model (no JavaFX dependency) to simplify persistence and testing.
 * FR: Modèle Java pur (sans dépendance JavaFX) pour simplifier la persistance et les tests.
 */
public class Idea {

    // EN: Unique identifier (generated locally).
    // FR: Identifiant unique (généré localement).
    private String id;

    // EN: Idea title entered by the user.
    // FR: Titre de l'idée saisi par l'utilisateur.
    private String title;

    // EN: Category selected by the user (e.g., Marketing, Website, E-commerce).
    // FR: Catégorie sélectionnée par l'utilisateur (ex : Marketing, Site Web, E-commerce).
    private String category;

    // EN: List of tasks for this idea.
    // FR: Liste des tâches associées à cette idée.
    private List<Task> tasks = new ArrayList<>();

    // EN: Default constructor required by Jackson (JSON).
    // FR: Constructeur par défaut requis par Jackson (JSON).
    public Idea() {}

    public Idea(String id, String title, String category) {
        this.id = id;
        this.title = title;
        this.category = category;
    }

    // ---------------------------
    // Getters / Setters (JSON)
    // ---------------------------

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) {
        this.tasks = (tasks == null) ? new ArrayList<>() : tasks;
    }

    // ---------------------------
    // Convenience helpers
    // ---------------------------

    // EN: Add a task to this idea.
    // FR: Ajoute une tâche à cette idée.
    public void addTask(Task task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    // EN: Remove a task by id.
    // FR: Supprime une tâche par son id.
    public boolean removeTaskById(String taskId) {
        return tasks.removeIf(t -> Objects.equals(t.getId(), taskId));
    }

    @Override
    public String toString() {
        // EN: Used by JavaFX ListView to display the idea.
        // FR: Utilisé par la ListView JavaFX pour afficher l'idée.
        return title + " (" + category + ")";
    }
}
