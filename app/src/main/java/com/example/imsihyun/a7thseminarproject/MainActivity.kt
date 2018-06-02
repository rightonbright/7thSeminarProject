package com.example.imsihyun.a7thseminarproject

import android.content.Intent
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var kakaoItems : ArrayList<KakaoData>
    lateinit var KakaoAdapter : KakaoAdapter

    // 2018.06.02 추가
    lateinit var swipeController: SwipeController
    lateinit var itemTouchListener: ItemTouchHelper

    var isDisplayButtons : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kakaoItems = ArrayList()
        kakaoItems.add(KakaoData(R.drawable.img1, "09의 바나나 안드로이드",
                "낰낰", "오후 4:07"))
        kakaoItems.add(KakaoData(R.drawable.img2, "이돌이의 차근차근기획",
                ":D ><", "오후 6:05"))
        kakaoItems.add(KakaoData(R.drawable.img3, "고창 애기덜 ㅎㅎ",
                "(이모티콘)", "오후 3:57"))
        kakaoItems.add(KakaoData(R.drawable.img4, "슴앗으로 깔맞춤",
                "ㅋㅋㅋㅋㅋㅋㅋㅋ", "오후 6:07"))
        kakaoItems.add(KakaoData(R.drawable.img5, "우리 가족",
                "알겠습니다~!", "오후 1:07"))
        kakaoItems.add(KakaoData(R.drawable.img6, "재후니오빠",
                "웅 담아", "오후 2:17"))
        kakaoItems.add(KakaoData(R.drawable.img9, "예쁜햄",
                "오늘 모해요....?", "오후 3:04"))
        kakaoItems.add(KakaoData(R.drawable.img8, "안드 2조",
                "넹~~~~!", "오후 4:07"))
        kakaoItems.add(KakaoData(R.drawable.img10, "임소담이",
                "ㅠㅠㅠ", "오후 4:08"))
        kakaoItems.add(KakaoData(R.drawable.img11, "주히유",
                "고마우미~~", "오후 4:14"))

        // 어댑터에 붙히기 작업
        KakaoAdapter = KakaoAdapter(kakaoItems)
        KakaoAdapter.setOnItemClickListener(this)   // 채팅방 클릭

        main_rv.layoutManager = LinearLayoutManager(this)
        main_rv.adapter = KakaoAdapter

        // 2018.06.02 추가
        swipeController = SwipeController()
        itemTouchListener = ItemTouchHelper(swipeController)
        itemTouchListener.attachToRecyclerView(main_rv)

        main_rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
                swipeController.onDraw(c!!)
            }
        })

        main_float_add.setOnClickListener {
            clickFloat()
        }

    }

    override fun onClick(v: View?) {
        val idx : Int = main_rv.getChildAdapterPosition(v)
        val name : String = kakaoItems[idx].name
        val profile : Int = kakaoItems[idx].profile


        val intent : Intent = Intent(applicationContext, ChatActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("profile", profile)
        startActivity(intent)

    }

    fun clickFloat() {

        if(!isDisplayButtons) {
            isDisplayButtons = true

            val animation = AnimationUtils.loadAnimation(this,
                    R.anim.float_button1_show)
            main_float_add.setBackgroundResource(R.drawable.img1)
            main_float_add.startAnimation(animation)

            val layoutParam1 = main_float_c1.layoutParams as RelativeLayout.LayoutParams
            layoutParam1.bottomMargin += (main_float_c1.height * 1.2).toInt()
            val showC1 = AnimationUtils.loadAnimation(this,
                    R.anim.float_button1_show)
            main_float_c1.layoutParams = layoutParam1
            main_float_c1.startAnimation(showC1)
            main_float_c1.isClickable = true

            val layoutParam2 = main_float_c2.layoutParams as RelativeLayout.LayoutParams
            layoutParam2.bottomMargin += (main_float_c2.height * 2.4).toInt()
            val showC2 = AnimationUtils.loadAnimation(this,
                    R.anim.float_button1_show)
            main_float_c2.layoutParams = layoutParam2
            main_float_c2.startAnimation(showC2)
            main_float_c2.isClickable = true

        } else {
            isDisplayButtons = false

            val animation = AnimationUtils.loadAnimation(this,
                    R.anim.float_button1_hide)
            main_float_add.setBackgroundResource(R.drawable.img1)
            main_float_add.startAnimation(animation)

            val layoutParam1 = main_float_c1.layoutParams as RelativeLayout.LayoutParams
            layoutParam1.bottomMargin -= (main_float_c1.height * 1.2).toInt()
            val showC1 = AnimationUtils.loadAnimation(this,
                    R.anim.float_button1_hide)
            main_float_c1.layoutParams = layoutParam1
            main_float_c1.startAnimation(showC1)
            main_float_c1.isClickable = true

            val layoutParam2 = main_float_c2.layoutParams as RelativeLayout.LayoutParams
            layoutParam2.bottomMargin -= (main_float_c2.height * 2.4).toInt()
            val showC2 = AnimationUtils.loadAnimation(this,
                    R.anim.float_button1_hide)
            main_float_c2.layoutParams = layoutParam2
            main_float_c2.startAnimation(showC2)
            main_float_c2.isClickable = true
        }

    }
}