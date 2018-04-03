package didikee.github.helper.promot;

/**
 * Created by didikee on 2018/3/4.
 */

public interface IReviewManager {
    /**
     * 判断是否需要展示评价对话框
     * @return true 显示
     *         false 不显示
     */
    boolean shouldShowReviewAction();

    /**
     * 展示评价对话框
     */
    void showReview();

    /**
     * 记录此时的评价动作，记下时间
     */
    void markReviewAction();

    /**
     * 用户选择最近不要显示
     */
    void markAskLater();

    /**
     * 用户成功的完成一次操作记做一次
     */
    void markSuccessAction();
}
