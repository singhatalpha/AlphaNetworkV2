package Utils;

import androidx.fragment.app.FragmentActivity;

public class BaseBackPressedPopListener implements OnBackPressedPopListener {
    private final FragmentActivity activity;

    public BaseBackPressedPopListener(FragmentActivity activity) {
        this.activity = activity;
    }


    @Override
    public void doPop() {
        activity.getSupportFragmentManager().popBackStack();
    }
}
