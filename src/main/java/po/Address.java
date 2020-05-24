package po;



public class Address {
	private short id;
	private String address;
	private String district;
	private String last_update;

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}

	@Override
	public String toString() {
		return "Address{" +
				"id=" + id +
				", address='" + address + '\'' +
				", district='" + district + '\'' +
				", last_update='" + last_update + '\'' +
				'}';
	}
}
