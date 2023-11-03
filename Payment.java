package com.training.application.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class Payment {
    int id;
    Card card;
    String name;
    int amount;
    int referenceNumber;
    String paymentDate;
}