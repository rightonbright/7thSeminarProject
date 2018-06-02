package com.example.imsihyun.a7thseminarproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast

class KakaoAdapter(private var kakaoItems : ArrayList<KakaoData>) : RecyclerView.Adapter<KakaoViewHolder>(){

    private lateinit var onItemClick : View.OnClickListener


    // MainActivity에서 변수 받아와서 onclick
    fun setOnItemClickListener(l : View.OnClickListener) {
        onItemClick = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KakaoViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.kakao_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return KakaoViewHolder(mainView)

    }

    override fun getItemCount(): Int = kakaoItems.size

    val context: Context? = null

    override fun onBindViewHolder(holder: KakaoViewHolder, position: Int) {


        holder.kakaoProfile.setImageResource(kakaoItems[position].profile)

        holder.kakaoName.text = kakaoItems[position].name
        holder.kakaoDate.text = kakaoItems[position].date
        holder.kakaoPreview.text = kakaoItems[position].preView
    }
}