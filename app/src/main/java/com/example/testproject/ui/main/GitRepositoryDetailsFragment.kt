package com.example.testproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testproject.MainActivity
import com.example.testproject.R
import com.example.testproject.common.BaseFragment
import com.example.testproject.databinding.GitRepositoryDetailsFragmentBinding

class GitRepositoryDetailsFragment: BaseFragment() {

    private lateinit var binding: GitRepositoryDetailsFragmentBinding
    private val args by navArgs<GitRepositoryDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.git_repository_details_fragment,
            container, false
        )

        NavigationUI.setupActionBarWithNavController(
            activity as MainActivity,
            findNavController()
        )

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureComponents()
    }

    private fun configureComponents() {
        (requireActivity() as MainActivity).title = args.repository.name
        Glide.with(requireContext())
            .asBitmap()
            .load(args.repository.owner.userAvatar)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.userImage)

        binding.userName.text = args.repository.owner.username
        binding.fork.text = getString(R.string.fork, args.repository.forks)
        binding.star.text = getString(R.string.star, args.repository.stars)
    }
}