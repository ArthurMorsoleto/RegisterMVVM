package com.arthurbatista.registermvvm.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.os.AsyncTask





@Database(entities = arrayOf(Client::class) , version = 1)
abstract class ClientDataBase () : RoomDatabase(){


    abstract fun clientDAO(): ClientDAO

    companion object {
        val databaseName = "clients_db"
        private var INSTANCE: ClientDataBase? = null

        fun getInstance(context: Context): ClientDataBase? {
            if(INSTANCE == null) {
                synchronized(ClientDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ClientDataBase::class.java,
                        databaseName
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }

    private val roomCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            PopulateDbAsyncTask(INSTANCE).execute()
        }
    }

    private class PopulateDbAsyncTask (db: ClientDataBase?) : AsyncTask<Void, Void, Void>() {
        var clientDAO: ClientDAO? = null

        init {
            clientDAO = db!!.clientDAO()
        }

        override fun doInBackground(vararg voids: Void): Void? {
            clientDAO!!.insert(Client(0, "Cliente 1", "111.111.111.-11",null, null, null, null,null,null,null))
            clientDAO!!.insert(Client(1, "Cliente 2", "111.111.111.-11",null, null, null, null,null,null,null))
            clientDAO!!.insert(Client(2, "Cliente 3", "111.111.111.-11",null, null, null, null,null,null,null))
            return null
        }
    }
}