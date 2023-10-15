package com.example.giphyapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.giphyapp.MainViewModel
import com.example.giphyapp.databinding.FragmentFullBinding

class FullFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentFullBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFullBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveDataCurrent.observe(viewLifecycleOwner) { gifModel ->
            val gifImageView = binding.imageView
            Log.d("MyLog", "url is: ${gifModel.url}")
            Glide.with(requireContext())
                .asGif()
                .load(gifModel.url)
                .into(gifImageView)
        }
    }
}