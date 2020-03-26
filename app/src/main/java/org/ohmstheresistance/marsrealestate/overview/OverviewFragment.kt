package org.ohmstheresistance.marsrealestate.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import org.ohmstheresistance.marsrealestate.databinding.MarsPropertiesItemviewBindingImpl

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = MarsPropertiesItemviewBindingImpl.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel


        return binding.root
    }

}
