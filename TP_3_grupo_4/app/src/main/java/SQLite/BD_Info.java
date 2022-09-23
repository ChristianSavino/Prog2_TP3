package SQLite;

public class BD_Info {

    public static final String usersCreationTable = "CREATE TABLE IF NOT EXISTS users(ID integer primary key autoincrement, Name text, Email text, Password text)";
    public static final String usersTable = "users";
    public static final String usersColumnId = "ID";
    public static final String usersNameColumn = "Name";
    public static final String usersEmailColumn = "Email";
    public static final String usersPasswordColumn = "Password";

    public static final String parkingsCreationTable = "CREATE TABLE IF NOT EXISTS parkings(ID integer primary key autoincrement, user_id integer foreign key, Patent text, Time integer)";
    public static final String parkingsTable = "parkings";
    public static final String parkingsColumnId = "ID";
    public static final String parkingsUser_idColumn = "user_id";
    public static final String parkingsPatentColumn = "Patent";
    public static final String parkingsTimeColumn = "Time";
}
