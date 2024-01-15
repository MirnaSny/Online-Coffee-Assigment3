package com.exercise1.onlinecoffeeassigment3.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.exercise1.onlinecoffeeassigment3.R
import com.exercise1.onlinecoffeeassigment3.databinding.FragmentPaymentBinding
import com.exercise1.onlinecoffeeassigment3.domain.OrderInfo
import com.exercise1.onlinecoffeeassigment3.domain.PaymentInfo

class PaymentFragment : Fragment() {

    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var pickTime: String
    private lateinit var binding: FragmentPaymentBinding
    private var paymentList : MutableList<String> = mutableListOf()
    private var hour = 1
    private var minutes = 0
    private var amPm = "AM"

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

    private fun setupListener() { // to collect all the data to the summaryFragment
        Log.e("paymentFragment","setupListener() called")
        binding.continueBtn3.setOnClickListener {
            Log.e("PaymentFragment", "continueBtn3 clicked")
            try {
                paymentListBuilding()
                val orderInfo = arguments?.getParcelable<OrderInfo>(getString(R.string.orderinfo))
                val paymentInfo = PaymentInfo(paymentList)
                val bundle = Bundle().apply {
                    putParcelable(getString(R.string.orderinfo), orderInfo)
                    putParcelable(getString(R.string.paymentinfo), paymentInfo)
                }
            if (isValidateCard()) {
                Log.e("PaymentFragment", "Navigating to SummaryFragment")
                findNavController().navigate(R.id.summaryFragment,bundle)
            }
            } catch (e: Exception) {
//                Log.e(TAG,e.toString())
                Toast.makeText(
                    requireContext(),
                    "Navigation failed. Please try again .",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun paymentListBuilding() {
        name = binding.editFullName.text.toString()
        phoneNumber = binding.editPhoneNumber.text.toString()
        pickTime = "$hour:$minutes $amPm"
        paymentList.apply {
            add(name)
            add(phoneNumber)
            add(pickTime)
        }
    }

    private fun isValidateCard(): Boolean {
        var isVal = true
        if (binding.etCardNumber.length() != 16) {
            binding.etCardNumber.error = "please enter a valid card number"
            isVal = false
        } else {
            binding.etCardNumber.error = null
        }
        val monthText=binding.MM.text.toString()
        if (monthText.isEmpty() || monthText.toInt() !in 1..12){
            binding.MM.error ="Please enter a valid month (1-12)"
            isVal=false
        }else {
            binding.MM.error = null
        }

        val yearText=binding.YY.text.toString()
        if (yearText.isEmpty() || yearText.toInt()< 2024){
            binding.YY.error ="Please enter a valid year (>= 2024)"
            isVal=false
        }else {
            binding.YY.error = null
        }
        val cvvNumber=binding.cardCvv.text.toString()
        if (cvvNumber.isEmpty() || cvvNumber.length != 3){
            binding.cardCvv.error ="Please enter a valid CVV "
            isVal=false
        }else
        {
            binding.cardCvv.error = null
        }
        return isVal
    }

    private fun setupUI() {
        setPickerTimeValues()
        showSpinner()
        setSpinnerAdapter()
        showCardNumberAndExpiry()
    }

    private fun setPickerTimeValues() {
        binding.hour.minValue = MIN_HOUR_VALUE
        binding.hour.maxValue = MAX_HOUR_VALUE

        binding.minutes.minValue = MIN_MIN_VALUE
        binding.minutes.maxValue = MAX_MIN_VALUE

        binding.amPm.minValue = MIN_AM_PM
        binding.amPm.maxValue = MAX_AM_PM

        //displayedValues -> put the Values inside number picker AM/PM
        binding.amPm.displayedValues = AM_PM_ARRAY

        //setOnValueChangedListener ->  if there's not any change on value so it will not change
        binding.hour.setOnValueChangedListener { numberPicker, _, _ ->
            hour = numberPicker.value
        }
        binding.minutes.setOnValueChangedListener { numberPicker, _, _ ->
            minutes = numberPicker.value

        }
        binding.amPm.setOnValueChangedListener { numberPicker, _, _ ->
            amPm = AM_PM_ARRAY[numberPicker.value]
        }
    }

    companion object {
        const val MIN_HOUR_VALUE = 1
        const val MAX_HOUR_VALUE = 12
        const val MIN_MIN_VALUE = 0
        const val MAX_MIN_VALUE = 59
        const val MIN_AM_PM = 0
        val AM_PM_ARRAY = arrayOf("AM", "PM")
        val MAX_AM_PM =
            AM_PM_ARRAY.size - 1 // AM/PM -> " 2 items " which means  2-1 = 1 -> so the maximum is "1"

        const val TAG ="Payment Fragment"
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
        binding.spinnerCardType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // selectedItem -> it will give me the position that the user selected
                    val selectedItemPosition = parent?.getItemAtPosition(position)
                    cardVisibility(selectedItemPosition.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    cardVisibility(null)
                }
            }
    }

    //cardVisibility -> I control the visibility of the card number and expiry
    private fun cardVisibility(selectedItem: String?) {
        binding.cardNumberExpiry.visibility = if (selectedItem == "_") View.GONE else View.VISIBLE
        binding.btnPlaceOrder2.visibility = if (selectedItem == "_") View.VISIBLE else View.GONE
        binding.continueBtn3.visibility = if (selectedItem == "_") View.GONE else View.VISIBLE

    }


}