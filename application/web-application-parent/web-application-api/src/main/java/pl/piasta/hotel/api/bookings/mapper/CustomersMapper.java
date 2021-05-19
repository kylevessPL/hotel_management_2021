package pl.piasta.hotel.api.bookings.mapper;

import org.mapstruct.Mapper;
import pl.piasta.hotel.api.bookings.Customer;
import pl.piasta.hotel.domainmodel.customers.CustomerDetails;

import java.util.List;

@Mapper
public interface CustomersMapper {

    List<CustomerDetails> mapToCustomerDetailsList(List<Customer> customers);
}
