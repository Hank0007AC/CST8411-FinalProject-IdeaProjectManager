/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : ToolSuggestion.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Holds recommended tools and AI assistants.
 * FR: Contient les outils et IA recommandés.
 */

package org.example;

import java.util.ArrayList;
import java.util.List;

public class ToolSuggestion {

    private List<String> tools = new ArrayList<>();
    private List<String> aiAssistants = new ArrayList<>();

    public ToolSuggestion() {}

    public ToolSuggestion(List<String> tools, List<String> aiAssistants) {
        this.tools = (tools == null) ? new ArrayList<>() : tools;
        this.aiAssistants = (aiAssistants == null) ? new ArrayList<>() : aiAssistants;
    }

    public List<String> getTools() {
        return tools;
    }

    public List<String> getAiAssistants() {
        return aiAssistants;
    }

    public void setTools(List<String> tools) {
        this.tools = tools;
    }

    public void setAiAssistants(List<String> aiAssistants) {
        this.aiAssistants = aiAssistants;
    }

    /**
     * EN: Build a readable display text.
     * FR: Construit un texte lisible pour affichage.
     */
    public String toDisplayText() {
        StringBuilder sb = new StringBuilder();

        sb.append("Recommended tools:\n");
        for (String t : tools) {
            sb.append(" - ").append(t).append("\n");
        }

        sb.append("\nRecommended AI assistants:\n");
        for (String a : aiAssistants) {
            sb.append(" - ").append(a).append("\n");
        }

        return sb.toString();
    }
}
