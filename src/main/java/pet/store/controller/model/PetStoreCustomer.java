package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor

public class PetStoreCustomer {
	
    private Long customerId;	
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private Set<PetStoreData> petstores = new HashSet<>();
	
	public PetStoreCustomer(Customer customer) {
        customerId = customer.getCustomerId();
        customerFirstName = customer.getCustomerFirstName();
        customerLastName = customer.getCustomerLastName();
        customerEmail = customer.getCustomerEmail();
        for (PetStore petStore : customer.getPetstores()) {
        	petstores.add(new PetStoreData(petStore));
        }
    };
    
    

}

