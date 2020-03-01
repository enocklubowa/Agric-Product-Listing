package com.example.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

class ViewAnimation {
    public static void fadeIn(final View v) {
        ViewAnimation.fadeIn(v, null);
    }

    public static void fadeIn(final View v, final AnimListener animListener) {
        v.setVisibility(View.GONE);
        v.setAlpha(0.0f);
        // Prepare the View for the animation
        v.animate()
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        if (animListener != null) animListener.onFinish();
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1.0f);
    }
    public interface AnimListener {
        void onFinish();
    }

    public static void fadeOutIn(View view) {
        view.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.f, 0.5f, 1.f);
        ObjectAnimator.ofFloat(view, "alpha", 0.f).start();
        animatorAlpha.setDuration(500);
        animatorSet.play(animatorAlpha);
        animatorSet.start();
    }


}
