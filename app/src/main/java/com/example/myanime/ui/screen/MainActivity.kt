package com.example.myanime.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myanime.core.ApiState
import com.example.myanime.data.api.RequestGet
import com.example.myanime.databinding.ActivityMainBinding
import com.example.myanime.domain.useCase.UseCase
import com.example.myanime.ui.adapter.Adapter
import com.example.myanime.ui.viewModel.MainViewModel
import com.example.myanime.ui.viewModel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = Adapter()
    private val repositoryImpl = RequestGet.RequestGetImpl()
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(UseCase(repositoryImpl))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        viewModel.dataAnime.observe(this@MainActivity) {
            if (it != null) {
                adapter.submitList(it)
            }
        }
        viewModel.apiState.observe(this) {
            when (it) {
                ApiState.ApiEmpty -> {
                    showProgressBar()
                    hideContent()
                    showSnackBar("empty")
                }
                ApiState.ApiLoading -> {
                    showProgressBar()
                    hideContent()
                    showSnackBar("Loading")
                }
                is ApiState.Success -> {
                    hideContent()
                    hideProgressBar()
                    showContent()
                    showSnackBar("success information")
                }
                is ApiState.Error -> {
                    hideContent()
                    hideProgressBar()
                    showSnackBar("problems")
                }
            }
        }
        binding.btnAscendingSort.setOnClickListener {
            viewModel.sortASC()
            adapter.notifyDataSetChanged()
        }
        binding.btnDescendingSort.setOnClickListener {
            viewModel.sortDes()
            adapter.notifyDataSetChanged()
        }
        binding.rvAnime.adapter = adapter
        binding.rvAnime.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        swipeRefresh()

        adapter.currentPosition = {
            if (it >= 1) {
                viewModel.delete(it - 1)
                adapter.notifyDataSetChanged()
            } else {
                Snackbar.make(
                    binding.root,
                    "в списке должен быть хотя бы один элемент",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this,
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG)
            .show()
    }

    private fun hideContent() {
        binding.contentContainer.visibility = View.GONE
    }

    private fun showContent() {
        binding.contentContainer.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun swipeRefresh() {
        binding.swipeRefreshContainer.setOnRefreshListener {
            viewModel.apply {
                viewModel.animeInfo(true)
                binding.swipeRefreshContainer.isRefreshing = false
            }
        }
    }
}