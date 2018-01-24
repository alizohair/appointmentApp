package dataBase;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;


public class DBConnection {

	private static SQLiteDatabase database=null;
	DatabaseHandler databaseHandler;

	public static String createConnection(){
		try{
		   database = DataBaseClass.getDatabase();

		}catch(Exception e)
		{
			return "Error:\n"+e.toString();
		}
		return "DONE";
	}


	public static String executeQuery(String query, String[] values) throws SQLException {
	//try{
			database.execSQL(query,values);
		//}catch(Exception e)
		//{return false;}
		//return true;
		return "DONE";
	}


	public static boolean executeQuery(String query)
	{
		try{
			database.execSQL(query);
		}catch(Exception e){return false;}
		return true;
	}

	public static Cursor rawQuery(String query, String[] where)throws SQLException
	{
		//try{

				/*int StrtIndex =query.toUpperCase().indexOf("FROM");
					String queryFrom = query.substring(0, StrtIndex + 5);
		queryFrom = queryFrom.toUpperCase();
		query = queryFrom + query.substring(StrtIndex+5 ,query.length());*/
			return  database.rawQuery(query,where);
		//}catch(Exception e){return null;}
	}


	public static boolean insertRow(String table, ContentValues values)
	{


		try{


			database.insertOrThrow(table,null, values);
		}catch(Exception e){

			e.printStackTrace();
			return false;
		}
		return true;
	}



	public static boolean deleteSKURecord(String table, String where, String[] values)
	{
		try{
			// db.delete("tbl_states", "id=?", new String[] {Long.toString(countryId)});
			database.delete(table, where ,values);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}



	public static boolean updateRecord(String table, ContentValues upDateValues, String whereCols, String[] whereValues)throws Exception
	{
	//	try{
			if(database.update(table, upDateValues , whereCols, whereValues)<1)
				throw new RuntimeException();
		//}catch(Exception e){return false;}
		return true;
	}



	public static String deleteTable(String table){
		try{
			database.delete(table, null, null);
		}catch(Exception e){return "Error:\n"+e.toString();}
		return "DONE";
	}



	public static boolean insertRecord(String table, ContentValues values) {
		try {
			database.insertOrThrow(table, null, values);
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean setRollback(boolean condition){
		boolean result=false;
		if(condition && (database!=null)){
	 	 database.endTransaction();
	 	 result=true;
		}
		return result;
	}
public static boolean setBeginTransaction(boolean condition){
		boolean result=false;
		if(condition && (database!=null)){
	 	 database.beginTransaction();
	 	 result=true;
		}
		return result;
	}



	static public boolean committTransaction(){
		try
		{
			if(database!=null)
			{
				//this makes sure transaction is committed
				database.setTransactionSuccessful();
				database.endTransaction();
			}
		}
		catch(Exception e){return false;}
		return true;
	}

	public static String closeConnection(){
		try{
		   if(database!=null){
			   database.endTransaction();
			   database.close();
			   database=null;
			   DataBaseClass.closeDatabase();
		   }
		}catch(Exception e){return "Error:\n"+e.toString();}
		return "DONE";
	}

	public static boolean setQueryTransaction(boolean condition){
		if(condition)
		database.setTransactionSuccessful();
		else
		database.endTransaction();
		return condition;
	}

	//creatinf for scheme merging so resolved this ....
	public static SQLiteDatabase getDatabasebj(){
		return database;
	}

}
