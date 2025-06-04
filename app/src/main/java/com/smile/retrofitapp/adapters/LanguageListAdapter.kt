package com.smile.retrofitapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smile.retrofitapp.databinding.ItemLayoutBinding
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.view.xml_view.viewModels.LangXmlViewModel

class LanguageListAdapter(private val cycleOwner: LifecycleOwner,
    private val languages: ArrayList<Language>)
    : RecyclerView.Adapter<LanguageListAdapter.MyViewHolder>() {

        init {
            Log.d(TAG, "LanguageListAdapter created")
        }

        inner class MyViewHolder(private val binding: ItemLayoutBinding)
            : ViewHolder(binding.root) {
            init {
                Log.d(TAG, "MyViewHolder created")
            }

            fun bindData(viewModel: LangXmlViewModel) {
                binding.apply {
                    lifecycleOwner = cycleOwner
                    this.viewModel = viewModel
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        val languageViewModel = LangXmlViewModel()
        languageViewModel.setLanguage(languages[position])
        holder.bindData(languageViewModel)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    companion object {
        private const val TAG = "LanguageListAdapter"
    }
}