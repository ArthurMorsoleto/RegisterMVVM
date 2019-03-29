package com.arthurbatista.registermvvm.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ClientDAO {

    @Insert
    fun insert(client: Client)

    @Update
    fun update(client: Client)

    @Delete
    fun delete(client: Client)

    @Query("SELECT * FROM client_table ORDER BY id ASC")
    fun getAllClients(): LiveData<List<Client>>
}