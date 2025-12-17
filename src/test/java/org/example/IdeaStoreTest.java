/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : IdeaStoreTest.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Unit tests for per-user idea persistence.
 * FR: Tests unitaires pour persistance des idées par utilisateur.
 */

package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IdeaStoreTest {

    @TempDir
    Path tmp;

    @Test
    void saveThenLoadIdeas() {
        IdeaStore store = new IdeaStore(tmp);

        List<Idea> ideas = new ArrayList<>();
        Idea i = new Idea(IdUtil.newIdeaId(), "Test Idea", "Marketing");
        i.addTask(new Task(IdUtil.taskId(i.getId(), 1), "Task 1"));
        ideas.add(i);

        store.saveIdeas("user1", ideas);

        List<Idea> loaded = store.loadIdeas("user1");
        assertEquals(1, loaded.size());
        assertEquals("Test Idea", loaded.get(0).getTitle());
        assertEquals(1, loaded.get(0).getTasks().size());
    }
}
