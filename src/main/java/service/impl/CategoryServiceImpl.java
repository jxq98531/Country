package service.impl;

import com.github.pagehelper.PageHelper;
import mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import po.Category;
import poi.WriteExcel;
import service.CategoryService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	public CategoryMapper categoryMapper;
	    
	public Category getCategoryByid(short id) {
		Category a=categoryMapper.getcategorybyid(id);
		return a;
	}

	public Category updatecategory(Category a) {
		categoryMapper.updateCategorybyid(a);
		return a;
	}

	public List<Category> getpageCategorys(int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);  
		List<Category> l=categoryMapper.getAllcategorys();
		return l;
	}

	public int getcategorynum() {
		List<Category> l=categoryMapper.getAllcategorys();
		return l.size();
	}

	public Category addcategory(Category a) {
		categoryMapper.insertCategory(a);
		return a;
	}

	public void delete(short id) {
		categoryMapper.delete(id);
	}

	public InputStream getInputStream() throws Exception {
		String[] title=new String[]{"id","name","last_update"};
		List<Category> plist=categoryMapper.getAllcategorys();
		List<Object[]>  dataList = new ArrayList<Object[]>();  
		for(int i=0;i<plist.size();i++){
			Object[] obj=new Object[3];
			obj[0]=plist.get(i).getId();
			obj[1]=plist.get(i).getName();
			obj[2]=plist.get(i).getLast_update();
			dataList.add(obj);
		}
		WriteExcel ex = new WriteExcel(title, dataList);  
		InputStream in;
		in = ex.export();
		return in;
	}

	@Override
	public List<Category> selectCategoryByName(Category a) {
		List<Category> list = categoryMapper.selectCategoryByName(a);
		return list;
	}

	@Override
	public List<Category> selectCategoryByName(Category a, int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);  
		List<Category> list = categoryMapper.selectCategoryByName(a);
		return list;
	}

}
