package com.smile.retrofitapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smile.retrofitapp.Constants
import com.smile.retrofitapp.databinding.ActivityMainBinding
import com.smile.retrofitapp.models.LanguageList
import com.smile.retrofitapp.retrofit.RestApiAsync
import com.smile.retrofitapp.adapters.LanguageListAdapter
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            lifecycleOwner = this@MainActivity
        }

        myRecyclerView = binding.myRecyclerVew

        MyRestApiAsync().getAllLanguages()
    }

    private inner class MyRestApiAsync: RestApiAsync<LanguageList>() {
        init {
            Log.d(TAG, "MyRestApiAsync.created")
        }

        override fun getUrl(): String {
            return Constants.CHAO_URL
        }

        override fun onResponse(call: Call<LanguageList>, response: Response<LanguageList>) {
            Log.d(TAG, "MyRestApiAsync.onResponse")
            var languageList: LanguageList? = null
            if (response.isSuccessful) {
                languageList = response.body()
            }
            languageList?.let {
                Log.d(TAG, "MyRestApiAsync.onResponse.languages.size() = ${it.languages.size}")
                myRecyclerView.apply {
                    setHasFixedSize(true)
                    adapter = LanguageListAdapter(this@MainActivity, it.languages)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                }
            } ?: run {
                Log.d(TAG, "MyRestApiAsync.onResponse.languageList null")
            }
        }

        override fun onFailure(call: Call<LanguageList>, t: Throwable) {
            Log.d(TAG, "MyRestApiAsync.onFailure.t = $t")
        }
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}