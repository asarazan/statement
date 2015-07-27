package net.sarazan.statement

import android.content.Context
import android.os.Build
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

/**
 * Created by Tre Murillo on 7/20/15
 * Copyright(c) 2015 Level, Inc.
 */
public class ScrollingFABBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {
    private var isAnimatingOut = false

    override public fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, nestedScrollAxes: Int): Boolean {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
    }

    override public fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        if (dyConsumed > 0 && !this.isAnimatingOut && child.getVisibility() === View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child)
        } else if (dyConsumed < 0 && child.getVisibility() !== View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateIn(child)
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private fun animateOut(button: FloatingActionButton) {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setInterpolator(INTERPOLATOR).withLayer().setListener(
                    object : ViewPropertyAnimatorListener {
                        override public fun onAnimationStart(view: View) {
                            this@ScrollingFABBehavior.isAnimatingOut = true
                        }

                        override public fun onAnimationCancel(view: View) {
                            this@ScrollingFABBehavior.isAnimatingOut = false
                        }

                        override public fun onAnimationEnd(view: View) {
                            this@ScrollingFABBehavior.isAnimatingOut = false
                            view.setVisibility(View.GONE)
                        }
                    }).start()
        } else {
            val anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.fab_out)
            anim.setInterpolator(INTERPOLATOR)
            anim.setDuration(200L)
            anim.setAnimationListener(object : Animation.AnimationListener {
                override public fun onAnimationStart(animation: Animation) {
                    this@ScrollingFABBehavior.isAnimatingOut = true
                }

                override public fun onAnimationEnd(animation: Animation) {
                    this@ScrollingFABBehavior.isAnimatingOut = false
                    button.setVisibility(View.GONE)
                }

                override public fun onAnimationRepeat(animation: Animation) {
                }
            })
            button.startAnimation(anim)
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private fun animateIn(button: FloatingActionButton) {
        button.setVisibility(View.VISIBLE)
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setInterpolator(INTERPOLATOR).withLayer().setListener(null).start()
        } else {
            val anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.fab_in)
            anim.setDuration(200L)
            anim.setInterpolator(INTERPOLATOR)
            button.startAnimation(anim)
        }
    }

    companion object {
        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }
}