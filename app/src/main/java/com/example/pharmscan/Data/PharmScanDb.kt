package com.example.pharmscan.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pharmscan.Data.DAO.*
import com.example.pharmscan.Data.Tables.*

// List all tables here and update version for each database change
// NOTE: Increament version each time any database changes are made
@Database(
    entities = [
        HostCompName::class,
        CollectedData::class,
        SystemInfo::class,
        PSNdc::class,
        Settings::class
    ],
    version = 6,
    exportSchema = false
)
abstract class PharmScanDb: RoomDatabase() {
    // These functions will return the Dao's for each table
    abstract fun getHostCompNameDao(): HostCompNameDao
    abstract fun getCollectedDataDao(): CollectedDataDao
    abstract fun getSystemInfoDao(): SystemInfoDao
    abstract fun getPSNdcDao(): PSNdcDao
    abstract fun getSettingsDao(): SettingsDao

    // companion object is a static object accessed in this class
//    companion object {
//        // @Volatile means all thread that may have this instance var will get
//        // updated immediately. This instance is of type PharmScanDb class
//        @Volatile
//        private var instance: PharmScanDb? = null
//        private val LOCK = Any()
//
//        // When this database class is created the invoke function will automatically
//        // be called. If instance is null create the database using synchronized(LOCK)
//        // so this cannot be called by another thread at the same time. If instance is
//        // not null then database was already created and just return the instance
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also {
//                instance = it }
//        }
//
//        // This function calls the Room database builder which will create the db
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                PharmScanDb::class.java,
//                "PharmScanDb.db"
//            ).build()
//    }





    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PharmScanDb? = null

        fun getDatabase(context: Context): PharmScanDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PharmScanDb::class.java,
                    "PharmScan.db"
                )
                    //.createFromAsset("PharmScan.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }



}

// TODO used for testing. Remove if not needed anymore
//insert into CollectedData values ("111", "1234567", "11111111111", "123456", "12345678", "12345678", "123", "R", "1111", "123", "1001", "09/03/21", "12345", "P", "12345678", 1)
//insert into CollectedData values ("222", "1234567", "22222222222", "123456", "12345678", "12345678", "123", "R", "2222", "123", "1002", "09/03/21", "12345", "P", "12345678", 2)
//insert into CollectedData values ("333", "1234567", "33333333333", "123456", "12345678", "12345678", "123", "R", "3333", "123", "1003", "09/03/21", "12345", "P", "12345678", 3)
//insert into CollectedData values ("444", "1234567", "44444444444", "123456", "12345678", "12345678", "123", "R", "4444", "123", "1004", "09/03/21", "12345", "P", "12345678", 4)
//insert into CollectedData values ("555", "1234567", "55555555555", "123456", "12345678", "12345678", "123", "R", "5555", "123", "1005", "09/03/21", "12345", "P", "12345678", 5)
//insert into CollectedData values ("666", "1234567", "66666666666", "123456", "12345678", "12345678", "123", "R", "6666", "123", "1006", "09/03/21", "12345", "P", "12345678", 6)

//insert into PSNdc values ("11111111111", "12345678", "12345678", 1)
//insert into PSNdc values ("22222222222", "12345678", "12345678", 2)
//insert into PSNdc values ("33333333333", "12345678", "12345678", 3)
//insert into PSNdc values ("44444444444", "12345678", "12345678", 4)