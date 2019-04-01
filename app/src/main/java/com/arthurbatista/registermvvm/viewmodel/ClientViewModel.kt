package com.arthurbatista.registermvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.arthurbatista.registermvp.model.cep.CEP
import com.arthurbatista.registermvvm.model.client.Client

class ClientViewModel(application: Application) : AndroidViewModel(application) {

    private var allClients: LiveData<List<Client>>

    val repository: ClientRepository = ClientRepository(application)

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

    fun findCEP(cep: String) : CEP {
        return repository.findCEP(cep)
    }
}