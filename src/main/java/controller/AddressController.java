package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pagemodel.AddressGrid;
import pagemodel.MSG;
import po.Address;
import service.AddressService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Api(tags = "地址接口")
@Controller
public class AddressController {
	private static Logger log = LogManager.getLogger(AddressController.class.getName());
	
	@Autowired
	private AddressService addressservice;
	
	@ApiOperation("获取所有地址列表")
	@RequestMapping(value="/addresss",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public AddressGrid listAddresss(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount,
			@RequestParam(required=false) String address,@RequestParam(required=false) String district){
		log.info("aaa");
		log.error("bbb");
		int total;
		List<Address> list;
		AddressGrid grid=new AddressGrid();
		if (address==null) {
			total=addressservice.getaddressnum();
			list=addressservice.getpageAddresss(current,rowCount);
			grid.setRows(list);
			grid.setTotal(total);
		} else {
			Address a = new Address();
			a.setAddress(address);
			a.setDistrict(district);
			total = addressservice.selectAddressByName(a).size();
			list = addressservice.selectAddressByName(a, current, rowCount);
			grid.setRows(list);
			grid.setTotal(total);
		}
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		return grid;
	}
	
	
	
	@ApiOperation("获取所有地址列表XML")
	@RequestMapping(value="/listAddresssXml",produces = {"application/xml;charset=UTF-8"},method = RequestMethod.GET)
	@ResponseBody
	public AddressGrid listAddresssXml(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount){
		int total=addressservice.getaddressnum();
		List<Address> list=addressservice.getpageAddresss(current,rowCount);
		AddressGrid grid=new AddressGrid();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(list);
		grid.setTotal(total);
		return grid;
	}
	
	
	@RequestMapping(value="/showaddress",method = RequestMethod.GET)
	public String showaddress(){
		return "/html/showaddress.html";
	}
	
	@ApiOperation("修改一个地址")
	@RequestMapping(value="/addresss/{id}",method = RequestMethod.PUT,consumes="application/json")
	@ResponseBody
	public Address updateaddress(@PathVariable("id") short id, @RequestBody Address a){
		Address address=addressservice.updateaddress(a);
		return address;
	}
	
	@ApiOperation("获取一个地址")
	@RequestMapping(value="/addresss/{id}",method = RequestMethod.GET)
	@ResponseBody
	public MSG getaddressbyid(@PathVariable("id") short id){
		Address a=addressservice.getAddressByid(id);
		return new MSG("200",a);
	}
	
	@ApiOperation("添加一个地址")
	@RequestMapping(value="/addresss",method = RequestMethod.POST)
	@ResponseBody
	public Address add(@RequestBody Address a){
		Address address=addressservice.addaddress(a);
		return address;
	}
	
	@ApiOperation("删除一个地址")
	@RequestMapping(value="/addresss/{id}",method = RequestMethod.DELETE)

	public @ResponseBody MSG delete(@PathVariable("id") short id){
		addressservice.delete(id);
		return new MSG("200");
	}
	
	@ApiOperation("把地址导出为Excel")
	@RequestMapping(value="/exportaddress",method = RequestMethod.GET)
	public void export(HttpServletResponse response) throws Exception{
		InputStream is=addressservice.getInputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("contentDisposition", "attachment;filename=AllUsers.xls");
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}
	
}
