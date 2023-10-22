package com.example.giphyapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.DialogManager
import com.example.giphyapp.MainViewModel
import com.example.giphyapp.R
import com.example.giphyapp.data.GifAdapter
import com.example.giphyapp.GiphyConstant.API_KEY
import com.example.giphyapp.data.GiphyResponse
import com.example.giphyapp.databinding.FragmentMainBinding

class MainFragment : Fragment(), GifAdapter.Listener {
    private val viewModel: MainViewModel by activityViewModels()

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
        val adapter = GifAdapter(emptyList(), this@MainFragment)
        binding.rcView.adapter = adapter

        binding.imReload.setOnClickListener {
            checkApiKey()
            viewModel.updateTrendingGifs()
        }

        binding.imSearch.setOnClickListener {
            DialogManager.searchByQuery(requireContext(), object: DialogManager.Listener {
                override fun onClick(query: String?) {
                    Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_queryFragment)
                    query?.let { viewModel.updateQueryGifs(it) }
                }
            })
        }

        viewModel.liveDataList.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        checkApiKey()
        viewModel.updateTrendingGifs()
    }

    override fun onClick(item: GiphyResponse.GiphyModel) {
        viewModel.setLiveDataCurrent(item)

        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_secondFragment)
        }
    }

    private fun checkApiKey() {
        if (API_KEY.isBlank()) {
            Toast.makeText(requireContext(), "Please add api_key", Toast.LENGTH_LONG).show()
        }
    }
}