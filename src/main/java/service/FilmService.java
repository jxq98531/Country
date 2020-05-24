package service;

import po.Film;

import java.io.InputStream;
import java.util.List;

public interface FilmService {
    List<Film> getpageFilms(int pagenum, int pagesize);
    int getfilmnum();
    Film getFilmByid(short id);
    Film updatefilm(Film a);
    Film addfilm(Film a);
    void delete(short id);
    InputStream getInputStream() throws Exception;
    public List<Film> selectFilmByName(Film a);
    public List<Film> selectFilmByName(Film a, int pagenum, int pagesize);
}
