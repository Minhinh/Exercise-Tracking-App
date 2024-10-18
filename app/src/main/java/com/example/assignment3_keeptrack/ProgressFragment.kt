package com.example.assignment3_keeptrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignment3_keeptrack.databinding.ProgressFragmentBinding

class ProgressFragment : Fragment() {
    private var _binding: ProgressFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProgressFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle progress display logic here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
