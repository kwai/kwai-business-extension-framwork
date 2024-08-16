package com.kuaishou.business.core.reduce;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午10:20
 * kbf core support some default reducer
 */
public class Reducers {

    /**
     * { "data1" : "a" } , { "data2" : "b" } -> { "data1" : "a" , "data2" : "b" }
     */
    public static <K, V> Reducer<Map<K, V>, Map<K, V>> flatMap(Predicate<Map<K, V>> predicate) {
        return new FlatMap<>(predicate);
    }

    /**
     * return true while all result match the predicate
     */
    public static <T> Reducer<T, Boolean> allMatch(Predicate<T> predicate) {
        return new AllMatch<>(predicate);
    }

    /**
     * [true, true, true] -> true
     * [true, false, true] -> false
     */
    public static <T> Reducer<T, Boolean> allMatch() {
        return allMatch(Boolean.TRUE::equals);
    }

    /**
     * return true while any result match the predicate
     */
    public static <T> Reducer<T, Boolean> anyMatch(Predicate<T> predicate) {
        return new AnyMatch<>(predicate);
    }

    /**
     * [true, true, true] -> true
     * [true, false, true] -> true
     * [false, false, false] -> false
     */
    public static <T> Reducer<T, Boolean> anyMatch() {
        return anyMatch(Boolean.TRUE::equals);
    }

    /**
     * [a, b, c] , [e, f, g] -> [a, b, c, e, f, g]
     */
    public static <T> Reducer<List<T>, List<T>> flatList(Predicate<List<T>> predicate) {
        return new FlatList<>(predicate);
    }

    /**
     * [a, b, c] , [c, g] -> [a, b, c, g]
     */
    public static <T> Reducer<List<T>, Set<T>> flatSet(Predicate<List<T>> predicate) {
        return new FlatSet<>(predicate);
    }

    /**
     * return the result which match the predicate
     */
    public static <T> Reducer<T, List<T>> collect(Predicate<T> predicate) {
        return new Collect<>(predicate);
    }

    /**
     * return the first result
     */
    public static <T> Reducer<T, T> first() {
        return new FirstOf<>();
    }

    /**
     * return the first result match the predicate
     */
    public static <T> Reducer<T, T> first(Predicate<T> predicate) {
        return new FirstOf<>(predicate);
    }

    /**
     * only provide a reduce type : first
     * only can used in executeVoid
     */
    public static <T> Reducer<T, Void> firstVoid() {
        return new FirstVoid<>();
    }

    /**
     * return all results
     */
    public static <T> Reducer<T, List<T>> all() {
        return new All<>();
    }

    /**
     * only provide a reduce type : all
     * only can used in executeVoid
     */
    public static <T> Reducer<T, Void> allVoid() {
        return new AllVoid<>();
    }
}
