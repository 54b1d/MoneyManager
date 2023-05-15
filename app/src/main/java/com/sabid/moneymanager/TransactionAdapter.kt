package com.sabid.moneymanager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var transactionList: List<Transaction> = emptyList()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_transaction, viewGroup, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transaction = transactionList[position]
        val viewHolder: TransactionViewHolder = holder as TransactionViewHolder
        viewHolder.trxDate.text = transaction.trxDate
        viewHolder.accountFrom.text = transaction.accountFromId.toString()
        viewHolder.accountTo.text = transaction.accountToId.toString()
        viewHolder.amount.text = transaction.amount.toString()
        viewHolder.narration.text = transaction.narration
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var trxDate: TextView
        var accountFrom: TextView
        var accountTo: TextView
        var amount: TextView
        var narration: TextView

        init {
            trxDate = itemView.findViewById(R.id.txtDate)
            accountFrom = itemView.findViewById(R.id.txtAccountFrom)
            accountTo = itemView.findViewById(R.id.txtAccountTo)
            amount = itemView.findViewById(R.id.txtAmount)
            narration = itemView.findViewById(R.id.txtNarration)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTransactionList(newList: List<Transaction>) {
        transactionList = newList
        notifyDataSetChanged()
    }
}