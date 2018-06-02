package com.example.imsihyun.a7thseminarproject


import android.graphics.RectF
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*

class SwipeController() : ItemTouchHelper.Callback() {

    private var buttonShowedStatae = ButtonsState.GONE

    // 사각형 
    private var buttonInstace : RectF? = null

    private var currentItemViewHolder : RecyclerView.Recycler.ViewHolder? = null

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        // 왼쪽이나 오른쪽으로 동작했을 때 액션할 수 있게 해준다
        return ItemTouchHelper.Callback.makeMovementFlags(0, LEFT or RIGHT)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        return
    }
}