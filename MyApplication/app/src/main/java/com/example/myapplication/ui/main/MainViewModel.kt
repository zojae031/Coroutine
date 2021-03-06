package com.example.myapplication.ui.main

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class TestType(var intent: Intent? = null, val text: String) {
    class KasperTest(intent: Intent? = null, text: String) : TestType(intent, text)

    class DictationTest(intent: Intent? = null, text: String) : TestType(intent, text)

    class AlarmPicker(intent: Intent? = null, text: String) : TestType(intent, text)

    class MediaPipe(intent: Intent? = null, text: String) : TestType(intent, text)

    class Mlkit(intent: Intent? = null, text: String) : TestType(intent, text)

    class Books(intent: Intent? = null, text: String) : TestType(intent, text)

    class ShareUi(intent: Intent? = null, text: String) : TestType(intent, text)
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val listItem = MutableLiveData<List<TestType>>()

    fun getItemList(): LiveData<List<TestType>> = listItem

    fun setTestItems(testList: List<TestType>) {
        listItem.value = testList
    }

}
