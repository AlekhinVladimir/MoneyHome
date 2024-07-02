package com.example.moneyhome.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moneyhome.R
import com.example.moneyhome.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val viewModel: MenuViewModel by viewModels()
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToExpenseScreen.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_addFragment)
        }
        binding.btnToHistoryScreen.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_historyFragment)
        }
        binding.btnToAnalyticsScreen.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_analyticsFragment)
        }
        binding.btnExit.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}