package pet.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j

public class PetStoreController {	
@Autowired
private PetStoreService petStoreService;

@PostMapping("/pet_store")
@ResponseStatus(code = HttpStatus.CREATED)
public PetStoreData savePetStore(@RequestBody PetStoreData petStoreData) {
	log.info("creating petStore {}", petStoreData);
	return petStoreService.savePetStore(petStoreData);
}

@PutMapping("/petStore/{petStoreId}")
public PetStoreData updatePetStore(@PathVariable Long petStoreId, 
		@RequestBody PetStoreData petStoreData) {
	petStoreData.setPetStoreId(petStoreId);
	log.info("Updating petStore {}", petStoreData);
	return petStoreService.savePetStore(petStoreData);
}

@PostMapping("/{petStoreId}/employee")
@ResponseStatus(code = HttpStatus.CREATED)
public PetStoreEmployee insertEmployee(@PathVariable Long petStoreId, 
		@RequestBody PetStoreEmployee petStoreEmployee) {
	log.info("Creating Petstore Employees {}", petStoreEmployee);
	return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
}

@PostMapping("/{petStoreId}/customer")
@ResponseStatus(code = HttpStatus.CREATED)  
public PetStoreCustomer insertCustomer(@PathVariable Long petStoreId, 
		@RequestBody PetStoreCustomer petStoreCustomer) {
	log.info("Creating Petstore Customers {}", petStoreCustomer);
	return petStoreService.saveCustomer(petStoreId, petStoreCustomer);

}

@GetMapping("/petStore")
public List<PetStoreData> retrieveAllPetStores() {
	log.info("Retrieve all petstores that are called");
	return petStoreService.retrieveAllPetStores();
	
}


@GetMapping("/petStore/{petStoreId}")
public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
	log.info("Retrieving petstore with ID={}", petStoreId);
	return petStoreService.retrievePetStoreById(petStoreId);


}

@DeleteMapping("/petStore/{petStoreId}")
public void deletePetStoreById(@PathVariable Long petStoreId) {
	log.info("Deleting petstore with ID={}", petStoreId);
	 petStoreService.deletePetStoreById(petStoreId);
}
}
