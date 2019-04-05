package org.fasttrackit.Transfer;

import java.util.Date;

public class SaveToDoItemRequest {
    private Date deadline;
    private String description;
    private boolean done;

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "SaveToDoItemRequest{" +
                "deadline=" + deadline +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}

