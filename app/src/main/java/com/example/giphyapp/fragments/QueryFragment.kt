package com.example.giphyapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.DialogManager
import com.example.giphyapp.MainViewModel
import com.example.giphyapp.R
import com.example.giphyapp.data.GifAdapter
import com.example.giphyapp.data.GiphyResponse
import com.example.giphyapp.databinding.FragmentQueryBinding

class QueryFragment : Fragment(), GifAdapter.Listener {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentQueryBinding

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
        val adapter = GifAdapter(emptyList(), this@QueryFragment)
        binding.rcSearchView.adapter = adapter

        binding.imSearchBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.liveDataList.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        binding.imSearch.setOnClickListener {
            DialogManager.searchByQuery(requireContext(), object: DialogManager.Listener {
                override fun onClick(query: String?) {
                    query?.let { viewModel.updateQueryGifs(it) }
                }
            })
        }
    }

    override fun onClick(item: GiphyResponse.GiphyModel) {
        viewModel.setLiveDataCurrent(item)

        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_queryFragment_to_secondFragment)
        }
    }
}