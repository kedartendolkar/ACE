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

        //Init adapter
        val adapter = ItemListAdapter(this)

        //Init recycler view
        val recycler = rec_item_list
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))

        //Set Toolbar
        setSupportActionBar(toolbar_item_list)

        //Show progressbar
        progress_item_list.visibility = View.VISIBLE

        //Setup swipe refresh
        swipe_item_list.setOnRefreshListener {
            itemViewModel.refreshData()
        }

        //Setup ViewModel
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel.getItemList().observe(this, Observer<DataModel> {
            //Stop swipe refresh loader
            swipe_item_list.isRefreshing = false
            //Hide progressbar
            progress_item_list.visibility = View.GONE

            //Hide RecyclerView and show No Data message when no items present
            if (it?.mRowsEntity == null || it.mRowsEntity.isEmpty()) {
                txt_no_data_item_list.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            } else {
                txt_no_data_item_list.visibility = View.GONE
                recycler.visibility = View.VISIBLE

            }
            //Set items to adapter
            adapter.setItems(it?.mRowsEntity)

            //Set toolbar title
            toolbar_item_list.title = it?.mTitle
        })
    }
}