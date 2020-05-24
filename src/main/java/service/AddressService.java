package service;

import po.Address;

import java.io.InputStream;
import java.util.List;

public interface AddressService {
    List<Address> getpageAddresss(int pagenum, int pagesize);
    int getaddressnum();
    Address getAddressByid(short id);
    Address updateaddress(Address a);
    Address addaddress(Address a);
    void delete(short id);
    InputStream getInputStream() throws Exception;
    public List<Address> selectAddressByName(Address a);
    public List<Address> selectAddressByName(Address a, int pagenum, int pagesize);
}
