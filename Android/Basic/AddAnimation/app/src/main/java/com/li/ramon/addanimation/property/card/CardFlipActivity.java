package com.li.ramon.addanimation.property.card;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.li.ramon.addanimation.R;

/*fragment  换场动画  card 翻转效果*/
public class CardFlipActivity extends AppCompatActivity {

    private boolean mShowingBack = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        flipCard();
    }


    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            mShowingBack = false;
            return;
        }
        // Flip to the back.
        mShowingBack = true;
        getFragmentManager()
                .beginTransaction()
                /*int enter, int exit, int popEnter, int popExit*/
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, new CardBackFragment())
                .addToBackStack(null)
                .commit();
    }
}
