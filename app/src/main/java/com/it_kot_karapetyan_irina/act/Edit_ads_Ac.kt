package com.it_kot_karapetyan_irina.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import com.it_kot_karapetyan_irina.R
import com.it_kot_karapetyan_irina.databinding.ActivityEditAdsBinding

class Edit_ads_Ac : AppCompatActivity() {

    // создаем переменную для доступа к активити
   // private lateinit var  rootElement: ActivityEditAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //инициализируем переменную
       // rootElement= ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_edit_ads)
    }
}