package com.arthurbatista.registermvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arthurbatista.registermvvm.model.cep.CEP
import com.arthurbatista.registermvvm.model.client.Client
import org.jetbrains.anko.doAsync

class ClientViewModel(application: Application) : AndroidViewModel(application) {

    private var allClients: LiveData<List<Client>>

    private val repository: ClientRepository = ClientRepository(application)

    init {
        this.allClients = repository.getAllClient()!!
    }

    fun insert(client: Client){
        repository.insertClient(client)
    }

    fun delete(client: Client){
        repository.deleteClient(client)
    }

    fun update(client: Client){
        repository.updateClient(client)
    }

    fun getAllClient(): LiveData<List<Client>>? {
        return repository.getAllClient()
    }

    fun findCEP(cep: String) : CEP? {
        return repository.findCEP(cep)
    }

    fun getCep(): CEP? {
        return repository.clientCEP
    }
}