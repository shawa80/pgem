package com.shawtonabbey.pgem.database;
import java.sql.*;
import java.util.*;


public class ARecordSet
{
	PreparedStatement statement;
	ResultSet resultSet;
 	ResultSetMetaData columnInfo;
	SQLWarning warnings;

	String errors = "";
	String message = "";

	public ARecordSet(PreparedStatement s)
	{
		statement = s;

		resultSet = null;
	}
	public boolean execute()
	{

		try {
						
			if (statement.execute())
			{
				//we have a recordset;
				resultSet = statement.getResultSet();
				columnInfo = resultSet.getMetaData();
			}
			else
			{
				//no result set came back;
				message += "\n\nCount " + statement.getUpdateCount();
				resultSet = null;
			}

			warnings = statement.getWarnings();

		}
		catch (SQLException e)
		{
			System.out.println("Exception " + e.toString());
			errors += "\n\n" + e.getMessage();
			System.out.println("Error on db access");
			return true;
		}
		return false;
	}
	public String get(int col)
	{
		if (resultSet == null)
		{
			return "";
		}

		try {
			return resultSet.getString(col);
		}
		catch (SQLException e)
		{
		}
		return "";
	}
	public String get(String col)
	{
		if (resultSet == null)
		{
			return "";
		}

		try {
			return resultSet.getString(col);
		}
		catch (SQLException e)
		{
		}
		return "";
	}
	public boolean next()
	{
		if (resultSet == null)
		{
			return false;
		}

		try {
			return resultSet.next();
		}
		catch (SQLException e)
		{

		}
		return false;

	}
	public void close()
	{
		if (resultSet == null)
		{
			return;
		}

		try {
			resultSet.close();
			statement.close();
		}
		catch (SQLException e)
		{

		}

	}
	public int getColumnCount()
	{
		if (resultSet == null)
		{
			return 0;
		}


		try {
			return columnInfo.getColumnCount();
		}
		catch (SQLException e)
		{

		}
		return 0;
	}
	public String getColumnName(int column)
	{
		if (resultSet == null)
		{
			return "";
		}


		try {
			return columnInfo.getColumnName(column);
		}
		catch (SQLException e)
		{

		}
		return "";
	}
	public String[] getColumnNames()
	{
		String[] columns = new String[getColumnCount()];
		for (int i = 0; i < getColumnCount(); i++)
		{
			columns[i] = getColumnName(i+1);
		}
		return columns;
		
	}
	public String getWarning()
	{
		if (warnings == null)
			return "";
		return warnings.toString();
	}
	public String getErrors()
	{
		return errors;
	}
	public String getMessages()
	{
		return message;
	}
	public Object [][] getArray()
	{
		Vector<Object[]> rows = new Vector<Object[]>();
		Object [] data;
		Object[][] finalData;

		while (next())
		{
			data = new Object[getColumnCount()];
			for (int i = 0; i < getColumnCount(); i++)
			{
				data[i] = get(i+1);
			}
			rows.add(data);
		}

		finalData = new Object[rows.size()][getColumnCount()];
		for (var j = 0; j < rows.size(); j++)
		{
			data = (Object[])rows.elementAt(j);
			for (var k = 0; k < getColumnCount(); k++)
			{
				finalData[j][k] = data[k];
			}
		}
				
		return  finalData;

	}


}
