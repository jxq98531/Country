package mapper;

import po.Category;

import java.util.List;

//mybatis的实体类和hibernate实体类的不同是mybatis的实体类不需要加载到spring的beanFcategoryy中，
//而是通过操作数据库的mapper来持久化数据,相当于DAO层。
public interface CategoryMapper {
	public List<Category> getAllcategorys();
	public void updateCategorybyid(Category a);
	public Category getcategorybyid(short id);
	public void insertCategory(Category a);
	public void delete(short id);
	public List<Category> selectCategoryByName(Category a);
	
}
