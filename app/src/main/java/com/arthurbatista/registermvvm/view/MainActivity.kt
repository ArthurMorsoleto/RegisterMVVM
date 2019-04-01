package com.arthurbatista.registermvvm.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Toast
import com.arthurbatista.registermvvm.R
import com.arthurbatista.registermvvm.model.client.Client
import com.arthurbatista.registermvvm.viewmodel.ClientViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var clientViewModel: ClientViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = recyclerView_Clients
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        val clientAdapter = ClientAdapter(){ userItem: Client -> itemClicked(userItem)}
        recyclerView.adapter = clientAdapter

        clientViewModel = ViewModelProviders.of(this@MainActivity).get(ClientViewModel::class.java)
        clientViewModel!!.getAllClient()!!.observe(this, Observer {
            if (it != null) {
                clientAdapter.setClients(it)
            }
        } )

        btnAddClient.setOnClickListener { view ->
            Snackbar.make(view, "Adicionar Cliente", Snackbar.LENGTH_LONG).show()
            val intent = Intent(applicationContext, AddClientActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean { return false }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(confirmDelete(clientAdapter.getClientPosition(viewHolder.adapterPosition))){
                    clientViewModel!!.delete(clientAdapter.getClientPosition(viewHolder.adapterPosition))
                    Toast.makeText(this@MainActivity, "Cliente excluido", Toast.LENGTH_SHORT).show()
                }
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun itemClicked(clickedUser: Client) {
        val userEditIntent = Intent(this, AddClientActivity::class.java)
        userEditIntent.putExtra("CLIENT", clickedUser)
        startActivity(userEditIntent)
    }

    private fun confirmDelete(clientToDelete: Client): Boolean {
        var result = false
        //abrir dialog
        val dialog = AlertDialog.Builder(this@MainActivity)
        dialog.setTitle("Confirmar exclusão")
        dialog.setMessage("Deseja excluir o usuário: " + clientToDelete.nome + "?")
        dialog.setPositiveButton("Sim") { _, _ ->
            result = true
        }
        dialog.setNegativeButton("Não") { _, _ ->
            result = false
        }
        dialog.show()
        return result
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
