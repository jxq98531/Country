package service;

import po.Category;

import java.io.InputStream;
import java.util.List;

public interface CategoryService {
    List<Category> getpageCategorys(int pagenum, int pagesize);
    int getcategorynum();
    Category getCategoryByid(short id);
    Category updatecategory(Category a);
    Category addcategory(Category a);
    void delete(short id);
    InputStream getInputStream() throws Exception;
    public List<Category> selectCategoryByName(Category a);
    public List<Category> selectCategoryByName(Category a, int pagenum, int pagesize);
}
