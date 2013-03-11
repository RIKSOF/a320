package com.riksof.a320.datasource;

import java.util.List;

import com.riksof.a320.remote.RemoteObject;

/**
 * Interface that contains common methods that need to be implemented by every datasource class
 * 
 * @author rizwan
 *
 */
public interface IDataSource {

	public void save(String id, RemoteObject obj) throws DataSourceException;

	public void saveOrUpdate(String id, RemoteObject obj) throws DataSourceException;

	public void update(String id, RemoteObject obj) throws DataSourceException;

	public RemoteObject fetchById(RemoteObject obj) throws DataSourceException;

	public List<RemoteObject> fetchAll(String id) throws DataSourceException;

	public List<RemoteObject> fetch(RemoteObject obj) throws DataSourceException;

	public int fetchRowCount() throws DataSourceException;
}
