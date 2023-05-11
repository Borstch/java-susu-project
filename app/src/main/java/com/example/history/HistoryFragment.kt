package com.example.history

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.HistoryAdapter
import com.example.R
import com.example.databinding.FragmentHistoryBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryFragmentViewModel by activityViewModels()
    private lateinit var adapter: HistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHistoryBinding.bind(requireView())

        adapter = HistoryAdapter(viewModel.historyItems.asReversed())

        val historyRV = binding.historyList
        historyRV.layoutManager = LinearLayoutManager(context)
        historyRV.adapter = adapter

        binding.toolbar.apply {
            inflateMenu(R.menu.menu_from_history)
            menu.apply {
                findItem(R.id.back).setOnMenuItemClickListener {
                    findNavController().navigate(R.id.action_historyFragment_to_calculatorFragment)
                    true
                }
                findItem(R.id.delete).setOnMenuItemClickListener {
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle(resources.getString(R.string.History_delete_alert))
                        setMessage(resources.getString(R.string.alert_check))

                        setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                            val file = File(requireContext().filesDir, "History.txt")
                            file.writeText("")
                            viewModel.historyItems.clear()
                            adapter.notifyDataSetChanged()
                        }

                        setNegativeButton(resources.getString(R.string.no)) { _, _ -> }
                        setCancelable(true)
                    }.create().show()
                    true
                }
            }
        }

        readHistory()
    }

    private fun readHistory() {

        val file = File(requireContext().filesDir, "History.txt")
        try {
            BufferedReader(FileReader(file)).use { br ->
                br.lines().forEach {
                    viewModel.historyItems.add(Pair(it.split("|").first(), it.split("|").last()))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        viewModel.historyItems.clear()
    }
}