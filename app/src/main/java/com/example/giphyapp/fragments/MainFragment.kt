package com.example.giphyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.DialogManager
import com.example.giphyapp.MainViewModel
import com.example.giphyapp.R
import com.example.giphyapp.data.GifAdapter
import com.example.giphyapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private val dialogManager = DialogManager()

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = GifAdapter(emptyList()) { query ->
            val bundle = Bundle()
            bundle.putString("imageUrl", query)
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_secondFragment, bundle)
        }
        binding.rcView.adapter = adapter

        binding.imReload.setOnClickListener {
            Toast.makeText(requireContext(), "Updating GIF", Toast.LENGTH_SHORT).show()
            viewModel.updateTrendingGifs()
        }

        binding.imSearch.setOnClickListener {
            dialogManager.searchByQuery(requireContext()) { query ->
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainFragment_to_queryFragment)
                query?.let {
                    viewModel.updateQueryGifs(it)
                }
            }
        }

        viewModel.listGifs.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                dialogManager.errorMessage(requireContext(), it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateTrendingGifs()
    }
}