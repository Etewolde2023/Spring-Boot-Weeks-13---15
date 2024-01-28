package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service 
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	
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

}


