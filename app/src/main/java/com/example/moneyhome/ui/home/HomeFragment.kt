package com.example.moneyhome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private val _binding by lazy(LazyThreadSafetyMode.NONE) { FragmentHomeBinding.inflate(layoutInflater) }
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel
        return _binding.root
    }
}
