/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : IntegrationFlowTest.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Integration test validating the full workflow: register -> login -> session -> save/load ideas.
 * FR: Test d'intégration validant le flux complet : register -> login -> session -> sauvegarde/chargement idées.
 */

package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationFlowTest {

    @TempDir
    Path tmp;

    @Test
    void fullWorkflow() {
        UserStore userStore = new UserStore(tmp);
        SessionManager sessionManager = new SessionManager(tmp);
        IdeaStore ideaStore = new IdeaStore(tmp);

        assertTrue(userStore.register("demo_user", "pass123".toCharArray()));
        assertTrue(userStore.authenticate("demo_user", "pass123".toCharArray()));

        sessionManager.saveSession("demo_user");
        assertNotNull(sessionManager.loadSession());

        Idea idea = new Idea(IdUtil.newIdeaId(), "Integration", "Website");
        idea.setTasks(new SuggestionEngine().generateDefaultTasks(idea.getId(), idea.getCategory()));

        ideaStore.saveIdeas("demo_user", List.of(idea));

        List<Idea> loaded = ideaStore.loadIdeas("demo_user");
        assertEquals(1, loaded.size());
        assertFalse(loaded.get(0).getTasks().isEmpty());
    }
}
