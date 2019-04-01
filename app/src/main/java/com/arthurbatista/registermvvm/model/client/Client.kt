package com.arthurbatista.registermvvm.model.client

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "client_table")
class Client(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo(name = "nome")
    var nome: String,
    @ColumnInfo(name = "cpf")
    var cpf: String,
    @ColumnInfo(name = "cep")
    var cep: String?,
    @ColumnInfo(name = "logradouro")
    var logradouro: String?,
    @ColumnInfo(name = "bairro")
    var bairro: String?,
    @ColumnInfo(name = "numero")
    var numero: String?,
    @ColumnInfo(name = "cidade")
    var cidade: String?,
    @ColumnInfo(name = "estado")
    var estado: String?
) : Serializable