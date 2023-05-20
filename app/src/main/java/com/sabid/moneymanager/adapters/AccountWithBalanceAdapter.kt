package com.sabid.moneymanager.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sabid.moneymanager.R
import com.sabid.moneymanager.activities.AddEditAccountActivity
import com.sabid.moneymanager.activities.LedgerActivity
import com.sabid.moneymanager.dataModels.AccountWithBalance

class AccountWithBalanceAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var accountWithBalanceList: List<AccountWithBalance> = emptyList()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_account, viewGroup, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val accountWithBalance = accountWithBalanceList[position]
        val viewHolder: AccountViewHolder = holder as AccountViewHolder
        viewHolder.tvAccountName.text = accountWithBalance.name
        viewHolder.tvAccountGroup.text = accountWithBalance.groupName
        viewHolder.tvAccountBalance.text = accountWithBalance.balance.toString()

        viewHolder.itemView.setOnClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    LedgerActivity::class.java
                ).putExtra("accountId", accountWithBalance.id)
            )
        }
        viewHolder.itemView.setOnLongClickListener {
            it.context.startActivity(
                Intent(
                    it.context,
                    AddEditAccountActivity::class.java
                ).putExtra("accountId", accountWithBalance.id)
            )
            true
        }
    }

    override fun getItemCount(): Int {
        return accountWithBalanceList.size
    }

    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAccountName: TextView
        var tvAccountGroup: TextView
        var tvAccountBalance: TextView

        init {
            tvAccountName = itemView.findViewById(R.id.tvAccountName)
            tvAccountGroup = itemView.findViewById(R.id.tvAccountGroup)
            tvAccountBalance = itemView.findViewById(R.id.tvAccountBalance)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAccountWithBalanceList(newList: List<AccountWithBalance>) {
        accountWithBalanceList = newList
        notifyDataSetChanged()
    }
}