/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : IdUtil.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Utility for generating stable ids for ideas and tasks.
 * FR: Utilitaire pour générer des identifiants stables pour les idées et tâches.
 */

package org.example;

import java.util.UUID;

public final class IdUtil {
    private IdUtil() {}

    // EN: Create a new idea id.
    // FR: Créer un nouvel id d'idée.
    public static String newIdeaId() {
        return "IDEA-" + UUID.randomUUID();
    }

    // EN: Create a deterministic task id based on idea + index.
    // FR: Créer un id de tâche déterministe basé sur idée + index.
    public static String taskId(String ideaId, int index) {
        return ideaId + "-TASK-" + index;
    }
}
