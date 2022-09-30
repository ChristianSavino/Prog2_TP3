package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.Parkings;
import model.Users;

public class DbHelper extends SQLiteOpenHelper {

    public static final String usersTable = "users";
    public static final String usersColumnId = "ID_u";
    public static final String usersNameColumn = "Name";
    public static final String usersEmailColumn = "Email";
    public static final String usersPasswordColumn = "Password";

    public static final String parkingsTable = "parkings";
    public static final String parkingsColumnId = "ID_p";
    public static final String parkingsUser_idColumn = "user_id"; //!!
    public static final String parkingsPatentColumn = "Patent";
    public static final String parkingsTimeColumn = "Time";

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS users(ID_u integer primary key autoincrement, Name text, Email text, Password text);";
        sqLiteDatabase.execSQL(query);

        /*String query2 = "CREATE TABLE IF NOT EXISTS parkings(ID_p integer primary key autoincrement, Patent text, Time integer, foreign key (ID_u) references users(ID_u));";
        sqLiteDatabase.execSQL(query2);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDB()
    {
        this.getWritableDatabase();
    }

    public void closeDB()
    {
        this.close();
    }

    public boolean createUser(Users user){
        ContentValues values = new ContentValues();

        values.put(usersEmailColumn,user.getEmail());
        values.put(usersNameColumn,user.getName());
        values.put(usersPasswordColumn,user.getPassword());

        long res = this.getWritableDatabase().insert(usersTable,null,values);

        if(res == -1) {
            return false;
        }
        else{ return true; }
    }

    public boolean consultarUserPass (String name, String password){
        Cursor c = null;
        c = this.getReadableDatabase().query(usersTable, new String[]{usersColumnId,
                 usersNameColumn, usersEmailColumn, usersPasswordColumn},
                "Name like '"+name+"' and Password like '"+password+"'",
                null, null, null, null);
        if(c.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Users getUser(Users user) {
        ContentValues values = new ContentValues();

        return null;
    }

    public void createParking(Parkings parkings){
        ContentValues values = new ContentValues();

        values.put(parkingsUser_idColumn,parkings.getUser_id());
        values.put(parkingsPatentColumn,parkings.getPatent());
        values.put(parkingsTimeColumn,parkings.getTime());

        this.getWritableDatabase().insert(parkingsTable,null,values);
    }

    public void deleteParking(Parkings parkings){
        ContentValues values = new ContentValues();

        values.put(parkingsColumnId,parkings.getId());
        String id = String.valueOf(parkings.getId());

        this.getWritableDatabase().delete(parkingsTable,"ID=?",new String[]{id});
    }

    public ArrayList<Parkings> getAllParkings()
    {
        ArrayList<Parkings> parkingsList = new ArrayList<Parkings>();
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query(parkingsTable, new String[]{parkingsColumnId,parkingsPatentColumn,parkingsTimeColumn},null,null,null,null,null);
        if(mcursor.moveToFirst())
        {
            do{
                Parkings parkings = new Parkings();
                parkings.setId(mcursor.getInt(0));
                parkings.setPatent(mcursor.getString(1));
                parkings.setTime(mcursor.getInt(2));
                parkingsList.add(parkings);
            }
            while(mcursor.moveToNext());
        }
        return parkingsList;
    }
}
