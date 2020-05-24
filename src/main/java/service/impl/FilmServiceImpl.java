package service.impl;

import com.github.pagehelper.PageHelper;
import mapper.FilmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import po.Film;
import poi.WriteExcel;
import service.FilmService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=5)
@Service
public class FilmServiceImpl implements FilmService {
	@Autowired
	public FilmMapper filmMapper;

	public Film getFilmByid(short id) {
		Film a=filmMapper.getfilmbyid(id);
		return a;
	}

	public Film updatefilm(Film a) {
		filmMapper.updateFilmbyid(a);
		return a;
	}

	public List<Film> getpageFilms(int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);
		List<Film> l=filmMapper.getAllfilms();
		return l;
	}

	public int getfilmnum() {
		List<Film> l=filmMapper.getAllfilms();
		return l.size();
	}

	public Film addfilm(Film a) {
		filmMapper.insertFilm(a);
		return a;
	}
	public void delete(short id) {
		filmMapper.delete(id);
	}

	public InputStream getInputStream() throws Exception {
		String[] title=new String[]{"id","title","description","special_features","last_update"};
		List<Film> plist=filmMapper.getAllfilms();
		List<Object[]>  dataList =  new ArrayList<Object[]>();
		for(int i=0;i<plist.size();i++){
			Object[] obj=new Object[5];
			obj[0]=plist.get(i).getId();
			obj[1]=plist.get(i).getTitle();
			obj[2]=plist.get(i).getDescription();
			obj[3]=plist.get(i).getSpecial_features();
			obj[4]=plist.get(i).getLast_update();
			dataList.add(obj);
		}
		WriteExcel ex = new WriteExcel(title, dataList);
		InputStream in;
		in = ex.export();
		return in;
	}

	@Override
	public List<Film> selectFilmByName(Film a) {
		List<Film> list = filmMapper.selectFilmByName(a);
		return list;
	}

	@Override
	public List<Film> selectFilmByName(Film a, int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);
		List<Film> list = filmMapper.selectFilmByName(a);
		return list;
	}

}
