package br.com.povengenharia.fitlife.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CalcDao {

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * FROM Calc WHERE type = :type")
    fun getRegisterByType(type: String) : List<Calc>

    @Update
    fun update(calc: Calc)

    @Query("DELETE FROM Calc WHERE id = :calcId ")
    fun delete(calcId: Int)
}