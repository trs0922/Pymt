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

    @Column(name = "card_type")
    String cardType;

    @Column(name = "card_holder_name")
    String cardHolderName;

    @Column(name = "card_number")
    String cardNumber;

    @Column(name = "cvc_number")
    String cvcNumber;

    @Column(name = "expire_date")
    String expireDate;

    @Column(name = "amount")
    int amount;

    @Column(name = "created_time")
    @CreationTimestamp
    Timestamp createdTime;

    @Column(name = "updated_time")
    @UpdateTimestamp
    Timestamp updatedTime;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "updated_by")
    String updatedBy;

    @Column(name = "reference_number")
    int referenceNumber;

    @Column(name = "payment_date")
    String paymentDate;


}
