package com.paging.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.paging.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_search_repositories.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() , HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    private val adapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_repositories)

        // get the view model
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)

        initAdapter()
        viewModel.getRepos()
    }
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private fun initAdapter() {
        list.adapter = adapter
        viewModel.viewState.observe(this, Observer{
            if(it != null) {
                Log.d("Activity", "list: ${it?.repos?.size}")
                showEmptyList(it?.repos?.size == 0)
                adapter.submitList(it.repos)
            }
        })
        viewModel.errorState.observe(this, Observer{
//            Toast.makeText(this, "nodata", Toast.LENGTH_SHORT).show()
            adapter.setIsLoading(false)
            list.adapter = adapter
        })
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }
}
