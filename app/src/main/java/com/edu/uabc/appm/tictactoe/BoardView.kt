package com.edu.uabc.appm.tictactoe

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * TODO: document your custom view class.
 */
public class BoardView : View {
    private val LINE_THICK = 6
    private val ELT_MARGIN = 20
    private val ELT_STROKE_WIDTH = 15
    var eltW: Int = 0
    var eltH: Int = 0
    var altura:Int = 0
    var ancho: Int = 0
    private var gridPaint: Paint? = null
    var oPaint: Paint? = null
    var xPaint: Paint? = null
    private var gameEngine: Board? = null
    private var activity: MainActivity? = null
    constructor(context: Context) : super(context) {

        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        //init(attrs, 0)
        gridPaint = Paint()
        oPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        oPaint!!.color = Color.RED
        oPaint!!.style = Paint.Style.STROKE
        xPaint = Paint(oPaint)
        xPaint!!.color = Color.BLUE


    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

    }

    fun setMainActivity(a: MainActivity) {
        activity = a
    }

    fun setGameEngine(g: Board) {
        gameEngine = g
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        altura = View.MeasureSpec.getSize(heightMeasureSpec)
        ancho = View.MeasureSpec.getSize(widthMeasureSpec)
        eltW = (ancho - LINE_THICK) / 3
        eltH = (altura - LINE_THICK) / 3

        setMeasuredDimension(ancho, altura)
    }

    private fun drawBoard(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {
                drawElt(canvas, gameEngine!!.getElt(i, j), i, j)
            }
        }
    }

    private fun drawGrid(canvas: Canvas) {
        for (i in 0..1) {
            // vertical lines
            val left = eltW * (i + 1)
            val right = left + LINE_THICK
            val top = 0f
            val bottom = altura

            canvas.drawRect(left.toFloat(), top, right.toFloat(), bottom.toFloat(), gridPaint)

            // horizontal lines
            val left2 = 0f
            val right2 = ancho
            val top2 = eltH * (i + 1)
            val bottom2 = top2 + LINE_THICK

            canvas.drawRect(left2, top2.toFloat(), right2.toFloat(), bottom2.toFloat(), gridPaint)
        }
    }

    private fun drawElt(canvas: Canvas, c: Char, x: Int, y: Int) {
        if (c == 'O') {
            val cx = eltW * x + eltW / 2
            val cy = eltH * y + eltH / 2

            canvas.drawCircle(cx.toFloat(), cy.toFloat(), (Math.min(eltW, eltH) / 2 - ELT_MARGIN * 2).toFloat(), oPaint)

        } else if (c == 'X') {
            val startX = eltW * x + ELT_MARGIN
            val startY = eltH * y + ELT_MARGIN
            val endX = startX + eltW - ELT_MARGIN * 2
            val endY = startY + eltH - ELT_MARGIN

            canvas.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), xPaint)

            val startX2 = eltW * (x + 1) - ELT_MARGIN
            val startY2 = eltH * y + ELT_MARGIN
            val endX2 = startX2 - eltW + ELT_MARGIN * 2
            val endY2 = startY2 + eltH - ELT_MARGIN

            canvas.drawLine(startX2.toFloat(), startY2.toFloat(), endX2.toFloat(), endY2.toFloat(), xPaint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGrid(canvas)
        drawBoard(canvas)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!gameEngine!!.isEnded() && event!!.getAction() == MotionEvent.ACTION_DOWN) {
            val x = (event!!.x / eltW).toInt()
            val y = (event.y / eltH).toInt()
            var win = gameEngine!!.play(x, y)
            invalidate()

            if (win != ' ') {
                activity!!.gameEnded(win)
            } else {
                // computer plays ...
                win = gameEngine!!.computer()
                invalidate()

                if (win != ' ') {
                    activity!!.gameEnded(win)
                }
            }
        }
        return super.onTouchEvent(event)

    }
}