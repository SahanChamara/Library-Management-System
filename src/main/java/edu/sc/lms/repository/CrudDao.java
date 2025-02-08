package edu.sc.lms.repository;

import java.util.List;

public interface CrudDao<T,ID> extends SuperDao{
    boolean add(T entity);
    T search(T entity);
    boolean update(T entity);
    boolean delete(ID id);
    List<T> getAll();
}
