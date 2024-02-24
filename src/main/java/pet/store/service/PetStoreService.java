package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service 
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;
	
	
@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {	
	
	Long petStoreId = petStoreData.getPetStoreId();
	PetStore petStore = findOrCreatePetStore(petStoreId);
	
    copyPetStoreFields(petStore, petStoreData);
    
	return new PetStoreData(petStoreDao.save(petStore));
}
    
private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
	petStore.setPetStoreId(petStoreData.getPetStoreId());
	petStore.setPetStoreName(petStoreData.getPetStoreName());
	petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
	petStore.setPetStoreCity(petStoreData.getPetStoreCity());
	petStore.setPetStoreState(petStoreData.getPetStoreState());
	petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	petStore.setPetStorePhone(petStoreData.getPetStorePhone());	
}	

    private PetStore findOrCreatePetStore(Long petStoreId) {
	
	PetStore petStore;  
	
	
	if(Objects.isNull(petStoreId)) {
		petStore = new PetStore();
		} 
	     else {
			petStore = findPetStoreById(petStoreId);
	     }
	return petStore;
			
}

 private PetStore findPetStoreById(Long petStoreId) {
	
	return petStoreDao.findById(petStoreId)
			.orElseThrow(() -> new NoSuchElementException(
					"PetStore with Id " + petStoreId + " was not found"));
					
}


@Transactional(readOnly = false)
public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
	PetStore petStore = findPetStoreById(petStoreId);
	Long employeeId = petStoreEmployee.getEmployeeId();
	Employee employee = findOrCreateEmployee(employeeId, petStoreId);
	copyEmployeeFields(employee, petStoreEmployee);
	employee.setPetStore(petStore);
	petStore.getEmployees().add(employee);
	return new PetStoreEmployee(employeeDao.save(employee));
}

private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
	employee.setEmployeeId(petStoreEmployee.getEmployeeId());
	employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
	employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
	employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
	employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
}

private Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
	Employee employee;
	
	
	if(Objects.isNull(employeeId)) {
		employee = new Employee();
		} 
	     else {	 
	     
			employee = findEmployeeById(employeeId, petStoreId);
	     }
	return employee;
	}
	

private Employee findEmployeeById(Long employeeId, Long petStoreId) {
	Employee employee = employeeDao.findById(employeeId)
			.orElseThrow(() -> new NoSuchElementException(
					"Employee with Id " + employeeId + " was not found"));
if(employee.getPetStore().getPetStoreId() != petStoreId) {
	throw new IllegalArgumentException("This Employee doesn't belong to this petstore");
}
return employee;
}

@Transactional(readOnly = false)
public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {	
	PetStore petStore = findPetStoreById(petStoreId);
	Customer customer= findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId());
	copyCustomerFields(customer, petStoreCustomer);
	// Add petStore to Set in Customer
	customer.getPetstores().add(petStore);
	
	// Add customer to Set in PetStore. 
	petStore.getCustomers().add(customer);
	
	return new PetStoreCustomer(customerDao.save(customer));
}

private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
	
	customer.setCustomerId(petStoreCustomer.getCustomerId());
	customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
	customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
	
}


private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
	
	Customer customer;
	

	if(Objects.isNull(customerId)) {
		customer = new Customer();
		} 
	     else {	 
	     
			customer = findCustomerById(petStoreId, customerId);
	     }
	return customer;	
	
}



private Customer findCustomerById(Long petStoreId, Long customerId) {
	Customer customer = customerDao.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException("Customer with ID= " + customerId
					+ " was not found."));
			boolean found = false;
			
			for(PetStore petStore : customer.getPetstores()) {
				if(petStore.getPetStoreId() == petStoreId) {
					found = true;
					break;
					
				}
			}
			if(!found) {
				throw new IllegalArgumentException("Customer with ID= " + customerId +
						"does not shop at Petstore with ID= " + petStoreId + ".");
	
	}
			return customer;
		
}	
	


@Transactional(readOnly = true)
public List<PetStoreData> retrieveAllPetStores() {
	List<PetStore> petStores = petStoreDao.findAll();
	List<PetStoreData> result = new LinkedList<>();
	for (PetStore petStore : petStores) {
		PetStoreData psd = new PetStoreData(petStore);
		psd.getCustomers().clear();
		psd.getEmployees().clear();
		result.add(psd);
		
	}
	return result;
	
}

public PetStoreData retrievePetStoreById(Long petStoreId) {
	
	PetStore petStore = findPetStoreById(petStoreId);	 	

	return new PetStoreData(petStore);

}

@Transactional(readOnly = false)
public void deletePetStoreById(Long petStoreId) {
	PetStore petStore = findPetStoreById(petStoreId);
	
	petStoreDao.delete(petStore);
}

}





		


			
	

	


	



	
	



	








