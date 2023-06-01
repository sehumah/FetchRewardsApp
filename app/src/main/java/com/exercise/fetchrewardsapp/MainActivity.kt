package com.exercise.fetchrewardsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val dataList = mutableListOf<Data>()
    private lateinit var rvData: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bind the RecyclerView adapter and layoutManager to populate and display the data
        rvData = findViewById(R.id.rv_data)
        val dataAdapter = DataAdapter(this, dataList)
        rvData.adapter = dataAdapter
        rvData.layoutManager = LinearLayoutManager(this)

        // make network request call to retrieve data from the remote source
        val client = AsyncHttpClient()

        client.get("https://fetch-hiring.s3.amazonaws.com/hiring.json", object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                try {
                    // get the retrieved remote data and populate the data list with it
                    val dataJSONArray = json?.jsonArray
                    dataList.addAll(Data.fromJSONArray(dataJSONArray!!))

                    // apply requirements for the app
                    dataList.apply {
                        // display all the items grouped by "listId"
                        dataList.groupBy { it.listId }

                        // sort the results first by "listId" then by name when displaying
                        dataList.sortBy { it.listId }
                        dataList.sortBy { it.name }

                        // filter out any items where "name" is blank or null
                        dataList.removeIf { it.name == "" }
                        dataList.removeIf { it.name == "null" }
                    }

                    // notify the adapter that the underlying dataset has changed
                    dataAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Error adding data to the list. Exception: $e")
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "onFailure: data retrieval failed")
            }
        })

    }

}
