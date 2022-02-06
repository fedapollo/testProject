package com.example.testproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.R
import com.example.testproject.common.BaseFragment
import com.example.testproject.databinding.MainFragmentBinding
import com.example.testproject.ui.main.adapter.GitRepositoriesAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment,
            container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureObservables()
        configureRecyclerView()

        viewModel.initialize()
    }

    private fun configureObservables() {
        viewModel.model.stateOb.observe(viewLifecycleOwner, Observer {
            handleState(it)
        })
    }

    private fun configureRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = GitRepositoriesAdapter { item ->
                val action = MainFragmentDirections.actionMainFragmentDestToGitRepositoryDetailsFragmentDest(
                    item
                )
                findNavController().navigate(action)
            }
            (adapter as GitRepositoriesAdapter).addItems(viewModel.model.repositories)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(!canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.getRepositories()
                    }
                }
            })
        }
    }

    private fun handleState(state: MainModel.MainState?) {
        when (state) {
            is MainModel.MainState.Loading -> if (state.isLoading) showFullLoadingDialog() else hideFullLoadingDialog()
            is MainModel.MainState.NotConnected -> {
                Snackbar.make(requireView(), getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show()
            }
            is MainModel.MainState.Error -> {
                Snackbar.make(requireView(), getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT).show()
            }
            is MainModel.MainState.DataLoaded -> {
                (binding.recyclerView.adapter as GitRepositoriesAdapter).addItems(viewModel.model.repositories)
            }
        }
    }

}