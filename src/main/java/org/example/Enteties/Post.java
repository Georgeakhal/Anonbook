package org.example.Enteties;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"Post\"")
public class Post {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "head", nullable = false)
    private String head;
    @Column(name = "img")
    private String img;

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
}