package com.da.learn.utils.common;

@FunctionalInterface
public interface DspSupplier<T> {
    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;
}
