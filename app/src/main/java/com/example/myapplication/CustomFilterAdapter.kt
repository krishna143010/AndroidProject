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
    NameAndId: List<NameAndId>
) :
    ArrayAdapter<NameAndId>(mContext, mLayoutResourceId, NameAndId) {
    private val city: MutableList<NameAndId> = ArrayList(NameAndId)
    private var allNameAndId: List<NameAndId> = ArrayList(NameAndId)

    override fun getCount(): Int {
        return city.size
    }
    override fun getItem(position: Int): NameAndId {
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
            val city: NameAndId = getItem(position)
            //println("NameAndId List city.definition:"+city.definition)

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
                    return (resultValue as NameAndId).definition
                }
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val filterResults = FilterResults()
                    if (constraint != null) {
                        val citySuggestion: MutableList<NameAndId> = ArrayList()
                        for (city in allNameAndId) {
                            //checks the contains
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
                            if (result is NameAndId) {
                                city.add(result)
                            }
                        }
                        notifyDataSetChanged()
                    } else if (constraint == null) {
                        city.addAll(allNameAndId)
                        notifyDataSetInvalidated()
                    }
                }
            }
        }
    }
