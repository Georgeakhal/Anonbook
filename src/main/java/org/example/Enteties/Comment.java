package org.example.Enteties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Table(name = "\"Comment\"")
public class Comment {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "\"postId\"", nullable = false)
    private String postId;
    @Column(name = "text")
    private String text;

    @JsonIgnore
    @Column(name = "date")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateToNow() {
        this.date = Date.valueOf(LocalDate.now());
    }
}
