package tn.ridha.Dao;

import java.util.HashMap;

public interface Dao<T> {
	public int insert(T t);
	public int update(T t);
	public T get(String column, String Key);
	public HashMap<Integer,T> getAll();
	public int delete(T t);
}
