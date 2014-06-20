package classe;

    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by ribardbastien on 29/04/2014.
 */

    public class MaBaseSQLite extends SQLiteOpenHelper {


    /*******************************************************************
     * CONTACT
     ******************************************************************/

        private static final String TABLE_CONTACTS = "table_contacts";
        private static final String COL_ID_CONTACT = "ID_CONTACT";
        private static final String COL_ID_UTILISATEUR = "ID_UTILISATEUR";
        private static final String COL_NOM = "NOM";
        private static final String COL_PRENOM = "PRENOM";
        private static final String COL_TELEPHONE = "TELEPHONE";
        private static final String COL_ADRESSE = "ADRESSE";
        private static final String COL_CP = "CP";
        private static final String COL_VILLE = "VILLE";
        private static final String COL_DATE_NAISSANCE = "DATE_NAISSANCE";
        private static final String COL_ID_CONTACT_SERVEUR = "ID_CONTACT_SERVEUR";
        private static final String COL_LONGITUDE = "LONGITUDE";
        private static final String COL_LATITUDE = "LATITUDE";



        private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + " ("
                + COL_ID_CONTACT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ID_UTILISATEUR + " INT, "
                + COL_NOM + " TEXT NOT NULL, " + COL_PRENOM + " TEXT NOT NULL, "
                + COL_TELEPHONE + " TEXT, " + COL_ADRESSE + " TEXT, "
                + COL_CP + " TEXT, " + COL_VILLE + " TEXT, "
                + COL_DATE_NAISSANCE + " TEXT , "+ COL_ID_CONTACT_SERVEUR + " INT, "+ COL_LONGITUDE +" REAL , "
                + COL_LATITUDE + " REAL );";

    /*******************************************************************
     * UTILISATEURS
     ******************************************************************/

    private static final String TABLE_UTILISATEURS = "table_utilisateurs";
    private static final String COL_ID_USER = "ID";
    private static final String COL_LOGIN = "LOGIN";
    private static final String COL_PASSWORD = "PASSWORD" ;
    private static final String COL_ID_UTILISATEUR_SERVEUR = "ID_UTILISATEUR_SERVEUR ";


    private static final String CREATE_TABLE_UTILISATEURS = "CREATE TABLE " + TABLE_UTILISATEURS + " ("
            + COL_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_LOGIN + " TEXT NOT NULL, " + COL_PASSWORD + " TEXT NOT NULL , " +
            COL_ID_UTILISATEUR_SERVEUR+" INT );";



    /*******************************************************************
     * Reseaux Sociaux
     ******************************************************************/

    private static final String TABLE_RESEAUX_SOCIAUX = "table_reseaux_sociaux";
    private static final String COL_ID_RESEAU_SOCIAL = "ID_RESEAU_SOCIAL";
    private static final String COL_PSEUDO = "PSEUDO";
    private static final String COL_TYPE_RESEAU_SOCIAL = "TYPE_RESEAU_SOCIAL";


    private static final String CREATE_TABLE_RESEAUX_SOCIAUX = "CREATE TABLE " + TABLE_RESEAUX_SOCIAUX + " ("
            + COL_ID_RESEAU_SOCIAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_PSEUDO + " TEXT NOT NULL, " +
            COL_TYPE_RESEAU_SOCIAL + " TEXT NOT NULL, " +COL_ID_CONTACT + " INT);";


    /*******************************************************************
     *POSSEDE
     ******************************************************************/

    private static final String TABLE_POSSEDE = "table_possede";
    private static final String COL_ID_RESEAU_SOCIAL_POSSEDE = "ID_RESEAU_SOCIAL_POSSEDE";
    private static final String COL_ID_CONTACT_POSSEDE = "ID_CONTACT_POSSEDE";


    private static final String CREATE_TABLE_POSSEDE = "CREATE TABLE " + TABLE_POSSEDE + " ("
            + COL_ID_RESEAU_SOCIAL_POSSEDE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ID_CONTACT_POSSEDE + " INT NOT NULL);";


    public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_TABLE_UTILISATEURS);
        db.execSQL(CREATE_TABLE_CONTACTS);
        db.execSQL(CREATE_TABLE_RESEAUX_SOCIAUX);
        db.execSQL(CREATE_TABLE_POSSEDE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_CONTACTS + ";");
        db.execSQL("DROP TABLE " + TABLE_UTILISATEURS + ";");
        db.execSQL("DROP TABLE " + TABLE_RESEAUX_SOCIAUX + ";");
        db.execSQL("DROP TABLE " + TABLE_POSSEDE + ";");
        onCreate(db);
    }


}

