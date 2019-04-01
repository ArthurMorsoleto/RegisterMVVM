package com.arthurbatista.registermvvm.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arthurbatista.registermvvm.R
import com.arthurbatista.registermvvm.model.client.Client
import com.arthurbatista.registermvvm.viewmodel.ClientViewModel
import kotlinx.android.synthetic.main.activity_add_client.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddClientActivity : AppCompatActivity() {

    private var clientViewModel: ClientViewModel? = null

    private var isUpdateDelete: Boolean = false

    private var clientToUpdateDelete: Client? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        val intentFromMain = intent

        if(intentFromMain.hasExtra("CLIENT")){
            isUpdateDelete = true
            clientToUpdateDelete = intentFromMain.extras.get("CLIENT") as Client
            setClientData(clientToUpdateDelete!!)
        }

        editCPF.addTextChangedListener(MaskEditUtil.mask(editCPF, MaskEditUtil.FORMAT_CPF))
        editCEP.addTextChangedListener(MaskEditUtil.mask(editCEP, MaskEditUtil.FORMAT_CEP))

        editCEP.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                Snackbar.make(view, "Buscando CEP", Snackbar.LENGTH_LONG).show()
                findClientCEP(editCEP.text.toString())
            }
        }

    }

    private fun setClientData(clientToUpdateDelete: Client) {
        editNome.setText(clientToUpdateDelete.nome)
        editCPF.setText(clientToUpdateDelete.cpf)
        editCEP.setText(clientToUpdateDelete.cep)
        editLogradouro.setText(clientToUpdateDelete.logradouro)
        editNumero.setText(clientToUpdateDelete.numero)
        editCidade.setText(clientToUpdateDelete.cidade)
        editBairro.setText(clientToUpdateDelete.bairro)
        editEstado.setText(clientToUpdateDelete.estado)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_client, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemSalvar -> {
                saveClient()
                true
            }
            R.id.itemMap -> {
                toast("Map")
                true
            }
            R.id.itemVoltar -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveClient() {

        val newClient = Client(null, "", "", "", "", "", "", "", "")

        if(!validarCampos()){

            newClient.nome = editNome.text.toString()
            newClient.cpf = editCPF.text.toString()
            newClient.cep = editCEP.text.toString()
            newClient.logradouro = editLogradouro.text.toString()
            newClient.numero = editNumero.text.toString()
            newClient.bairro = editBairro.text.toString()
            newClient.cidade = editCidade.text.toString()
            newClient.estado = editEstado.text.toString()

            if(isUpdateDelete){
                try {
                    newClient.id = clientToUpdateDelete!!.id
                    clientViewModel = ClientViewModel(application)
                    clientViewModel!!.update(newClient)
                    toast("Cliente: " + newClient.nome + " Atualizado com Sucesso")
                    finish()
                } catch (e: Exception) {
                    Log.e("ErrorSaving", e.message.toString())
                    toast("Erro ao atualizar o cliente")
                }
            }
            else {
                try {
                    clientViewModel = ClientViewModel(application)
                    clientViewModel!!.insert(newClient)
                    toast("Cliente: " + newClient.nome + " Salvo com Sucesso")
                    finish()
                } catch (e: Exception) {
                    Log.e("ErrorSaving", e.message.toString())
                    toast("Erro ao salvar o cliente")
                }
            }
        }
    }

    fun findClientCEP(cep: String){
        clientViewModel = ClientViewModel(application)
        clientViewModel!!.findCEP(cep)
        TODO("finalizar retrofit")

//        editLogradouro.setText(cep.getLogradouro())
//        editBairro.setText(cep.getBairro())
//        editCidade.setText(cep.getLocalidade())
//        editEstado.setText(cep.getUf())
    }

    private fun validarCampos(): Boolean {

        val nomeC = editNome.text.toString()
        val cpfC = editCPF.text.toString()
        val cepC = editCEP.text.toString()
        val lograC = editLogradouro.text.toString()
        val numeroC = editNumero.text.toString()
        val bairroC = editBairro.text.toString()
        val cidadeC = editCidade.text.toString()
        val estadoC = editEstado.text.toString()

        var check = false

        when {
            isEmptyValue(nomeC) -> {
                editNome.requestFocus()
                check = true
            }
            isEmptyValue(cpfC) -> {
                editCPF.requestFocus()
                check = true
            }
            isEmptyValue(cepC) -> {
                editCEP.requestFocus()
                check = true
            }
            isEmptyValue(lograC) -> {
                editLogradouro.requestFocus()
                check = true
            }
            isEmptyValue(numeroC) -> {
                editNumero.requestFocus()
                check = true
            }
            isEmptyValue(bairroC) -> {
                editBairro.requestFocus()
                check = true
            }
            isEmptyValue(cidadeC) -> {
                editCidade.requestFocus()
                check = true
            }
            isEmptyValue(estadoC) -> {
                editEstado.requestFocus()
                check = true
            }
        }
        if (check) {
            val dlg = AlertDialog.Builder(this)
            dlg.setTitle("Aviso")
            dlg.setMessage("Há campo(s) inválido(s): ")
            dlg.setNeutralButton("OK", null)
            dlg.show()
        }
        return check
    }

    //verificar se os campos estão vazios
    private fun isEmptyValue(value: String): Boolean {
        var result = false
        if (TextUtils.isEmpty(value) || value.trim { it <= ' ' }.isEmpty()) {
            result = true
        }
        return result
    }

}
