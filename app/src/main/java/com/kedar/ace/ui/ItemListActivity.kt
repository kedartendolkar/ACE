package com.kedar.ace.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kedar.ace.R
import com.kedar.ace.model.DataModel
import kotlinx.android.synthetic.main.activity_item_list.*


class ItemListActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val adapter = ItemListAdapter(this)
        val recycler = rec_item_list
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))

        setSupportActionBar(toolbar_item_list)

        progress_item_list.visibility = View.VISIBLE

        swipe_item_list.setOnRefreshListener {
            itemViewModel.refreshData()
        }

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel.getItemList().observe(this, Observer<DataModel> {
            swipe_item_list.isRefreshing = false
            progress_item_list.visibility = View.GONE

            if (it?.mRowsEntity == null || it.mRowsEntity.isEmpty()) {
                txt_no_data_item_list.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            } else {
                txt_no_data_item_list.visibility = View.GONE
                recycler.visibility = View.VISIBLE

            }
            adapter.setItems(it?.mRowsEntity)
            toolbar_item_list.title = it?.mTitle
        })
    }
}