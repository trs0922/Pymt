package com.training.application.controller;
import com.training.application.domain.Payment;
import com.training.application.entity.PaymentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.training.application.service.PaymentService;

import java.util.Map;

@RestController
@RequestMapping(value = "/payment-service")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @RequestMapping(value = "/make-payment", method = RequestMethod.POST)
    public ResponseEntity makePayment(@RequestBody Payment payment) {
        //System.out.println("Am in make payment method:" + payment);
        try {
            PaymentEntity paymentEntity = paymentService.makePayment(payment);
            if (paymentEntity != null) {
                ResponseEntity response = new ResponseEntity("Successfully payment done.Your order reference number is:" + payment.getReferenceNumber(), HttpStatus.OK);
                return response;
            } else {
                ResponseEntity response = new ResponseEntity("Validation fail,Please give the proper value", HttpStatus.BAD_REQUEST);
                return response;
            }
        } catch (Exception e) {
            System.out.println("Given input is not correct");
        }
        return null;
    }

    @RequestMapping(value = "/get-payment", method = RequestMethod.GET)
    public ResponseEntity getPayment(@RequestParam(name = "id") int id) {
        try {
            Payment payment = paymentService.getPayment(id);
            if (payment != null) {
                return ResponseEntity.status(HttpStatus.OK).body(payment);
            } else {
                ResponseEntity response = new ResponseEntity("There is no payment with the given id", HttpStatus.NOT_FOUND);
                return response;
            }
        } catch (Exception e) {
            System.out.println("The given id is not available");
        }
        return null;
    }

    @RequestMapping(value = "/update-payment", method = RequestMethod.PUT)
    public ResponseEntity updatePayment(@RequestBody Payment payment) {
        try {
            paymentService.updatePayment(payment);
            ResponseEntity response = new ResponseEntity("Payment successfully updated", HttpStatus.OK);
            return response;
        } catch (Exception e) {
            ResponseEntity response = new ResponseEntity("Payment updated not success", HttpStatus.BAD_REQUEST);
            return response;
        }
    }


    @RequestMapping(value = "/delete-payment",method =RequestMethod.DELETE)
    public ResponseEntity deletePayment(@RequestParam(name = "id")int id){
        try{
            paymentService.deletePayment(id);
            ResponseEntity response = new ResponseEntity("Your payment detail was successfully deleted",HttpStatus.OK);
            return response;
        }catch(Exception e){
             ResponseEntity response = new ResponseEntity("Please give valid id",HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}