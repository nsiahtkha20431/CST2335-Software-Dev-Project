package databank.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.SessionMap;
import javax.inject.Inject;
import javax.inject.Named;

import databank.dao.DevicesDao;
import databank.model.DevicesPojo;

@Named
@SessionScoped
public class DevicesController implements Serializable {
		private static final long serialVersionUID = 1L;

		@Inject
		@SessionMap
		private Map< String, Object> sessionMap;

		@Inject
		private DevicesDao devicesDao;

		private List< DevicesPojo> devices;

		//necessary methods to make controller work

		public void loadDevices() {
			setDevices( devicesDao.readAllDevices());
		}

		public List< DevicesPojo> getDevices() {
			return devices;
		}

		public void setDevices( List< DevicesPojo> devices) {
			this.devices = devices;
		}

		public String navigateToAddForm() {
			//Pay attention to the name here, it will be used as the object name in add-person.xhtml
			//ex. <h:inputText value="#{newPerson.firstName}" id="firstName" />
			sessionMap.put( "newDevice", new DevicesPojo());
			return "add-device?faces-redirect=true";
		}

		public String submitDevice( DevicesPojo devices) {
			//TODO use DAO, also update the Person object with current date. you can use Instant::now
			devicesDao.createDevice(devices);
			return "list-devices.xhtml?faces-redirect=true";
		}

		public String navigateToUpdateForm( int serialNumber) {
			//TODO use session map to keep track of of the object being edited
			//use DAO to find the object
			DevicesPojo devices = devicesDao.readDeviceBySn(serialNumber);
			sessionMap.put("editDevice",devices);
			return "edit-device.xhtml?faces-redirect=true";
		}

		public String submitUpdatedDevice( DevicesPojo devices) {
			//TODO use DAO
			devicesDao.updateDevice(devices);
			return "list-devices.xhtml?faces-redirect=true";
		}

		public String deleteDevice( int serialNumber) {
			devicesDao.deleteDeviceBySn(serialNumber);
			return "list-devices.xhtml?faces-redirect=true";
		}


	}

