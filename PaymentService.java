package com.training.application.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Optional;
import com.training.application.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.training.application.domain.Card;
import com.training.application.domain.Payment;
import com.training.application.repository.PaymentRepository;
@Service
public class PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	public PaymentEntity makePayment(Payment payment) {
		Card cd = payment.getCard();
		int id = payment.getId();
		String cardNumber = cd.getCardNumber();
		String cardType = cd.getCardType();
		String cardHolderName = cd.getCardHolderName();
		String cvcNumber = cd.getCvcNumber();
		int amount = payment.getAmount();
		String name = payment.getName();
		int referenceNumber = payment.getReferenceNumber();
		String expireDate = cd.getExpireDate();
		if (expireDate == null || expireDate.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a non-empty date.");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		Date ed = null;
		try {
            ed = sdf.parse(expireDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter the date in mm/dd/yyyy format.");
		}
		Date currentDate = new Date();
	    int compareDate = currentDate.compareTo(ed);
		if((cardNumber.matches("\\d{16}")) && (cvcNumber.matches("\\d{3}")) && amount > 0 && compareDate < 0) {
			System.out.println("Valid card");
		    PaymentEntity paymentEntity = new PaymentEntity();
			paymentEntity.setId(payment.getId());
			paymentEntity.setCardType(cd.getCardType());
			paymentEntity.setCardHolderName(cd.getCardHolderName());
			paymentEntity.setCardNumber(cd.getCardNumber());
			paymentEntity.setCvcNumber(cd.getCvcNumber());
			paymentEntity.setExpireDate(cd.getExpireDate());
			paymentEntity.setAmount(payment.getAmount());
			paymentEntity.setCreatedTime(null);
			paymentEntity.setUpdatedTime(null);
			paymentEntity.setCreatedBy(payment.getName());
			paymentEntity.setUpdatedBy(payment.getName());
			paymentEntity.setReferenceNumber(payment.getReferenceNumber());
			paymentRepository.save(paymentEntity);
			System.out.println("Payment is done");
			return paymentEntity;
		}else {
			System.out.println("Invalid input,Please give valid input");
			throw new InputMismatchException();
			}

	}
	
	public Payment getPayment(int id) {
	Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findById(id);
	if(optionalPaymentEntity.isPresent()) {
		PaymentEntity paymentEntity = optionalPaymentEntity.get();
		System.out.println(paymentEntity);
		Payment payment = new Payment();
		payment.setId(paymentEntity.getId());
		payment.setAmount(paymentEntity.getAmount());
		payment.setPaymentDate(paymentEntity.getPaymentDate());
		payment.setReferenceNumber(paymentEntity.getReferenceNumber());
		return payment;
	}else {
		System.out.println("There is no payment in the given id:" + id);
		return null;
	       }
	
	}

}
