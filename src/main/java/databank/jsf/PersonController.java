package databank.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.SessionMap;
import javax.inject.Inject;
import javax.inject.Named;

import databank.dao.PersonDao;
import databank.model.PersonPojo;

/**
 * Description: Responsible for collection of Person Pojo's in XHTML (list)
 * <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO (data access object)
 */

//TODO don't forget this object is a managed bean with a session scope
@Named 
@SessionScoped
public class PersonController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@SessionMap
	private Map<String, Object> sessionMap;

	@Inject
	private PersonDao personDao;

	private List<PersonPojo> people;

	// necessary methods to make controller work

	public void loadPeople() {
		setPeople(personDao.readAllPeople());
	}

	public List<PersonPojo> getPeople() {
		return people;
	}

	public void setPeople(List<PersonPojo> people) {
		this.people = people;
	}

	public String navigateToAddForm() {
		sessionMap.put("newPerson", new PersonPojo());
		return "add-person.xhtml?faces-redirect=true";
	}

	public String submitPerson(PersonPojo person) {
		personDao.createPerson(person);
		return "list-people?faces-redirect=true";
	}

	public String navigateToUpdateForm(int personId) { 
		PersonPojo p1 = personDao.readPersonById(personId);
		sessionMap.put("existingPerson", p1);
		return "edit-person?faces-redirect=true";
	}

	public String submitUpdatedPerson(PersonPojo person) { 
		personDao.updatePerson(person);
		return "list-people?faces-redirect=true";
	}

	public String deletePerson(int personId) {
		personDao.deletePersonById(personId);
		return "list-people?faces-redirect=true";
	}

}