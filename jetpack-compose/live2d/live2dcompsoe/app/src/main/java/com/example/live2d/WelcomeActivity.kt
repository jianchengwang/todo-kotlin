package com.example.live2d

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

class WelcomeActivity : Activity() {
    private var _glSurfaceView: GLSurfaceView? = null
    private var _glRenderer: GLRenderer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JniBridgeJava.SetActivityInstance(this)
        JniBridgeJava.SetContext(this)
        _glSurfaceView = GLSurfaceView(this)
        _glSurfaceView!!.setEGLContextClientVersion(2)
        _glRenderer = GLRenderer()
        _glSurfaceView!!.setRenderer(_glRenderer)
        _glSurfaceView!!.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        setContentView(_glSurfaceView)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) View.SYSTEM_UI_FLAG_LOW_PROFILE else View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onStart() {
        super.onStart()
        JniBridgeJava.nativeOnStart()
    }

    override fun onResume() {
        super.onResume()
        _glSurfaceView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        _glSurfaceView!!.onPause()
        JniBridgeJava.nativeOnPause()
    }

    override fun onStop() {
        super.onStop()
        JniBridgeJava.nativeOnStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        JniBridgeJava.nativeOnDestroy()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointX = event.x
        val pointY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> JniBridgeJava.nativeOnTouchesBegan(pointX, pointY)
            MotionEvent.ACTION_UP -> JniBridgeJava.nativeOnTouchesEnded(pointX, pointY)
            MotionEvent.ACTION_MOVE -> JniBridgeJava.nativeOnTouchesMoved(pointX, pointY)
        }
        return super.onTouchEvent(event)
    }
}
