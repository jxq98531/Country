package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pagemodel.CategoryGrid;
import pagemodel.MSG;
import po.Category;
import service.CategoryService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Api(tags = "分类接口")
@Controller
public class CategoryController {
	private static Logger log = LogManager.getLogger(CategoryController.class.getName());
	
	@Autowired
	private CategoryService categoryservice;
	
	@ApiOperation("获取所有分类列表")
	@RequestMapping(value="/categorys",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public CategoryGrid listCategorys(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount,
			@RequestParam(required=false) String name){
		log.info("aaa");
		log.error("bbb");
		int total;
		List<Category> list;
		CategoryGrid grid=new CategoryGrid();
		if (name==null) {
			total=categoryservice.getcategorynum();
			list=categoryservice.getpageCategorys(current,rowCount);
			grid.setRows(list);
			grid.setTotal(total);
		} else {
			Category a = new Category();
			a.setName(name);

			total = categoryservice.selectCategoryByName(a).size();
			list = categoryservice.selectCategoryByName(a, current, rowCount);
			grid.setRows(list);
			grid.setTotal(total);
		}
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		return grid;
	}
	
	
	
	@ApiOperation("获取所有分类列表XML")
	@RequestMapping(value="/listCategorysXml",produces = {"application/xml;charset=UTF-8"},method = RequestMethod.GET)
	@ResponseBody
	public CategoryGrid listCategorysXml(@RequestParam("current") int current,@RequestParam("rowCount") int rowCount){
		int total=categoryservice.getcategorynum();
		List<Category> list=categoryservice.getpageCategorys(current,rowCount);
		CategoryGrid grid=new CategoryGrid();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(list);
		grid.setTotal(total);
		return grid;
	}
	
	
	@RequestMapping(value="/showcategory",method = RequestMethod.GET)
	public String showcategory(){
		return "/html/showcategory.html";
	}
	
	@ApiOperation("修改一个分类")
	@RequestMapping(value="/categorys/{id}",method = RequestMethod.PUT,consumes="application/json")
	@ResponseBody
	public Category updatecategory(@PathVariable("id") short id, @RequestBody Category a){
		Category category=categoryservice.updatecategory(a);
		return category;
	}
	
	@ApiOperation("获取一个分类")
	@RequestMapping(value="/categorys/{id}",method = RequestMethod.GET)
	@ResponseBody
	public MSG getcategorybyid(@PathVariable("id") short id){
		Category a=categoryservice.getCategoryByid(id);
		return new MSG("200",a);
	}
	
	@ApiOperation("添加一个分类")
	@RequestMapping(value="/categorys",method = RequestMethod.POST)
	@ResponseBody
	public Category add(@RequestBody Category a){
		Category category=categoryservice.addcategory(a);
		return category;
	}
	
	@ApiOperation("删除一个分类")
	@RequestMapping(value="/categorys/{id}",method = RequestMethod.DELETE)

	public @ResponseBody MSG delete(@PathVariable("id") short id){
		categoryservice.delete(id);
		return new MSG("200");
	}
	
	@ApiOperation("把分类导出为Excel")
	@RequestMapping(value="/exportcategory",method = RequestMethod.GET)
	public void export(HttpServletResponse response) throws Exception{
		InputStream is=categoryservice.getInputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("contentDisposition", "attachment;filename=AllUsers.xls");
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}
	
}
