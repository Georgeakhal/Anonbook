package org.example.Enteties;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Table(name = "\"Comment\"")
public class Comment {
    @Id
    @JoinColumn(name = "\"post\"", referencedColumnName = "id")
    @Column(name = "\"postId\"", nullable = false)
    private String postId;
    @Column(name = "text", nullable = true)
    private String text;
    @Column(name = "date", nullable = true)
    private Date date;

    public Comment() {
        setDateToNow();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDateToNow() {
        this.date = Date.valueOf(LocalDate.now());
    }
}
