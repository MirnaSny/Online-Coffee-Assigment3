package com.exercise1.onlinecoffeeassigment3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.exercise1.onlinecoffeeassigment3.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(){

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        return binding.root
    }


    private fun setupUI(){

    }

    private fun showSpinner(){
        binding.btnPlaceOrderOne.setOnClickListener{

        }
    }
    private fun isValidateNameAndNumber():Boolean{
    val isVal:Boolean
    if (binding.editFullName.length() == 0){// it means the user still not give any data
        binding.editFullName.error = "Please Enter Your Name" //it means give the error to user

    }else{
        binding.editFullName.error = null

    }
        if (binding.editPhoneNumber.length() != 10){
            binding.editPhoneNumber.error = "Failed Phone Number"
            isVal = false
        }else{
        binding.editPhoneNumber.error= null
            isVal=true
    }
        return isVal
    }

    //validation = to make sure the user add the number and name in the app
}