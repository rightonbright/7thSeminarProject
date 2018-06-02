package com.example.imsihyun.a7thseminarproject

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class KakaoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    // 아이템에 들어갈 뷰를 명시한다

    var kakaoProfile : ImageView = itemView!!.findViewById(R.id.item_profile_image) as ImageView

    var kakaoName : TextView = itemView!!.findViewById(R.id.item_name_tv) as TextView

    var kakaoDate : TextView = itemView!!.findViewById(R.id.item_date_tv) as TextView

    var kakaoPreview : TextView = itemView!!.findViewById(R.id.item_preview_tv) as TextView

    //var kakaoEditText : EditText = itemView!!.findViewById(R.id.main_et) as EditText

}