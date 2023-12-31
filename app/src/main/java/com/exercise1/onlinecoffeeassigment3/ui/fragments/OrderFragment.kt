package com.exercise1.onlinecoffeeassigment3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exercise1.onlinecoffeeassigment3.R
import com.exercise1.onlinecoffeeassigment3.databinding.FragmentOrderBinding
import com.exercise1.onlinecoffeeassigment3.domain.OrderInfo
import java.util.logging.ErrorManager


class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private lateinit var coffeeType: String
    private lateinit var coffeeSize: String
    private var orderList: MutableList<String> = mutableListOf()
    private var checkBoxList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(layoutInflater)
        return binding.root
    }

    private val coffeeTypeList by lazy {
        listOf(binding.americano, binding.macchiato, binding.latte, binding.cappuccino)
    }

    private val coffeeSizeList by lazy {
        listOf(binding.small, binding.medium, binding.large)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupListener()
    }

    private fun setupUI() {
        showCoffeeSize()
        showCheckBoxOptions()
    }

    private fun setupListener() {
        binding.continueBtn3.setOnClickListener {
            try {
                initListener()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "navigation_failed_please_try_again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initListener() {
        coffeeType()
        coffeeSize()
        checkBox()
        order()
        val orderInfo = OrderInfo(orderList)
        val bundle = Bundle().apply {
            putParcelable(getString(R.string.order), orderInfo)
        }
        findNavController().navigate(R.id.paymentFragment, bundle)

    }
    //putParcelable = add "object" -> data type = "Parcelable"

    private fun showCoffeeSize() {
        val list = listOf(binding.americano, binding.cappuccino, binding.latte, binding.macchiato)
        list.forEach {
            it.setOnClickListener {
                binding.coffeeSize.visibility = View.VISIBLE
                binding.continueBtn1.visibility = View.GONE
            }
        }
    }

    private fun showCheckBoxOptions() {
        val list = listOf(binding.small, binding.medium, binding.large)
        list.forEach {
            it.setOnClickListener {
                binding.checkbox.visibility = View.VISIBLE
                binding.continueBtn2.visibility = View.GONE
                binding.continueBtn3.visibility = View.VISIBLE
            }
        }
    }

    private fun checkBox() {
        val coffeeCheckBoxList = listOf(
            binding.firstOption,
            binding.cream,
            binding.sugar,
            binding.notFatMilk,
            binding.wholeMilk,
            binding.almondMilk,
            binding.halfMilk
        )
        checkBoxList.addAll(coffeeCheckBoxList.filter { it.isChecked }.map { it.text.toString() })
    }

    //    checkBoxList -> empty till now
//     checkBoxList = it is for add a more than one option from " coffeeCheckBoxList "
    private fun coffeeType() {
        coffeeType = selectedValue(coffeeSizeList).joinToString()
    }

    private fun coffeeSize() {
        coffeeSize = selectedValue(coffeeTypeList).joinToString()
    }


    private fun selectedValue(list: List<RadioButton>): List<String> {
        return list.filter { it.isChecked }.map { it.text.toString() }
    }
    //for example:
    //filter = binding.latte -> map = latte
    // filter for the "LIST"

    private fun order() {
        orderList.add(getString(R.string.a_with, coffeeSize , coffeeType))
        if (checkBoxList.size == 1) {
            orderList.add(checkBoxList[0])
        } else {
            for (i in 0 until checkBoxList.size - 1) {
                orderList.add(checkBoxList[i] + getString(R.string.comma))
            }
            orderList.add(getString(R.string.and) + checkBoxList.last())
        }
    }
    //until -> remove the last index
    // i -> index
    // orderList -> empty

}