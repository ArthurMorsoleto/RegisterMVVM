package com.arthurbatista.registermvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.arthurbatista.registermvvm.model.Client
import com.arthurbatista.registermvvm.model.ClientDAO
import com.arthurbatista.registermvvm.model.ClientDataBase

class ClientRepository{

    var clientDAO: ClientDAO? = null
    var allClients: LiveData<List<Client>>? = null

    constructor(application: Application){
        val clientDataBase = ClientDataBase.getInstance(application)
        clientDAO = clientDataBase!!.clientDAO()
        allClients = clientDAO!!.getAllClients()
    }

    fun insertClient(client: Client){ InsertClientAsyncTask(this.clientDAO!!).execute(client) }

    fun deleteClient(client: Client){ DeleteClientAsyncTask(this.clientDAO!!).execute(client) }

    fun updateClient(client: Client){ UpdateClientAsyncTask(this.clientDAO!!).execute(client) }

    fun getAllClients(client: Client) : LiveData<List<Client>>? { return allClients }

    class InsertClientAsyncTask(clientDAO: ClientDAO) : AsyncTask<Client, Void, Void>() {

        private var clientDAO: ClientDAO? = clientDAO

        override fun doInBackground(vararg params: Client?): Void? {
            clientDAO!!.insert(params[0]!!)
            return null
        }
    }

    class DeleteClientAsyncTask(clientDAO: ClientDAO) : AsyncTask<Client, Void, Void>() {

        private var clientDAO: ClientDAO? = clientDAO

        override fun doInBackground(vararg params: Client?): Void? {
            clientDAO!!.delete(params[0]!!)
            return null
        }
    }

    class UpdateClientAsyncTask(clientDAO: ClientDAO) : AsyncTask<Client, Void, Void>() {

        private var clientDAO: ClientDAO? = clientDAO

        override fun doInBackground(vararg params: Client?): Void? {
            clientDAO!!.update(params[0]!!)
            return null
        }
    }


}