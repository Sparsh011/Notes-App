package com.example.notesapp;

public class firebasemodel {
    private String title;
    private String content;
//    NOTE - the names of the variables above should be the same as the key of the following data which is added otherwise it wont work.
//    Map<String, Object> note = new HashMap<>();
//                    note.put("title", title);
//                    note.put("content", content);
//    this is present in CreateNewNote.java

    public firebasemodel(){
//        empty constructor, it is required by firestore
    }

    public firebasemodel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
