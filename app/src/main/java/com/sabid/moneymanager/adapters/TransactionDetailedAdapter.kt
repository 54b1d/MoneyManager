package com.sabid.moneymanager.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sabid.moneymanager.R
import com.sabid.moneymanager.activities.AddEditTransactionActivity
import com.sabid.moneymanager.activities.LedgerActivity
import com.sabid.moneymanager.dataModels.TransactionDetailed


class TransactionDetailedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var transactionDetailedList: List<TransactionDetailed> = emptyList()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_transaction, viewGroup, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transactionDetailed = transactionDetailedList[position]
        val viewHolder: TransactionViewHolder = holder as TransactionViewHolder
        viewHolder.trxDate.text = transactionDetailed.trxDate
        viewHolder.tvTransactionType.text = transactionDetailed.transactionTypeName
        viewHolder.accountFrom.text = transactionDetailed.accountFromName
        viewHolder.accountTo.text = transactionDetailed.accountToName
        viewHolder.amount.text = transactionDetailed.amount.toString()
        viewHolder.narration.text = transactionDetailed.narration

        viewHolder.accountFrom.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context, LedgerActivity::class.java
                ).putExtra("accountId", transactionDetailed.accountFromId)
            )
        }
        viewHolder.accountTo.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context, LedgerActivity::class.java
                ).putExtra("accountId", transactionDetailed.accountToId)
            )
        }

        viewHolder.itemView.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context, AddEditTransactionActivity::class.java
                ).putExtra("transactionId", transactionDetailed.id)
            )
        }
    }

    override fun getItemCount(): Int {
        return transactionDetailedList.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var trxDate: TextView
        var tvTransactionType: TextView
        var accountFrom: TextView
        var accountTo: TextView
        var amount: TextView
        var narration: TextView

        init {
            trxDate = itemView.findViewById(R.id.txtDate)
            tvTransactionType = itemView.findViewById(R.id.tvTransactionType)
            accountFrom = itemView.findViewById(R.id.txtAccountFrom)
            accountTo = itemView.findViewById(R.id.txtAccountTo)
            amount = itemView.findViewById(R.id.txtAmount)
            narration = itemView.findViewById(R.id.txtNarration)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTransactionList(newList: List<TransactionDetailed>) {
        transactionDetailedList = newList
        notifyDataSetChanged()
    }
}