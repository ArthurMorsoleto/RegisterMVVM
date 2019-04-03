package com.arthurbatista.registermvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import com.arthurbatista.registermvp.model.cep.RetrofitInitializer
import com.arthurbatista.registermvvm.model.cep.CEP
import com.arthurbatista.registermvvm.model.client.Client
import com.arthurbatista.registermvvm.model.client.ClientDAO
import com.arthurbatista.registermvvm.model.client.ClientDataBase
import retrofit2.Call
import retrofit2.Response


class ClientRepository(application: Application) {

    private var clientDAO: ClientDAO? = null
    private var allClients: LiveData<List<Client>>? = null
    var clientCEP: CEP? = null

    init {
        val clientDataBase = ClientDataBase.getInstance(application)
        clientDAO = clientDataBase!!.clientDAO()
        allClients = clientDAO!!.getAllClients()
    }

    fun insertClient(client: Client){ InsertClientAsyncTask(this.clientDAO!!).execute(client) }

    fun deleteClient(client: Client){ DeleteClientAsyncTask(this.clientDAO!!).execute(client) }

    fun updateClient(client: Client){ UpdateClientAsyncTask(this.clientDAO!!).execute(client) }

    fun getAllClient(): LiveData<List<Client>>? {
        Log.i("TESTE_getAllClient", allClients.toString())
        return allClients
    }

    fun findCEP(cepToFind: String) : CEP? {
        val retrofitConfig = RetrofitInitializer()
        val call = retrofitConfig.getCEPService().findCEP(cepToFind)

        call.enqueue(object : retrofit2.Callback<CEP> {
            override fun onResponse(call: Call<CEP>, response: Response<CEP>) {
                if(response.isSuccessful){
                    val returnedCep = response.body()
                    if (returnedCep != null) {
                        clientCEP = CEP(
                            returnedCep.cep,
                            returnedCep.logradouro,
                            returnedCep.bairro,
                            returnedCep.localidade,
                            returnedCep.uf
                        )
                        Log.i("TESTE_CEP", returnedCep.cep + " " +
                                returnedCep.logradouro + " " +
                                returnedCep.bairro + " " +
                                returnedCep.localidade + " " +
                                returnedCep.uf
                        )
                        return
                    }
                }
            }
            override fun onFailure(call: Call<CEP>, t: Throwable) { Log.i("TESTE_CEP","Erro na requisição") }
        })
        return clientCEP
    }



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