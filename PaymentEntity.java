package com.training.application.entity;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.ToString;
@ToString
@Data
@Entity
@Table(name = "pymt_detail",schema = "pro")
public class PaymentEntity {
    @Id
    @Column(name = "Id")
    int id;

    @Column(name = "cardType")
    String cardType;

    @Column(name = "cardHolderName")
    String cardHolderName;

    @Column(name = "cardNumber")
    String cardNumber;

    @Column(name = "cvcNumber")
    String cvcNumber;

    @Column(name = "expireDate")
    String expireDate;

    @Column(name = "amount")
    int amount;

    @Column(name = "createdTime")
    @CreationTimestamp
    Timestamp createdTime;

    @Column(name = "updatedTime")
    @UpdateTimestamp
    Timestamp updatedTime;

    @Column(name = "createdBy")
    String createdBy;

    @Column(name = "updatedBy")
    String updatedBy;

    @Column(name = "referenceNumber")
    int referenceNumber;

    @Column(name = "paymentDate")
    String paymentDate;


}
