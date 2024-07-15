package com.ispan.warashibe.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Rank")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rankID")
    private Integer rankID;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "memberID", referencedColumnName = "memberID")
    private Members member;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "rankmsg")
    private String rankmsg;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rankDate")
    private Date rankDate;

    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Orders order;
}
