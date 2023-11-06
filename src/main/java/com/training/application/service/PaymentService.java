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
		int amount = payment.getAmount();

		Card cd = payment.getCard();
		String cardNumber = cd.getCardNumber();
		String cvcNumber = cd.getCvcNumber();
		String expireDate = cd.getExpireDate();


		Date expiryDate = null;
		if ((cardNumber == null || cardNumber.trim().isEmpty()) &&
				(cvcNumber == null || cvcNumber.trim().isEmpty())  &&
				    (expireDate == null || expireDate.trim().isEmpty())) {
			System.out.println("Invalid input. Please check your card number,cvc number and expire date.");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			try {
				expiryDate = sdf.parse(expireDate);
			} catch (ParseException e) {
				System.out.println("Invalid date format. Please enter the date in MM/dd/yyyy format.");
			}
		}
		Date currentDate = new Date();
		boolean compareDate = currentDate.after(expiryDate);
		try {
			if ((cardNumber.matches("\\d{16}")) && (cvcNumber.matches("\\d{3}")) && amount > 0 && compareDate) {
				System.out.println("Valid card");
				PaymentEntity paymentEntity = new PaymentEntity();
				paymentEntity.setId(payment.getId());
				paymentEntity.setCardType(cd.getCardType());
				paymentEntity.setCardHolderName(cd.getCardHolderName());
				paymentEntity.setCardNumber(cd.getCardNumber());
				paymentEntity.setCvcNumber(cd.getCvcNumber());
				paymentEntity.setExpireDate(cd.getExpireDate());
				paymentEntity.setAmount(payment.getAmount());
				paymentEntity.setCreatedBy(payment.getName());
				paymentEntity.setUpdatedBy(payment.getName());
				paymentEntity.setReferenceNumber(payment.getReferenceNumber());
				paymentEntity.setPaymentDate(payment.getPaymentDate());
				paymentEntity.setPaymentStatus(payment.getPaymentStatus());
				paymentRepository.save(paymentEntity);
				System.out.println("Payment is done");
				return paymentEntity;
			} else {
				System.out.println("Invalid input,Please give valid input");
				throw new InputMismatchException();
			}
		} catch (Exception e) {
			System.out.println("The given data is not valid");
		}
		return null;
	}

	public Payment getPayment(int id) {

		Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findById(id);
		try {
			if (optionalPaymentEntity.isPresent()) {
				PaymentEntity paymentEntity = optionalPaymentEntity.get();
				System.out.println(paymentEntity);

				Payment payment = new Payment();
				payment.setId(paymentEntity.getId());
				payment.setAmount(paymentEntity.getAmount());
				payment.setPaymentDate(paymentEntity.getPaymentDate());
				payment.setReferenceNumber(paymentEntity.getReferenceNumber());
				return payment;
			} else {
				System.out.println("There is no payment in the given id:" + id);
				return null;
			}
		} catch (Exception e) {
			System.out.println("Please give the valid id");
		}
		return null;
	}

	public void updatePayment(Payment payment) {

		Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findById(payment.getId());
		try {
			if (optionalPaymentEntity.isPresent()) {
				PaymentEntity paymentEntity = optionalPaymentEntity.get();
				int amount = paymentEntity.getAmount();
				if (amount >= payment.getAmount()) {

					Card cd = payment.getCard();
					paymentEntity.setId(payment.getId());
					paymentEntity.setCardType(cd.getCardType());
					paymentEntity.setCardHolderName(cd.getCardHolderName());
					paymentEntity.setCardNumber(cd.getCardNumber());
					paymentEntity.setCvcNumber(cd.getCvcNumber());
					paymentEntity.setExpireDate(cd.getExpireDate());
					paymentEntity.setAmount(payment.getAmount());
					paymentEntity.setCreatedBy(payment.getName());
					paymentEntity.setUpdatedBy(payment.getName());
					paymentEntity.setReferenceNumber(payment.getReferenceNumber());
					paymentEntity.setPaymentDate(payment.getPaymentDate());
					paymentEntity.setPaymentStatus(payment.getPaymentStatus());
					paymentRepository.save(paymentEntity);
					System.out.println("Payment is updated");

				} else {
					System.out.println("The given amount is higher");
				}
			}
		} catch (Exception e) {
			System.out.println("The given is not found");
		}
	}

	public void deletePayment(int id) {
		Optional<PaymentEntity> optionalPaymentEntity = paymentRepository.findById(id);
		try {
			if (optionalPaymentEntity.isPresent()) {
				PaymentEntity paymentEntity = optionalPaymentEntity.get();
				paymentRepository.delete(paymentEntity);
			} else {
				System.out.println("The given id is not found");
			}
		} catch (Exception e) {
			System.out.println("Please give the valid id");
		}
	}
}