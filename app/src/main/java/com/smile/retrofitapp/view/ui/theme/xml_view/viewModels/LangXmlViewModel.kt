package com.smile.retrofitapp.view.ui.theme.xml_view.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smile.retrofitapp.models.Language

class LangXmlViewModel: ViewModel() {
    private val _language: MutableLiveData<Language> = MutableLiveData()
    val language: LiveData<Language>
        get() = _language

    fun setLanguage(language: Language) {
        _language.value = language
    }

    fun setId(id: Int) {
        _language.value?.id = id
    }

    fun setLangNo(langNo: String) {
        _language.value?.langNo = langNo
    }

    fun setLangNa(langNa: String) {
        _language.value?.langNa = langNa
    }

    fun setLangEn(langEn: String?) {
        langEn?.let {
            _language.value?.langEn = it
        }
    }
}