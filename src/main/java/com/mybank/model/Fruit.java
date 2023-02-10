package com.mybank.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.*;

@Entity
@Cacheable
public class Fruit extends PanacheEntity {

    /* Change to private */
    @Column(length = 40, unique = true)
    public String name;

}
