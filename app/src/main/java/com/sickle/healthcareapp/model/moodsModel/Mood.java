package com.sickle.healthcareapp.model.moodsModel;

public class Mood {

    private int id;
    private String selectedMood;
    private int selectedMoodLevel;
    private String selectedMoodNote;
    private String selectedMoodNoteDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelectedMood() {
        return selectedMood;
    }

    public void setSelectedMood(String selectedMood) {
        this.selectedMood = selectedMood;
    }

    public int getSelectedMoodLevel() {
        return selectedMoodLevel;
    }

    public void setSelectedMoodLevel(int selectedMoodLevel) {
        this.selectedMoodLevel = selectedMoodLevel;
    }

    public String getSelectedMoodNote() {
        return selectedMoodNote;
    }

    public void setSelectedMoodNote(String selectedMoodNote) {
        this.selectedMoodNote = selectedMoodNote;
    }

    public String getSelectedMoodNoteDate() {
        return selectedMoodNoteDate;
    }

    public void setSelectedMoodNoteDate(String selectedMoodNoteDate) {
        this.selectedMoodNoteDate = selectedMoodNoteDate;
    }
}
