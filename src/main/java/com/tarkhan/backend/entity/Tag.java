package com.tarkhan.backend.entity;

import com.tarkhan.backend.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tag extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Book> books;
}
