package com.hwk1.Dao.Claim

import com.hwk1.Dao.Dao
import com.hwk1.Dao.Database
import java.util.*

class ClaimDao : Dao() {
    fun addClaim(cObj : Claim) {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. Prepare the SQL statement

        // Creating random UUID and converting it into string
        val uuid : String = UUID.randomUUID().toString();
        sqlStmt = "insert into claim (id, title, date, isSolved) values ('${uuid}', '${cObj.title}', '${cObj.date}', '${cObj.isSolved}')"

        // 3. Submit the SQL statement
        conn?.exec(sqlStmt)
    }

    fun getAllClaims() : List<Claim> {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. Prepare the SQL statement
        sqlStmt = "select id, title, date, isSolved from claim"

        // 3. Submit the SQL statement
        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // 4. Convert each record into Claim object
        while (st?.step()!!) {
            // Converting string UUID into UUID datatype
            val id = UUID.fromString(st.columnString(0))

            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnString(3)
            claimList.add(Claim(id, title, date, isSolved.toBoolean()))
        }

        return claimList
    }
}