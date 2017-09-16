package dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import com.hass.ali.doctorsapp.ApplicationClass;

import java.util.StringTokenizer;


public class DataBaseClass {

	private static SQLiteDatabase database=null;
	public static boolean statusOfDB = true;

	/*public DataBaseClass(ViewPopListScreen context){
		//this.context=context;
	}*/
	
	public static SQLiteDatabase getDatabase()
	{

		DatabaseHandler databaseHandler;
		databaseHandler = new DatabaseHandler(ApplicationClass.getContext());
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		return db;
	}

	


	static public boolean beginTransaction(){
		try
		{                         
			if(database!=null)
			{
				database.beginTransaction();  	
			}
		}
		catch(Exception e){return true;}
		return false;
	}
	
	
	static public boolean committQuery(){
		try
		{                         
			if(database!=null)
			{
				//this makes sure transaction is committed                         
				database.setTransactionSuccessful();
				database.endTransaction();                 
			}
		}
		catch(Exception e){return true;}
		return false;
	}
	
	
	static public boolean closeDatabase(){
		try
		{                         
			if(database!=null)
			{
				database.close();
			}
			database=null;
		}
		catch(Exception e){e.printStackTrace();return true;}
		return false;
	}
	

	
	static public String executeQuery(String query)    throws Exception
	{                
	//	try{
			//Create a table
			database.execSQL(query);
	//	}catch(Exception e){return e.toString();}
		return "DONE";
	}
	
	
	static public void deleteTableValues(SQLiteDatabase database, String tableName)
	{
		database.delete(tableName, null, null);
	}
	
    
	
	//-----------------------------------------------------------------------------------	
	static public String getRecord(String tableName, String[] cols, String where)
	{ 
		Cursor cursor = null;
		String query="SELECT ",result="";
		
		for(int i=0;i<cols.length;i++)
			query+=(i>0)?" , "+cols[i]:" "+cols[i];
		
		query += " from "+tableName+" "+where;	
		
		int index[] = new int[cols.length];
		String values[] = new String[cols.length];
		
		result=" SQL Query : \n"+query+"\n\n";
		
		try 
		{
			//cursor = database.rawQuery("SELECT CALL_ID, SOLUTION_NAME, ROW, DSR_CODE  FROM "+tableName+" Where DSR_CODE=? and ROW=? order by SOLUTION_NAME DESC", new String [] {Configuration.USER_PINCODE,""+Configuration.INDEX});
			cursor = database.rawQuery(query,null);
			
			if(cursor.getCount() > 0) 
			{
				for(int i=0;i<index.length;i++){
				  index[i] = cursor.getColumnIndex(cols[i]); 
				}
				cursor.moveToFirst(); 
				do 
				{ 
				  try
				  {
					  for(int i=0;i<cols.length;i++){
						  values[i] = (String)cursor.getString(index[i]);
						  result +=i+":  "+values[i];
					  }
					cursor.moveToNext();
				  }catch(Exception e){}
				}while(!cursor.isAfterLast());
			} 
		}
		catch(Exception e){result="Error : "+e.toString();}
		finally
		{ 
			if(cursor != null) 
			cursor.close(); 
		}
		return result; 
	}
	
	

	static public String getRecords(){
		String query="";
		Cursor cursor = null;
		
		try 
		{
		    query="SELECT NAME,POPTYPE,SUB_ELEMENT FROM POP Where SECTION=?  order by SECTION DESC";
			
			cursor = database.rawQuery(query, new String[]{"N'00093'"});
			
			if(cursor.getCount() > 0) 
			{
//				int index1 = cursor.getColumnIndex("NAME"); 
//				int index2= cursor.getColumnIndex("POPTYPE"); 
//				int index3 = cursor.getColumnIndex("SUB_ELEMENT");
				cursor.moveToFirst(); 
				do 
				{ 
				  try
				  {
//					String p1 = (String)cursor.getString(index1); 
//					String p2 = (String)cursor.getString(index2); 
//					String p3 = (String)cursor.getString(index3);
//					result+="\n"+p1+" : "+p2+" : "+p3;
					cursor.moveToNext();
				  }catch(Exception e){}
				}while(!cursor.isAfterLast());
			} 
		}
		catch(Exception e){return "Error : \n"+e.toString();}
		finally
		{ 
			if(cursor != null) 
			cursor.close(); 
		}
		
		return query;
	}
	
	
	static public String deleteAllTables(String tables){
		try
		{
		String line="";
     	StringTokenizer tokenizer = new StringTokenizer(tables.trim(),"\n");
     	while(tokenizer.hasMoreElements())
     	{
     	   line = tokenizer.nextToken().toString();
     	   if(line.trim().equals(""))
     		continue;
     	   if(line.trim().startsWith("CREATE TABLE"))
     	   {
     		  line = line.replace("CREATE TABLE", " DROP TABLE IF EXISTS "); 
     		  line = line.substring(0,line.indexOf('('));
     		  System.out.println("DROP SCRIPT--->"+line);
     		  Log.i("DatabaseClass","Query Being Executed--->"+line);
     		  line = DataBaseClass.executeQuery(line.trim());
	       		if(!line.trim().equals("DONE")){
	       			return line;
	       		}
     	   }
     	}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "ERROR";
		}
		return "DONE";
	}
	
	

	static public String createAllTables(String tablesData){
		try
		{
		String insertMsg="",query="",line="";
     	boolean isQuery=false;
     	
		StringTokenizer tokenizer = new StringTokenizer(tablesData.trim(),"\n");
     	while(tokenizer.hasMoreElements())
     	{
     	   line = tokenizer.nextToken().toString();
     	   if(line.trim().equals(""))
     	   continue;
     	   
     	   if(line.trim().startsWith("CREATE TABLE"))
     	   {
     		 isQuery=true;
     		 query+=line;  
     		 continue;   
     	   }
     	   else if(line.trim().startsWith("GO") || line.trim().startsWith(");"))
     	   {
     		    query+=line;  
      		    isQuery=false;
      		    if(query.trim().startsWith("CREATE TABLE")){
          		    query= query.replace("CREATE TABLE", " CREATE TABLE IF NOT EXISTS ");
          		    System.out.println("Query Being Executed--->"+query);
          		    Log.i("DatabaseClass", "Query Being Executed--->"+query);
           			insertMsg = DataBaseClass.executeQuery(query);
	       			if(!insertMsg.trim().equals("DONE")){
	       			    return ("Table Error :\n "+query);
	       			}
      		    }
       			query="";
       			insertMsg="";
     	   }
     	   else
     	   {
     		   if(isQuery && (!(line.trim().startsWith("INSERT INTO"))))
     		   query+=line;
     	   } 	   
     	   
     	}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "ERROR";
		}
		return "DONE";
	}
	

	static public String insertDataTables(String tablesData)
	{
		String line="";
//     	boolean isQuery=true;
		StringTokenizer tokenizer = new StringTokenizer(tablesData.trim(),"\n");
     	while(tokenizer.hasMoreElements())
     	{
     	   line = tokenizer.nextToken().toString();
     	   if(line.trim().equals(""))
    	   continue;
     	   if(line.trim().startsWith("INSERT INTO"))
     	   {
     			try{
     				if(database==null)
     				return "Error : database==null";
     					
     				database.execSQL(line.trim());
     			}catch(Exception e){return "Error In Data Insertion:\n"+e.toString()+"\n\n"+line;}
     	   }
     	}
		return "DONE";
	}
	
	
	
}
