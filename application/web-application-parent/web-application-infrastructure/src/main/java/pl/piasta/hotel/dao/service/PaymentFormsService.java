package pl.piasta.hotel.dao.service;

import pl.piasta.hotel.dao.model.PaymentFormsEntity;

import java.util.List;

public interface PaymentFormsService {

	List<PaymentFormsEntity> findAll();
	PaymentFormsEntity findById(Integer id);
	List<PaymentFormsEntity> findByName(String name);
	long count();
	void delete(PaymentFormsEntity paymentFormsEntity);
	void save(PaymentFormsEntity paymentFormsEntity);

}
