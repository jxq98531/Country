package service.impl;

import com.github.pagehelper.PageHelper;
import mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import po.Address;
import poi.WriteExcel;
import service.AddressService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service
public class AddressServiceImpl implements AddressService{
	@Autowired
	public AddressMapper addressMapper;
	    
	public Address getAddressByid(short id) {
		Address a=addressMapper.getaddressbyid(id);
		return a;
	}

	public Address updateaddress(Address a) {
		addressMapper.updateAddressbyid(a);
		return a;
	}

	public List<Address> getpageAddresss(int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);  
		List<Address> l=addressMapper.getAlladdresss();
		return l;
	}

	public int getaddressnum() {
		List<Address> l=addressMapper.getAlladdresss();
		return l.size();
	}

	public Address addaddress(Address a) {
		addressMapper.insertAddress(a);
		return a;
	}

	public void delete(short id) {
		addressMapper.delete(id);
	}

	public InputStream getInputStream() throws Exception {
		String[] title=new String[]{"id","address","district","last_update"};
		List<Address> plist=addressMapper.getAlladdresss();
		List<Object[]>  dataList = new ArrayList<Object[]>();  
		for(int i=0;i<plist.size();i++){
			Object[] obj=new Object[4];
			obj[0]=plist.get(i).getId();
			obj[1]=plist.get(i).getAddress();
			obj[2]=plist.get(i).getDistrict();
			obj[3]=plist.get(i).getLast_update();
			dataList.add(obj);
		}
		WriteExcel ex = new WriteExcel(title, dataList);  
		InputStream in;
		in = ex.export();
		return in;
	}

	@Override
	public List<Address> selectAddressByName(Address a) {
		List<Address> list = addressMapper.selectAddressByName(a);
		return list;
	}

	@Override
	public List<Address> selectAddressByName(Address a, int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);  
		List<Address> list = addressMapper.selectAddressByName(a);
		return list;
	}

}
