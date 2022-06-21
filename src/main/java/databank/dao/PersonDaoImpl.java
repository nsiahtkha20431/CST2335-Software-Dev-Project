package databank.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import databank.dao.PersonDao;
import databank.model.PersonPojo;

@SuppressWarnings("unused")
@ApplicationScoped
public class PersonDaoImpl implements PersonDao, Serializable {
	/** explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final String ARMADA_DS_JNDI = "java:app/jdbc/armada";
	private static final String READ_ALL = "select * from personnel";
	private static final String READ_PERSON_BY_ID = "select * from personnel where personnelID = ?";
	private static final String INSERT_PERSON = "insert into personnel (last_name,first_name,card_number,department_number,department_name,gender) values(?,?,?,?,?,?)";
	private static final String UPDATE_PERSON_ALL_FIELDS = "update personnel set last_name = ?, first_name = ?, card_number = ?, department_number = ?, department_name = ?, gender = ? where personnelID = ?";
	private static final String DELETE_PERSON_BY_ID = "delete from personnel where personnelID = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = ARMADA_DS_JNDI)
	protected DataSource armadaDS;

	protected Connection conn;
	protected PreparedStatement readAllPstmt;
	protected PreparedStatement readByIdPstmt;
	protected PreparedStatement createPstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement deleteByIdPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = armadaDS.getConnection();
			readAllPstmt = conn.prepareStatement(READ_ALL);
			createPstmt = conn.prepareStatement(INSERT_PERSON, RETURN_GENERATED_KEYS);
			readByIdPstmt = conn.prepareStatement(READ_PERSON_BY_ID); 
			updatePstmt = conn.prepareStatement(UPDATE_PERSON_ALL_FIELDS);
			deleteByIdPstmt = conn.prepareStatement(DELETE_PERSON_BY_ID);
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database: " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			readAllPstmt.close();
			createPstmt.close();
			readByIdPstmt.close();
			updatePstmt.close();
			deleteByIdPstmt.close();
			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection: " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<PersonPojo> readAllPeople() {
		logMsg("reading all People");
		List<PersonPojo> people = new ArrayList<>();
		try {
			ResultSet rs = readAllPstmt.executeQuery();
			logMsg("finished executing query");
			
			while (rs.next()) {
				PersonPojo newPerson = new PersonPojo();
				newPerson.setId(rs.getInt("personnelID"));
				newPerson.setFirstName(rs.getString("first_name"));
				newPerson.setLastName(rs.getString("last_name"));
				newPerson.setCardNumber(rs.getInt("card_number"));
				newPerson.setDeptNumber(rs.getInt("department_number"));
				newPerson.setDeptName(rs.getString("department_name"));
				newPerson.setGender(rs.getString("gender"));
				people.add(newPerson);
			}
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		logMsg("number of people retrieved = " + people.size());

		return people;
	}

	@Override
	public PersonPojo createPerson(PersonPojo person) {  
		logMsg("creating an person");
		try {
			createPstmt.setString(1, person.getLastName());
			createPstmt.setString(2, person.getFirstName());
			createPstmt.setInt(3, person.getCardNumber());
			createPstmt.setInt(4, person.getDeptNumber());
			createPstmt.setString(5, person.getDeptName());
			createPstmt.setString(6, person.getGender());
			
			if (person.getCardNumber() != 0 && person.getDeptNumber() != 0) {
				createPstmt.execute();
			}
			
		} catch (SQLException e) {
			logMsg("something went wrong creating a new person: " + e.getLocalizedMessage());
		}
		logMsg("added person");
		return person; 
	}

	@Override
	public PersonPojo readPersonById(int personId) {
		logMsg("read a specific person");
		PersonPojo personReadByID = new PersonPojo();
		
		try {
			
			readByIdPstmt.setInt(1, personId); 
			ResultSet rs = readByIdPstmt.executeQuery();
			
			if (rs.next()) {
				personReadByID.setId(rs.getInt("personnelID"));
				personReadByID.setFirstName(rs.getString("first_name"));
				personReadByID.setLastName(rs.getString("last_name"));
				personReadByID.setCardNumber(rs.getInt("card_number"));
				personReadByID.setDeptNumber(rs.getInt("department_number"));
				personReadByID.setDeptName(rs.getString("department_name"));
				personReadByID.setGender(rs.getString("gender"));
			}
			
		} catch (SQLException e) {
			logMsg("something went wrong reading person: " + e.getLocalizedMessage());
		}
		return personReadByID;
	}

	@Override
	public void updatePerson(PersonPojo person) {
		logMsg("updating a specific person");
		
		try {
			updatePstmt.setString(1, person.getLastName());
			updatePstmt.setString(2, person.getFirstName());
			updatePstmt.setInt(3, person.getCardNumber());
			updatePstmt.setInt(4, person.getDeptNumber());
			updatePstmt.setString(5, person.getDeptName());
			updatePstmt.setString(6, person.getGender());
			updatePstmt.setInt(7, person.getId());
			updatePstmt.executeUpdate();
			
		} catch (SQLException e) {
			logMsg("something went wrong updating person: " + e.getLocalizedMessage());
		}
	}

	@Override
	public void deletePersonById(int personId) {
		logMsg("deleting a specific person");
		try {
			
			deleteByIdPstmt.setInt(1, personId);
			deleteByIdPstmt.execute();
			
		} catch (SQLException e) {
			logMsg("something went wrong deleting person: " + e.getLocalizedMessage());
		}
	}

}