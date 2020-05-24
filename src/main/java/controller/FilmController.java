package controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pagemodel.FilmGrid;
import pagemodel.MSG;
import po.Film;
import service.FilmService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Api(tags = "电影接口")
@Controller
public class FilmController {
	private static Logger log = LogManager.getLogger(FilmController.class.getName());

	@Autowired
	private FilmService filmservice;

	@ApiOperation("获取所有电影列表")
	@RequestMapping(value="/films",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public FilmGrid listFilms(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount,
                                      @RequestParam(required=false) String title){
		log.info("aaa");
		log.error("bbb");
		int total;
		List<Film> list;
		FilmGrid grid=new FilmGrid();
		if (title==null) {
			total=filmservice.getfilmnum();
			list=filmservice.getpageFilms(current,rowCount);
			grid.setRows(list);
			grid.setTotal(total);
		} else {
			Film a = new Film();
			a.setTitle(title);
			total = filmservice.selectFilmByName(a).size();
			list = filmservice.selectFilmByName(a, current, rowCount);
			grid.setRows(list);
			grid.setTotal(total);
		}
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		return grid;
	}



	@ApiOperation("获取所有电影列表XML")
	@RequestMapping(value="/listFilmsXml",produces = {"application/xml;charset=UTF-8"},method = RequestMethod.GET)
	@ResponseBody
	public FilmGrid listFilmsXml(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount){
		int total=filmservice.getfilmnum();
		List<Film> list=filmservice.getpageFilms(current,rowCount);
		FilmGrid grid=new FilmGrid();
		grid.setCurrent(current);
		grid.setRowCount(rowCount);
		grid.setRows(list);
		grid.setTotal(total);
		return grid;
	}


	@RequestMapping(value="/showfilm",method = RequestMethod.GET)
	public String showfilm(){
		return "/html/showfilm.html";
	}

	@ApiOperation("修改一个电影")
	@RequestMapping(value="/films/{id}",method = RequestMethod.PUT,consumes="application/json")
	@ResponseBody
	public Film updatefilm(@PathVariable("id") short id, @RequestBody Film a){
		Film film=filmservice.updatefilm(a);
		return film;
	}

	@ApiOperation("获取一个电影")
	@RequestMapping(value="/films/{id}",method = RequestMethod.GET)
	@ResponseBody
	public MSG getfilmbyid(@PathVariable("id") short id){
		Film a=filmservice.getFilmByid(id);
		return new MSG("200",a);
	}
	@ApiOperation("添加一个电影")
	@RequestMapping(value="/films",method = RequestMethod.POST)
	@ResponseBody
	public Film add(@RequestBody Film a){
		Film film=filmservice.addfilm(a);
		return film;
	}

	@ApiOperation("删除一个电影")
	@RequestMapping(value="/films/{id}",method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") short id){
		filmservice.delete(id);
		return "/html/showfilm.html";
	}

	@ApiOperation("把电影导出为Excel")
	@RequestMapping(value="/exportfilm",method = RequestMethod.GET)
	public void export(HttpServletResponse response) throws Exception{
		InputStream is=filmservice.getInputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("contentDisposition", "attachment;filename=AllUsers.xls");
		ServletOutputStream output = response.getOutputStream();
		IOUtils.copy(is, output);
	}

}
