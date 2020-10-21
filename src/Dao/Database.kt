package com.hwk1.Dao

import com.almworks.sqlite4java.SQLite
import com.almworks.sqlite4java.SQLiteConnection
import java.io.File


class Database constructor (var dbName : String = "") {
    init {
        // Creating database
        dbName = "C:\\Users\\Iftekharul Islam\\Desktop\\411 HW1 Database\\claimDB.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()

        // Creating table with four columns
        val sqlStmt = "create table if not exists claim (id text, title text, date txt, isSolved txt)"
        dbConn.exec(sqlStmt)
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }
}