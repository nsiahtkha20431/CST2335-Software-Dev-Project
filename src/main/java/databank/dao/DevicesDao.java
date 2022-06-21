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


import databank.model.DevicesPojo;


@Named
@ApplicationScoped
public class DevicesDao implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String DATABANK_DS_JNDI = "java:app/jdbc/armada";
	private static final String READ_ALL = "SELECT device_name,serial_number,comm_type,ip_address,serial_port_number FROM devices";
	private static final String READ_DEVICE_BY_SN = "SELECT device_name,serial_number,comm_type,ip_address,serial_port_number FROM devices WHERE serial_number = ?";
	private static final String INSERT_DEVICE = "insert into devices (device_name,serial_number,comm_type,ip_address,serial_port_number) values(?,?,?,?,?)";
	private static final String UPDATE_DEVICE_ALL_FIELDS = "UPDATE armada.devices set device_name = ?, serial_number = ?, comm_type = ?, ip_address = ?, serial_port_number = ? where serial_number=?";
	private static final String DELETE_DEVICE_BY_SN = "delete from devices where serial_number = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg( String msg) {
		( (ServletContext) externalContext.getContext()).log( msg);
	}

	@Resource( lookup = DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllPstmt;
	protected PreparedStatement readBySnPstmt;
	protected PreparedStatement createPstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement deleteBySnPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg( "building connection and stmts");
			conn = databankDS.getConnection();
			readAllPstmt = conn.prepareStatement( READ_ALL);
			createPstmt = conn.prepareStatement( INSERT_DEVICE);
			//TODO initialize other PreparedStatements
			readBySnPstmt=conn.prepareStatement(READ_DEVICE_BY_SN);
			updatePstmt=conn.prepareStatement(UPDATE_DEVICE_ALL_FIELDS);
			deleteBySnPstmt=conn.prepareStatement(DELETE_DEVICE_BY_SN);
		} catch ( Exception e) {
			logMsg( "something went wrong getting connection from database: " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg( "closing stmts and connection");
			readAllPstmt.close();
			createPstmt.close();
			//TODO close other PreparedStatements
			readBySnPstmt.close();
			deleteBySnPstmt.close();
			updatePstmt.close();
			conn.close();
		} catch ( Exception e) {
			logMsg( "something went wrong closing stmts or connection: " + e.getLocalizedMessage());
		}
	}
	
	public List< DevicesPojo> readAllDevices() {
		logMsg( "reading all devices");
		List< DevicesPojo> devices = new ArrayList<>();
		try ( ResultSet rs = readAllPstmt.executeQuery();) {

			while ( rs.next()) {
				DevicesPojo newDevice = new DevicesPojo();
				newDevice.setDeviceName( rs.getString( "device_name"));
				newDevice.setSerialNumber( rs.getInt( "serial_number"));
				newDevice.setCommType(rs.getString("comm_type"));
				newDevice.setIpAddress(rs.getString("ip_address"));
				newDevice.setSerialPortNumber(rs.getInt("serial_port_number"));
				devices.add( newDevice);
			}
		} catch ( SQLException e) {
			logMsg( "something went wrong reading devices: " + e.getLocalizedMessage());
		}
		return devices;
	}

	public DevicesPojo createDevice( DevicesPojo device) {
		logMsg( "creating an device");
		//TODO complete the insertion of a new person
		//TODO use try-and-catch statement
		try {
			createPstmt.setString(1, device.getDeviceName());
			createPstmt.setInt(2, device.getSerialNumber());
			createPstmt.setString(3, device.getCommType());
			createPstmt.setString(4, device.getIpAddress());
			createPstmt.setInt(5, device.getSerialPortNumber());
					
				
    		createPstmt.execute();
    	}
    	catch (SQLException e) {
            logMsg("something went wrong creating device: " + e.getLocalizedMessage());
    	}
    
		return null;
}


	public DevicesPojo readDeviceBySn( int serialNumber) {
		logMsg( "read a specific device");
		
		//TODO complete the retrieval of a specific person by its id
		//TODO use try-and-catch statement
		DevicesPojo device = new DevicesPojo();
		try {
			readBySnPstmt.setInt(1, serialNumber);
    		ResultSet rs = readBySnPstmt.executeQuery();
    		
    		if (rs.next()) {
    			device.setDeviceName( rs.getString( "device_name"));
				device.setSerialNumber( rs.getInt( "serial_number"));
				device.setCommType(rs.getString("comm_type"));
				device.setIpAddress(rs.getString("ip_address"));
				device.setSerialPortNumber(rs.getInt("serial_port_number"));
				
    		}
		
		}
    	catch (SQLException e) {
            logMsg("something went wrong reading device by sn: " + e.getLocalizedMessage());
    	}
    	
    	return device;
	}

	public void updateDevice( DevicesPojo device) {
		logMsg( "updating a specific device");
		//TODO complete the update of a specific person
		//TODO use try-and-catch statement
		try {
			updatePstmt.setString(1, device.getDeviceName());
			updatePstmt.setInt(2, device.getSerialNumber());
			updatePstmt.setString(3, device.getCommType());
			updatePstmt.setString(4, device.getIpAddress());
			updatePstmt.setInt(5, device.getSerialPortNumber());
			updatePstmt.setInt(6, device.getSerialNumber());   		
			updatePstmt.execute();
    	}
    	catch (SQLException e) {
            logMsg("something went wrong when updating: " + e.getLocalizedMessage());
    	}
    	
	}

	public void deleteDeviceBySn( int serialNumber) {
		logMsg( "deleting a specific device");
		//TODO complete the deletion of a specific person
		//TODO use try-and-catch statement
		try {
			deleteBySnPstmt.setInt(1, serialNumber);
			deleteBySnPstmt.execute();
    	}
    	catch (SQLException e) {
            logMsg("something went wrong when deleting device: " + e.getLocalizedMessage());
    	}
	}

}
