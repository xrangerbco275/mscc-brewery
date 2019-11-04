package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService
{
    @Override
    public CustomerDto getCustomerById(UUID id)
    {
        return CustomerDto.builder().id(UUID.randomUUID())
                .name("Joe Cruz")
                .build();
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto)
    {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto)
    {
        //todo impl
        log.debug("Updating....");
    }

    @Override
    public void deleteById(UUID customerId)
    {
        log.debug("Deleting.... ");
    }

}
