/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : Task.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Model representing a single to-do item for an idea.
 * FR: Modèle représentant une tâche (to-do) associée à une idée.
 */

package org.example;

import java.util.Objects;

public class Task {

    private String id;
    private String title;
    private boolean done;

    // EN: Default constructor for JSON.
    // FR: Constructeur par défaut pour JSON.
    public Task() {}

    public Task(String id, String title) {
        this.id = id;
        this.title = title;
        this.done = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    @Override
    public String toString() {
        // EN: Display with a checkmark-like prefix.
        // FR: Affichage avec un préfixe de type "check".
        return (done ? "[Done] " : "[Todo] ") + title;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
