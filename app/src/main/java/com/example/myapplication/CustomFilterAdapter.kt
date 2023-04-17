package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView

class CustomFilterAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    cities: List<Cities>
) :
    ArrayAdapter<Cities>(mContext, mLayoutResourceId, cities) {
    private val city: MutableList<Cities> = ArrayList(cities)
    private var allCities: List<Cities> = ArrayList(cities)

    override fun getCount(): Int {
        return city.size
    }
    override fun getItem(position: Int): Cities {
        return city[position]
    }
    override fun getItemId(position: Int): Long {
        return city[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val city: Cities = getItem(position)
            println("Cities List city.definition:"+city.definition)

            val cityAutoCompleteView = convertView!!.findViewById<View>(android.R.id.text1) as TextView
            cityAutoCompleteView.text = city.definition
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }
    override fun getFilter(): Filter {
            return object : Filter() {
                override fun convertResultToString(resultValue: Any) :String {
                    return (resultValue as Cities).definition
                }
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val filterResults = FilterResults()
                    if (constraint != null) {
                        val citySuggestion: MutableList<Cities> = ArrayList()
                        for (city in allCities) {
                            if (city.definition.toLowerCase().contains(constraint.toString().toLowerCase())
                            ) {
                                citySuggestion.add(city)
                            }
                        }
                        filterResults.values = citySuggestion
                        filterResults.count = citySuggestion.size
                    }
                    return filterResults
                }
                override fun publishResults(
                    constraint: CharSequence?,
                    results: FilterResults
                ) {
                    city.clear()
                    if (results.count > 0) {
                        for (result in results.values as List<*>) {
                            if (result is Cities) {
                                city.add(result)
                            }
                        }
                        notifyDataSetChanged()
                    } else if (constraint == null) {
                        city.addAll(allCities)
                        notifyDataSetInvalidated()
                    }
                }
            }
        }
    }
