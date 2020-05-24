package mapper;

import po.Film;

import java.util.List;

public interface FilmMapper {
    public List<Film> getAllfilms();
    public void updateFilmbyid(Film a);
    public Film getfilmbyid(short id);
    public void insertFilm(Film a);
    public void delete(short id);
    public List<Film> selectFilmByName(Film a);

}