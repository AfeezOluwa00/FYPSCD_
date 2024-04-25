package com.sickle.healthcareapp.model.painModels;

public class PainModel {

    private int painLevel;
    private String painNotes;
    private String painAreasList;
    private String painNoteDate;
    private String currentPainEmotion;


    public int getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(int painLevel) {
        this.painLevel = painLevel;
    }

    public String getPainNotes() {
        return painNotes;
    }

    public void setPainNotes(String painNotes) {
        this.painNotes = painNotes;
    }

    public String getPainAreasList() {
        return painAreasList;
    }

    public void setPainAreasList(String painAreasList) {
        this.painAreasList = painAreasList;
    }

    public String getPainNoteDate() {
        return painNoteDate;
    }

    public void setPainNoteDate(String painNoteDate) {
        this.painNoteDate = painNoteDate;
    }

    public String getCurrentPainEmotion() {
        return currentPainEmotion;
    }

    public void setCurrentPainEmotion(String currentPainEmotion) {
        this.currentPainEmotion = currentPainEmotion;
    }
}
