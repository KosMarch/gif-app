package com.example.giphyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.giphyapp.databinding.FragmentFullBinding

class FullFragment : Fragment() {
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

        binding.imBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        Glide.with(requireContext())
            .asGif()
            .load(arguments?.getString("imageUrl"))
            .into(binding.ivFull)
    }
}