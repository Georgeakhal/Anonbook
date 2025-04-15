package org.example.Enteties;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"Post\"")
public class Post {
    @JsonIgnore
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "head", nullable = false)
    private String head;
    @Column(name = "img")
    private String img;

    @JsonIgnore
    @Column(name = "\"dateTime\"")
    private Timestamp dateTime;

    public Post() {
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTimeToNow() {
        this.dateTime = Timestamp.valueOf(LocalDateTime.now());
    }
}