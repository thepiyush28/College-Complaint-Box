package com.example.complaintbox;

public class Complaint {

    private final long id;
    private final String complaintId;
    private final String studentName;
    private final String category;
    private final String title;
    private final String description;
    private final String date;
    private final String status;

    public Complaint(long id, String complaintId, String studentName, String category, String title,
                     String description, String date, String status) {
        this.id = id;
        this.complaintId = complaintId;
        this.studentName = studentName;
        this.category = category;
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
