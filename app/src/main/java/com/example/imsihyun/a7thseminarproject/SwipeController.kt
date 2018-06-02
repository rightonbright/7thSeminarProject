package com.example.imsihyun.a7thseminarproject


import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.view.MotionEvent

@SuppressLint("ClickableViewAccessibility")
class SwipeController() : ItemTouchHelper.Callback() {

    private var swipeBack = false

    private var buttonShowedState = ButtonsState.GONE

    private var buttonInstance: RectF? = null

    private var currentItemViewHolder: RecyclerView.ViewHolder? = null

    private var buttonsActions: SwipeController? = null

    constructor(buttonsActions: SwipeController) : this(){
        this.buttonsActions = buttonsActions
    }

    private val buttonWidth = 300f

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {

        // swipeBack = true
        // else swipeBack = false
        if(swipeBack) {
            swipeBack = (buttonShowedState != ButtonsState.GONE)
            if(buttonShowedState != ButtonsState.GONE)
                return 0
        }

        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

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

    private fun setItemsClickable(recyclerView: RecyclerView,
                                  isClickable : Boolean) {
        // 인자로 들어갈 리사이클러뷰를 다 아이템에 넘겨준다
        for (i in 0 until recyclerView.childCount)
            recyclerView.getChildAt(i).isClickable = isClickable
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        var dX = dX
        if(actionState == ACTION_STATE_SWIPE) {
            if(buttonShowedState == ButtonsState.GONE) {
                if(buttonShowedState == ButtonsState.LEFT_VISIBLE) // 왼쪽 보여질 때
                    dX = Math.max(dX, buttonWidth)
                if(buttonShowedState == ButtonsState.RIGHT_VISIBLE) // 오른쪽 보여질 때
                    dX = Math.min(dX, -buttonWidth)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            } else {
                //
                setTouchListener(c!!, recyclerView!!, viewHolder!!, dX, dY, actionState, isCurrentlyActive)
            }
        }

        if(buttonShowedState == ButtonsState.GONE) {
            // 안 보이는 상태는 안 보이는 상태로 둔다
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    private fun setTouchUpListener(c : Canvas,
                                   recyclerView : RecyclerView,
                                   viewHolder : RecyclerView.ViewHolder,
                                   dX : Float,
                                   dY : Float,
                                   actionState : Int,
                                   isCurrentlyActive : Boolean) {

                    recyclerView.setOnTouchListener { v, event ->
                        if(event.action == MotionEvent.ACTION_UP) {
                            super@SwipeController.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            recyclerView.setOnTouchListener { v, event ->
                                return@setOnTouchListener false
                            }

                            // 나머지는 클릭할 수 있는 상태로 활성화시킨다
                            setItemsClickable(recyclerView, true)
                            swipeBack = false

                            // if() -> 버튼 이벤트로 무언가를 처리 할 예정

                            buttonShowedState = ButtonsState.GONE
                            currentItemViewHolder = null

                        }

                        return@setOnTouchListener false
                    }
    }

    private fun setTouchDownListener(c : Canvas,
                                     recyclerView : RecyclerView,
                                     viewHolder : RecyclerView.ViewHolder,
                                     dX : Float,
                                     dY : Float,
                                     actionState : Int,
                                     isCurrentlyActive : Boolean) {

                // Down이라는 인자 클릭 시 업 액션
                recyclerView.setOnTouchListener { v, event ->
                    if(event.action == MotionEvent.ACTION_DOWN) {
                        setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    }

                    return@setOnTouchListener false

                }
    }


    private fun setTouchListener(c : Canvas,
                                 recyclerView : RecyclerView,
                                 viewHolder : RecyclerView.ViewHolder,
                                 dX : Float,
                                 dY : Float,
                                 actionState : Int,
                                 isCurrentlyActive : Boolean) {

                recyclerView.setOnTouchListener { v, event ->
                    swipeBack = (event.action == MotionEvent.ACTION_CANCEL ||   // 터치를 놓을 때
                            event.action == MotionEvent.ACTION_UP)

                    if(swipeBack) {
                        if(dX < -buttonWidth)
                            buttonShowedState = ButtonsState.RIGHT_VISIBLE
                        else if(dX > buttonWidth)
                            buttonShowedState = ButtonsState.LEFT_VISIBLE

                        // 안 보이는 상태가 아니었다면
                        if(buttonShowedState != ButtonsState.GONE) {
                            setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            setItemsClickable(recyclerView, false)
                        }
                    }

                    return@setOnTouchListener false
                }
    }


    private fun drawButtons(c : Canvas,
                            viewHolder: RecyclerView.ViewHolder) {
        val buttonWidthWithoutPadding = buttonWidth - 20
        val corners = 16f

        val itemView = viewHolder.itemView
        val p = Paint()

        val leftButton = RectF(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left + buttonWidthWithoutPadding, itemView.bottom.toFloat())
        //pixel
        p.color = Color.BLUE
        c.drawRoundRect(leftButton, corners, corners, p)
        drawText("EDIT", c, leftButton, p)

        val rightButton = RectF(itemView.right - buttonWidthWithoutPadding, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
        p.color = Color.RED
        c.drawRoundRect(rightButton, corners, corners, p)
        drawText("DELETE", c, rightButton, p)

        buttonInstance = null
        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton
        } else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton
        }

    }

    private fun drawText(text : String,
                         c : Canvas,
                         button : RectF,
                         p : Paint) {

        val textSize = 60f
        p.color = Color.WHITE
        p.isAntiAlias = true
        p.textSize = textSize

        val textWidth = p.measureText(text)
        c.drawText(text, button.centerX() - textWidth/2,
                button.centerY() + textSize/2, p)
    }

    fun onDraw(c : Canvas) {
        if(currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder!!)
        }
    }


}