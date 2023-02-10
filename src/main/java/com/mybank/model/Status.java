package com.mybank.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Cacheable
public class Status extends PanacheEntityBase {

    private static final Logger logger = LoggerFactory.getLogger(Status.class);



    @Id @Column(name = "id", nullable = false) public Long id;

    @Column
    public String reference;


    @Column
    public String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Status convertStatus(Status status){
        status.setStatus("nuevo");
        return status;
    }


}