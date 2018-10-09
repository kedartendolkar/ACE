package com.kedar.ace.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kedar.ace.R
import com.kedar.ace.model.DataModel
import com.kedar.ace.utils.NO_ERROR
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
        recycler.addItemDecoration(SpacesItemDecorator(resources.getDimension(R.dimen.item_margin).toInt()))

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
        itemViewModel.getItemList().observe(this, Observer<DataModel> { dataModel ->
            //Stop swipe refresh loader
            swipe_item_list.isRefreshing = false
            //Hide progressbar
            progress_item_list.visibility = View.GONE

            dataModel?.let {
                //Set items to adapter
                adapter.setItems(it.mRowsEntity)

                //Set toolbar title
                toolbar_item_list.visibility = View.VISIBLE
                toolbar_item_list.title = it.mTitle.orEmpty()
            }
        })

        //Hide RecyclerView and show Error message when no items present
        itemViewModel.getError().observe(this, Observer<Int> { error ->
            error?.let {
                if (it != NO_ERROR) {
                    txt_no_data_item_list.visibility = View.VISIBLE
                    txt_no_data_item_list.text = com.kedar.ace.data.ErrorHandler().getErrorMessage(this, it)
                    recycler.visibility = View.GONE
                    toolbar_item_list.visibility = View.GONE
                } else {
                    txt_no_data_item_list.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                }
            }
        })
    }
}