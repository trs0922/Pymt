package com.training.application.controller;
import com.training.application.domain.Payment;
import com.training.application.entity.PaymentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.training.application.service.PaymentService;

import java.util.Map;

@RestController
public class PaymentController {
	@Autowired
	PaymentService paymentService;
    @RequestMapping(value = "/payment-service/make-payment", method = RequestMethod.POST)
    public ResponseEntity makePayment(@RequestBody Payment payment) {
        //System.out.println("Am in make payment method:" + payment);
        PaymentEntity paymentEntity =  paymentService.makePayment(payment);
       if(paymentEntity == null) {
    	   ResponseEntity response = new ResponseEntity("Validation fail,Please give the proper value",HttpStatus.BAD_REQUEST);
           return response;
       }else {
        ResponseEntity response = new ResponseEntity("Successfully payment done.Your order reference number is:" + payment.getReferenceNumber(),HttpStatus.OK);
        return response;
       }
    }
    @RequestMapping(value = "payment-service/get-payment",method = RequestMethod.GET)
    public ResponseEntity getPayment(@RequestParam(name ="id")int id) {
    	Payment payment = paymentService.getPayment(id);
    	if(payment == null) {
    		ResponseEntity response = new ResponseEntity("There is no payment with the given id",HttpStatus.NOT_FOUND);
    		return response;
    	}else {
    		return ResponseEntity.status(HttpStatus.OK).body(Map.of("Your paid amount:",  "payment.getAmount()",
                                                                    "Your reference number:", "payment.getReferenceNumber()",
                                                                     "Your payment date:", "payment.getPaymentDate()"));
    	}
    }


}