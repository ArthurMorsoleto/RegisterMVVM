package com.arthurbatista.registermvvm.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurbatista.registermvvm.R
import com.arthurbatista.registermvvm.model.client.Client
import kotlinx.android.synthetic.main.client_item.view.*


class ClientAdapter(val clickListener: (Client) -> Unit) : RecyclerView.Adapter<ClientAdapter.MyViewHolder>(){

    var clientList: List<Client>? = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.client_item, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(clientList!!.isEmpty()) 0 else clientList!!.size
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val currentUser = clientList?.get(position)
        viewHolder.let {
            if (currentUser != null) {
                it.bindView(currentUser, clickListener)
            }
        }
    }

    fun setClients(list: List<Client>) {
        this.clientList = list
        notifyDataSetChanged()
    }

    fun getClientPosition(position: Int): Client {
        return this.clientList!![position]
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(user: Client, clickListener: (Client) -> Unit){
            val txtName = itemView.txtUserName
            val txtCPF = itemView.txtUserCPF

            itemView.setOnClickListener { clickListener(user) }

            txtName.text = user.nome
            txtCPF.text = user.cpf
        }
    }

}