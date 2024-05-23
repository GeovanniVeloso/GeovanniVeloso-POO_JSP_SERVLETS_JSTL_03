package persistence;

import java.sql.SQLException;
import java.util.List;

public interface ICRUD <T> {
	
	public void registrar(T t)throws SQLException, ClassNotFoundException;

	public void atualizar(T t)throws SQLException, ClassNotFoundException;
	
	public void deletar(T t)throws SQLException, ClassNotFoundException;
	
	public T procurar(T t)throws SQLException, ClassNotFoundException;
	
	public List<T>lista()throws SQLException, ClassNotFoundException;
	
}
