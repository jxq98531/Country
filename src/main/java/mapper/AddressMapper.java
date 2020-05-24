package mapper;

import po.Address;

import java.util.List;

public interface AddressMapper {
	public List<Address> getAlladdresss();
	public void updateAddressbyid(Address a);
	public Address getaddressbyid(short id);
	public void insertAddress(Address a);
	public void delete(short id);
	public List<Address> selectAddressByName(Address a);

}