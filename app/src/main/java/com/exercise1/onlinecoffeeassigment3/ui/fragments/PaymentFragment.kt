package com.exercise1.onlinecoffeeassigment3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.exercise1.onlinecoffeeassigment3.R
import com.exercise1.onlinecoffeeassigment3.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupListener()
    }

    private fun setupListener() {
        binding.continueBtn3.setOnClickListener{

        }
    }


    private fun setupUI() {
        showSpinner()
        setSpinnerAdapter()
        showCardNumberAndExpiry()
    }


    private fun showSpinner() {
        binding.btnPlaceOrderOne.setOnClickListener {
            if (isValidateNameAndNumber()) {
                binding.clSpinner.visibility = View.VISIBLE
                binding.btnPlaceOrderOne.visibility = View.GONE
            }
        }
    }

    private fun isValidateNameAndNumber(): Boolean {
        //validation = to make sure the user add the number and name in the app

        val isVal: Boolean
        if (binding.editFullName.length() == 0) {// it means the user still not give any data
            binding.editFullName.error = "Please Enter Your Name" //it means give the error to user

        } else {
            binding.editFullName.error = null

        }
        if (binding.editPhoneNumber.length() != 10) {
            binding.editPhoneNumber.error = "Failed Phone Number"
            isVal = false
        } else {
            binding.editPhoneNumber.error = null
            isVal = true
        }
        return isVal
    }

    private fun setSpinnerAdapter() {
        //create adapter for spinner -> arrayCard
        ArrayAdapter.createFromResource(
            binding.spinnerCardType.context,
            R.array.CardType,
            android.R.layout.simple_spinner_item

        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCardType.adapter = adapter
        }
    }

    private fun showCardNumberAndExpiry() {
        binding.spinnerCardType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // selectedItem -> it will give me the position that the user selected
                val selectedItemPosition =parent?.getItemAtPosition(position)
                cardVisibility(selectedItemPosition.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                cardVisibility(null)
            }
        }
    }

    //cardVisibility -> I control the visibility of the card number and expiry
    private fun cardVisibility(selectedItem:String?){
        binding.cardNumberExpiry.visibility = if (selectedItem == "_") View.GONE else View.VISIBLE
        binding.btnPlaceOrder2.visibility=if (selectedItem == "_") View.VISIBLE else View.GONE
        binding.continueBtn3.visibility = if (selectedItem == "_") View.GONE else View.VISIBLE

    }



}