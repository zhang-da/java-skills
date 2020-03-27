package com.da.learn.learnboot.maintainpush;

public abstract class AbstractMaintainRule<T extends Comparable<? super T>> implements MaintainRule {

    protected JudgeResultType judgeMethod(T current, T last, Range<T> range) {
        T start = range.getStart();
        T end = range.getEnd();
        if (current.compareTo(start) < 0) {
            return JudgeResultType.PASS;
        }
        if (current.compareTo(end) >= 0) {
            return JudgeResultType.PASS;
        }
        if (last == null) {
            return JudgeResultType.KEEP;
        }
        if (last.compareTo(start) < 0) {
            return JudgeResultType.KEEP;
        }
        if (last.compareTo(end) < 0) {
            return willRepeat() ? JudgeResultType.KEEP : JudgeResultType.PASS_BY_REPEAT;
        }
        return JudgeResultType.PASS;
    }

    /**
     * 如果在某时间段已经产生过保养信息，是否重复产生
     * @return
     */
    @Override
    public abstract boolean willRepeat();

    protected enum JudgeResultType {
        /**
         * 1 小于范围
         * 5 大于范围
         */
        PASS,
        /**
         * 2 在范围 && 上次小于范围
         * 3 在范围 && 上次在范围 && 需要重复记录
         * 6 在范围 && 没有上次
         */
        KEEP,

        /**
         * 4 在范围 && 上次在范围 && 不需要重复记录
         */
        PASS_BY_REPEAT;
    }

}
