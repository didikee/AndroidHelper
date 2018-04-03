package didikee.github.helper.promot;

import android.content.Context;

import didikee.me.android.utils.SPUtil;

/**
 * Created by didikee on 2018/3/4.
 */

public abstract class ReviewManager implements IReviewManager {
    private static final String KEY_REVIEW_TIME = "key_review_time";
    private static final String KEY_REVIEW_LATER = "key_review_later";
    private static final String KEY_REVIEW_SUCCESS = "key_review_success";
    private static final int success_limit = 3;
    private static final long DAY_TIME = 60 * 60 * 24 * 1000;
    private static final long REVIEW_LATER_OFFSET = DAY_TIME * 7;

    private Context context;

    public ReviewManager(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldShowReviewAction() {
        long currentTimeMillis = System.currentTimeMillis();
        long reviewTime = (long) SPUtil.get(context, KEY_REVIEW_TIME, 0L);
        long reviewLaterTime = (long) SPUtil.get(context, KEY_REVIEW_LATER, 0L);
        int successCount = (int) SPUtil.get(context, KEY_REVIEW_SUCCESS, 0);
        if (reviewTime > 0) {
            //已经评价过，不再弹出
            return false;
        } else {
            if (successCount % success_limit == 1) {
                if (reviewLaterTime > 0) {
                    if (currentTimeMillis - reviewLaterTime > REVIEW_LATER_OFFSET) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public void markReviewAction() {
        SPUtil.put(context, KEY_REVIEW_TIME, System.currentTimeMillis());
    }

    @Override
    public void markAskLater() {
        SPUtil.put(context, KEY_REVIEW_LATER, System.currentTimeMillis());
    }

    @Override
    public void markSuccessAction() {
        int successTimes = (int) SPUtil.get(context, KEY_REVIEW_SUCCESS, 0);
        successTimes++;
        SPUtil.put(context, KEY_REVIEW_SUCCESS, successTimes);
    }
}
