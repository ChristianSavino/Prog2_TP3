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

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     sqLiteDatabase.execSQL(BD_Info.usersCreationTable);
     sqLiteDatabase.execSQL(BD_Info.parkingsCreationTable);
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

    public void createUser(Users user){
        ContentValues values = new ContentValues();

        values.put(BD_Info.usersEmailColumn,user.getEmail());
        values.put(BD_Info.usersNameColumn,user.getName());
        values.put(BD_Info.usersPasswordColumn,user.getPassword());

        this.getWritableDatabase().insert(BD_Info.usersTable,null,values);
    }

    public Users getUser(Users user) {
        ContentValues values = new ContentValues();

        return null;
    }

    public void createParking(Parkings parkings){
        ContentValues values = new ContentValues();

        values.put(BD_Info.parkingsUser_idColumn,parkings.getUser_id());
        values.put(BD_Info.parkingsPatentColumn,parkings.getPatent());
        values.put(BD_Info.parkingsTimeColumn,parkings.getTime());

        this.getWritableDatabase().insert(BD_Info.parkingsTable,null,values);
    }

    public void deleteParking(Parkings parkings){
        ContentValues values = new ContentValues();

        values.put(BD_Info.parkingsColumnId,parkings.getId());
        String id = String.valueOf(parkings.getId());

        this.getWritableDatabase().delete(BD_Info.parkingsTable,"ID=?",new String[]{id});
    }

    public ArrayList<Parkings> getAllParkings()
    {
        ArrayList<Parkings> parkingsList = new ArrayList<Parkings>();
        Cursor mcursor = null;
        mcursor = this.getReadableDatabase().query(BD_Info.parkingsTable, new String[]{BD_Info.parkingsColumnId,BD_Info.parkingsPatentColumn,BD_Info.parkingsTimeColumn},null,null,null,null,null);
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
