package com.da.learn.utils.retry;

import com.da.learn.utils.common.DspSupplier;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class RetryUtil {

    /**
     * 重试方法，不支持显式异常抛出
     *
     * @param maxRetryCount
     * @param supplier
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> T retry1(int maxRetryCount, Supplier<T> supplier, BiFunction<T, Exception, Boolean> consumer) {
        T result = null;
        Exception exception = null;

        int retryCount = 0;
        boolean callMethod = true;
        while (callMethod && retryCount <= maxRetryCount) {
            try {
                // 获取调用服务的结果
                result = supplier.get();
            } catch (Exception e) {
                // 如果重试次数不小于最大重试次数，就抛出异常，我们把对异常的处理交给业务方
                if (retryCount >= maxRetryCount) {
                    throw e;
                }
                exception = e;
            }
            // 对结果进行判断
            callMethod = consumer.apply(result, exception);
            if (callMethod) {
                retryCount++;
            }
        }
        return result;
    }

    /**
     * 重试方法，支持显式异常抛出
     *
     * @param maxRetryCount 最大重试次数
     * @param supplier      重试方法
     * @param consumer      是否重试条件
     * @param <T>           返回结果类型
     * @return 重试方法返回结果
     * @throws Exception
     */
    public static <T> T retry2(int maxRetryCount, DspSupplier<T> supplier, BiFunction<T, Exception, Boolean> consumer) throws Exception {
        T result = null;
        Exception exception = null;

        int retryCount = 0;
        boolean callMethod = true;
        while (callMethod && retryCount <= maxRetryCount) {
            try {
                // 获取调用服务的结果
                result = supplier.get();
            } catch (Exception e) {
                // 如果重试次数不小于最大重试次数，就抛出异常，我们把对异常的处理交给业务方
                if (retryCount >= maxRetryCount) {
                    throw e;
                }
                exception = e;
            }
            // 对结果进行判断
            callMethod = consumer.apply(result, exception);
            if (callMethod) {
                retryCount++;
            }
        }
        return result;
    }

}
