package com.kedar.ace.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.kedar.ace.R
import com.kedar.ace.data.ErrorHandler
import com.kedar.ace.data.local.entity.DataModel
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
        itemViewModel.getItemList()?.observe(this, Observer<DataModel> { dataModel ->
            //Stop swipe refresh loader
            swipe_item_list.isRefreshing = false
            //Hide progressbar
            progress_item_list.visibility = View.GONE

            dataModel?.let { model ->
                //Hide RecyclerView and show Error message when no items present
                if (model.error != null) {
                    if (model.rowsEntity == null) {
                        txt_no_data_item_list.visibility = View.VISIBLE
                        txt_no_data_item_list.text = ErrorHandler().getErrorMessage(this, model.error?.errorCode)
                        recycler.visibility = View.GONE
                        toolbar_item_list.visibility = View.GONE
                    } else {
                        Toast.makeText(this, ErrorHandler().getErrorMessage(this, model.error?.errorCode), Toast.LENGTH_SHORT).show()
                    }
                    model.error = null
                } else {
                    txt_no_data_item_list.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                }

                //Set items to adapter
                adapter.setItems(model.rowsEntity)

                //Set toolbar title
                toolbar_item_list.visibility = View.VISIBLE
                toolbar_item_list.title = model.title
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        itemViewModel.destroy()
    }
}