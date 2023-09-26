package com.example.vahan_app
// UniversityListAdapter.kt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UniversityListAdapter(context: Context, universities: List<University>) :
    ArrayAdapter<University>(context,0, universities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_university, parent, false)

        val university = getItem(position)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val countryTextView = view.findViewById<TextView>(R.id.countryTextView)
        val websiteTextView = view.findViewById<TextView>(R.id.websiteTextView)

        nameTextView.text = university?.name
        countryTextView.text = university?.country
        websiteTextView.text = university?.web_pages?.get(0)

        websiteTextView.setOnClickListener {
            val websiteUrl = university?.web_pages?.get(0)
            if (websiteUrl != null) {
                // Open an in-app WebView
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", websiteUrl)
                context.startActivity(intent)
            }
        }

        return view
    }
}
