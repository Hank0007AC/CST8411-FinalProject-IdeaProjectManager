/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : SessionManagerTest.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Unit tests for session save/load/clear.
 * FR: Tests unitaires pour sauvegarde/chargement/suppression de session.
 */

package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {

    @TempDir
    Path tmp;

    @Test
    void saveLoadClearSession() {
        SessionManager sm = new SessionManager(tmp);

        assertNull(sm.loadSession());

        sm.saveSession("rachid_1");
        Session s = sm.loadSession();
        assertNotNull(s);
        assertEquals("rachid_1", s.getUsername());
        assertNotNull(s.getCreatedAtIso());

        sm.clearSession();
        assertNull(sm.loadSession());
    }
}
