// Copyright (c) 2023 Samsung Electronics Co. LTD. Released under the MIT License.

package com.samsung.helpcrossing.executor

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


class CrossingEvent(private var context: Context) : TextToSpeech.OnInitListener {
    fun setTTS() {
        textToSpeech = TextToSpeech(context, this)
    }

    internal fun countCars2Sec(nCar: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (nCar != 0) {
                infoDetection("Some car has been detected")
            } else {
                infoDetection("Cross the street")
            }
        }
    }

    private fun infoDetection(info: String) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
        textToSpeech?.speak(info, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeechResult = textToSpeech!!.setLanguage(Locale.US)
            if (textToSpeechResult == TextToSpeech.LANG_MISSING_DATA ||
                textToSpeechResult == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Log.e("Test", "English language is not supported or missing data!")
            }
        } else {
            Log.e("Test", "TTS Initialization Failed!")
        }
    }

    companion object {
        var textToSpeech: TextToSpeech? = null
        var textToSpeechResult: Int = 0
    }
}