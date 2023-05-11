package com.example.calculator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.R
import com.example.databinding.FragmentCalculatorBinding
import java.io.File
import java.text.DecimalFormat

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private lateinit var binding: FragmentCalculatorBinding
    private val viewModel: CalculatorFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalculatorBinding.bind(requireView())

        setFields()
        binding.expression.showSoftInputOnFocus = false

        with(binding) {
            zeroBtn.setOnClickListener { updateText("0") }
            oneBtn.setOnClickListener { updateText("1") }
            twoBtn.setOnClickListener { updateText("2") }
            threeBtn.setOnClickListener { updateText("3") }
            fourBtn.setOnClickListener { updateText("4") }
            fiveBtn.setOnClickListener { updateText("5") }
            sixBtn.setOnClickListener { updateText("6") }
            sevenBtn.setOnClickListener { updateText("7") }
            eightBtn.setOnClickListener { updateText("8") }
            nineBtn.setOnClickListener { updateText("9") }
            plusBtn.setOnClickListener { updateText("+") }
            minusBtn.setOnClickListener { updateText("-") }
            divideBtn.setOnClickListener { updateText(resources.getString(R.string.divide)) }
            multiplyBtn.setOnClickListener { updateText(resources.getString(R.string.multiply)) }
            expBtn.setOnClickListener { updateText("^") }
            separatorBtn.setOnClickListener { updateText(resources.getString(R.string.separator)) }
            openBracketBtn?.setOnClickListener { updateText("(") }
            closeBracketBtn?.setOnClickListener { updateText(")") }
            reciprocalBtn?.setOnClickListener { updateText("^(-1)") }
            sqrtBtn?.setOnClickListener { updateText("^(1/2)") }
            modBtn?.setOnClickListener { updateText("%") }
            eBtn?.setOnClickListener { updateText("e") }
            logBtn?.setOnClickListener { updateText("log(") }
            sinBtn?.setOnClickListener { updateText("sin(") }
            cosBtn?.setOnClickListener { updateText("cos(") }
            tanBtn?.setOnClickListener { updateText("tan(") }
            piBtn?.setOnClickListener { updateText("π") }

            copyBtn?.setOnClickListener { viewModel.copyResult() }

            pasteBtn?.setOnClickListener {
                updateText(viewModel.copiedResult.toString())
            }

            clearBtn.setOnClickListener {
                viewModel.clear()
                setFields()
            }

            backBtn.setOnClickListener {
                viewModel.back()
                try {
                    viewModel.evaluateMath()
                    setFields()
                } catch (e: Exception) {
                    binding.result.setText(R.string.error)
                    binding.expression.setText(viewModel.expression)
                }
            }

            plusMinusBtn.setOnClickListener {
                viewModel.expression = "-(" + viewModel.expression + ")"
                updateText("")
            }

            equalBtn.setOnClickListener {
                try {
                    viewModel.evaluateMath()
                    saveToHistory()
                    viewModel.expression = viewModel.result.toString()
                    setFields()
                } catch (e: Exception) {
                    binding.result.setText(R.string.error)
                }
            }
        }

        binding.toolbar.apply {
            inflateMenu(R.menu.menu)
            menu.apply {
                findItem(R.id.more).setOnMenuItemClickListener {
                    findNavController().navigate(R.id.action_calculatorFragment_to_historyFragment)
                    true
                }
            }
        }

    }

    private fun updateText(str: String) {
        viewModel.update(str)
        try {
            viewModel.evaluateMath()
            setFields()
        } catch (e: Exception) {
            binding.result.setText(R.string.error)
            binding.expression.setText(viewModel.expression)
        }
    }

    private fun setFields() {
        binding.expression.setText(viewModel.expression)
        val decimalFormat = DecimalFormat("#.########")
        binding.result.text = decimalFormat.format(viewModel.result)
    }

    private fun saveToHistory() {
        val file = File(requireContext().filesDir, "History.txt")
        file.appendText(viewModel.expression + "|=" + viewModel.result + "\n")
    }
}