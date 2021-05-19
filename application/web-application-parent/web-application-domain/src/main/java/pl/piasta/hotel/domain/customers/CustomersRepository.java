package pl.piasta.hotel.domain.customers;

import pl.piasta.hotel.domainmodel.customers.CustomerDetails;

public interface CustomersRepository {

    Integer saveCustomer(CustomerDetails customerDetails);
}
