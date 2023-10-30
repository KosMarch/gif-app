package com.example.giphyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.DialogManager
import com.example.giphyapp.MainViewModel
import com.example.giphyapp.R
import com.example.giphyapp.data.GifAdapter
import com.example.giphyapp.databinding.FragmentQueryBinding

class QueryFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentQueryBinding

    private val dialogManager = DialogManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcSearchView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = GifAdapter(emptyList()) { query ->
            val bundle = Bundle()
            bundle.putString("imageUrl", query)
            Navigation.findNavController(view)
                .navigate(R.id.action_queryFragment_to_secondFragment, bundle)
        }
        binding.rcSearchView.adapter = adapter

        binding.imSearchBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.listGifs.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        binding.imSearch.setOnClickListener {
            dialogManager.searchByQuery(requireContext()
            ) { query -> query?.let { viewModel.updateQueryGifs(it) } }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                dialogManager.errorMessage(requireContext(), it)
            }
        }
    }
}