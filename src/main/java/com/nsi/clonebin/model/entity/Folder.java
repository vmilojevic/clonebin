package com.nsi.clonebin.model.entity;

//@Entity
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "folder")
@Data
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", nullable = false)
    private Timestamp cratedAt;

    public Folder(UUID userId, String name, Timestamp cratedAt) {
        this.userId = userId;
        this.name = name;
        this.cratedAt = cratedAt;
    }

}
