import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    object LotteryRecords : BaseColumns {
        const val TABLE_NAME = "lottery"
        const val COLUMN_NAME_ATTRIBUTE1 = "date"
        const val COLUMN_NAME_ATTRIBUTE2 = "round"
        const val COLUMN_NAME_BALL1 = "ball1"
        const val COLUMN_NAME_BALL2 = "ball2"
        const val COLUMN_NAME_BALL3 = "ball3"
        const val COLUMN_NAME_BALL4 = "ball4"
        const val COLUMN_NAME_BALL5 = "ball5"
        const val COLUMN_NAME_BALL6 = "ball6"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Companion.SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(Companion.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Records.db"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${LotteryRecords.TABLE_NAME}"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${LotteryRecords.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${LotteryRecords.COLUMN_NAME_ATTRIBUTE1} TEXT," +
                    "${LotteryRecords.COLUMN_NAME_ATTRIBUTE2} TEXT," +
                    "${LotteryRecords.COLUMN_NAME_BALL1} TEXT)," +
                    "${LotteryRecords.COLUMN_NAME_BALL2} TEXT)," +
                    "${LotteryRecords.COLUMN_NAME_BALL3} TEXT)," +
                    "${LotteryRecords.COLUMN_NAME_BALL4} TEXT)," +
                    "${LotteryRecords.COLUMN_NAME_BALL5} TEXT)," +
                    "${LotteryRecords.COLUMN_NAME_BALL6} TEXT)"

    }
}