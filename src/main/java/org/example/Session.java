/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : Session.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Represents a logged-in session for a user.
 * FR: Représente une session connectée pour un utilisateur.
 */

package org.example;

public class Session {

    private String username;
    private String createdAtIso;

    public Session() {}

    public Session(String username, String createdAtIso) {
        this.username = username;
        this.createdAtIso = createdAtIso;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCreatedAtIso() { return createdAtIso; }
    public void setCreatedAtIso(String createdAtIso) { this.createdAtIso = createdAtIso; }
}
