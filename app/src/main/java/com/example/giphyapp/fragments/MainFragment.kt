package com.example.giphyapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapp.MainViewModel
import com.example.giphyapp.R
import com.example.giphyapp.data.GifAdapter
import com.example.giphyapp.data.GiphyModel
import com.example.giphyapp.databinding.FragmentMainBinding

class MainFragment : Fragment(), GifAdapter.Listener {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: GifAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rcView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = GifAdapter(emptyList(), this@MainFragment)
        recyclerView.adapter = adapter

        viewModel.liveDataList.observe(viewLifecycleOwner) { gifList ->
            adapter.updateData(gifList)
        }
    }

    override fun onClick(item: GiphyModel) {
        viewModel.liveDataCurrent.value = item
        Log.d("MyLog", "Clicked on item with URL: ${viewModel.liveDataCurrent.value}")
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_secondFragment)
        };
    }
}