package br.com.leandro.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "MESSAGE")
public class Message implements Serializable {
    @Id
    @Column(name = "ID")
    public String id;

    @Column(name = "TEXT")
    public String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
