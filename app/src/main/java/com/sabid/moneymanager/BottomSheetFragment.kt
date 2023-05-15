package com.sabid.moneymanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sabid.moneymanager.databinding.FragmentBottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomSheetBinding
    var selectedValue = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        //val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        binding.btnIncome.setOnClickListener {
            selectedValue = 1
        }
        binding.btnExpense.setOnClickListener { selectedValue = 2 }
        binding.btnCurrent.setOnClickListener { selectedValue = 3 }

        return binding.root.rootView
    }
}