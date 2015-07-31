package runkoserver.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runkoserver.domain.Person;
import runkoserver.repository.PersonRepository;

/**
 * Service for all person-repository's handling requests.
 */
@Service
public class PersonService implements RepoService{
    
    @Autowired
    private PersonRepository repository;
    
    public List<Person> findAll() {
        return repository.findAll();
    }
    
    public void save(Person person) {
        if (person.getName() != null && !person.getName().trim().isEmpty()) {
            repository.save(person);
        }
    }
    
    public Person findById(Long Id) {
        return repository.findById(Id);        
    }
    
    public List<Person> findByName(String name) {
        return repository.findByName(name);
    }
    
<<<<<<< HEAD
    public Person findByUsername(String username){
=======
    public Person findByUsername(String username) {
>>>>>>> 473d1851677df37d5130ca62be133d2d26730518
        return repository.findByUsername(username);
    }
    
    @Override
    public void delete(Long Id) {
        if (repository.exists(Id)) {
            repository.delete(Id);
        }
    }

    //For tests, unused anywhere else
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
    
}
