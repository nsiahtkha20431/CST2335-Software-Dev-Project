package databank.model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.faces.view.ViewScoped;



public class DevicesPojo implements Serializable{
		private static final long serialVersionUID = 1L;

		
		private String deviceName;
		private int serialNumber;
		private String commType;
		private String ipAddress;
		private int serialPortNumber;
		
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		public int getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(int serialNumber) {
			this.serialNumber = serialNumber;
		}
		public String getCommType() {
			return commType;
		}
		public void setCommType(String commType) {
			this.commType = commType;
		}
		public String getIpAddress() {
			return ipAddress;
		}
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
		public int getSerialPortNumber() {
			return serialPortNumber;
		}
		public void setSerialPortNumber(int serialPortNumber) {
			this.serialPortNumber = serialPortNumber;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			// only include member variables that really contribute to an object's identity
			// i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
			// they shouldn't be part of the hashCode calculation
			return prime * result + Objects.hash(getSerialNumber());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}

			/* enhanced instanceof - yeah!
			 * As of JDK 14, no need for additional 'silly' cast:
			    if (animal instanceof Cat) {
			        Cat cat = (Cat)animal;
			        cat.meow();
	                // other class Cat operations ...
	            }
	         * Technically, 'otherPersonPojo' is a <i>pattern</i> that becomes an in-scope variable binding.
	         * Note: need to watch out just-in-case there is already a 'otherPersonPojo' variable in-scope!
			 */
			if (obj instanceof DevicesPojo otherDevicePojo) {
				// see comment (above) in hashCode(): compare using only member variables that are
				// truely part of an object's identity
				return Objects.equals(this.getSerialNumber(), otherDevicePojo.getSerialNumber());
			}
			return false;
		}

}
