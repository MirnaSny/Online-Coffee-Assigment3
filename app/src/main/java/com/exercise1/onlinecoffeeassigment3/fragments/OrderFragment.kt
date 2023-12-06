package com.exercise1.onlinecoffeeassigment3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exercise1.onlinecoffeeassigment3.R
import com.exercise1.onlinecoffeeassigment3.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private lateinit var coffeeType:String
    private lateinit var coffeeSize:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initListener()
        showCheckBoxOptions()
        showCoffeeSize()
    }

    private fun initListener() {
        binding.continueBtn3.setOnClickListener {
            findNavController().navigate(R.id.paymentFragment)
        }
    }
    private fun order(){

    }
    private fun showCoffeeSize(){
        val list = listOf(binding.americano,binding.cappuccino,binding.latte ,binding.macchiato)
        list.forEach{
            it.setOnClickListener {
                binding.coffeeSize.visibility=View.VISIBLE
                binding.continueBtn1.visibility=View.GONE
            }
        }
    }

    private fun showCheckBoxOptions(){
        val list = listOf(binding.small,binding.medium,binding.large)
        list.forEach{
            it.setOnClickListener {
                binding.checkbox.visibility = View.VISIBLE
                binding.continueBtn2.visibility=View.GONE
                binding.continueBtn3.visibility=View.VISIBLE
            }
        }
    }
//    private fun coffeeType(){
//        coffeeType = selectCoffee
//    }
//
//    private fun coffeeSize(){
//        coffeeSize = selectCoffee
//    }
//
//    private fun selectCoffee(list:List<RadioButton>):List<String>{
//        return list.filter{it.isChecked}
//    }
}