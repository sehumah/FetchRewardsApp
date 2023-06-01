package com.exercise.fetchrewardsapp

import org.json.JSONArray

private val TAG = Data::class.java.simpleName

data class Data(
    var id: Int,
    var listId: Int,
    var name: String
    ) {

    companion object {

        // takes a JSONArray, extracts and populate a list with the individual array objects
        fun fromJSONArray(dataJSONArray: JSONArray): List<Data> {
            val dataList = mutableListOf<Data>()  // to store data from remote source

            // loop through data json array
            for (i in 0 until dataJSONArray.length()) {
                val dataJSON = dataJSONArray.getJSONObject(i)  // grab an individual data object
                dataList.add(Data(dataJSON.getInt("id"), dataJSON.getInt("listId"), dataJSON.getString("name")))  // add the data object to dataList
            }
            return dataList
        }

    }

}
