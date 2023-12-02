package pet.store.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Customer {
// identifying the primary key for the customer table
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
// Many to many relationship with petStore table
	@EqualsAndHashCode.Exclude 
	@ToString.Exclude
	@ManyToMany(mappedBy =  "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petstores = new HashSet<>();
	
	
}


	
