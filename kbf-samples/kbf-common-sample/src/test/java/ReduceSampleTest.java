import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.core.reduce.Reducers;

/**
 * @author liuzhuo07
 * Created on 2024-09-05
 */
public class ReduceSampleTest {

	@Test
    public void all() {
		Reducer<Integer, List<Integer>> all = Reducers.all();
		List<Integer> result = all.reduce(Lists.newArrayList(1, 2, 3));
		Assert.assertEquals(result, Lists.newArrayList(1, 2, 3));
	}

	@Test
	public void allMatch() {
		Reducer<Boolean, Boolean> allMatch1 = Reducers.allMatch();
		Boolean result1 = allMatch1.reduce(Lists.newArrayList(true, false, true));
		Assert.assertEquals(result1, false);

		Reducer<Integer, Boolean> allMatch2 = Reducers.allMatch(i -> i % 2 == 0);
		Boolean result2 = allMatch2.reduce(Lists.newArrayList(1, 2, 3));
		Assert.assertEquals(result2, false);

		Boolean result3 = allMatch2.reduce(Lists.newArrayList(2, 4, 6));
		Assert.assertEquals(result3, true);
	}

	@Test
	public void anyMatch() {
		Reducer<Boolean, Boolean> anyMatch1 = Reducers.anyMatch();
		Boolean result1 = anyMatch1.reduce(Lists.newArrayList(true, false, false));
		Assert.assertEquals(result1, true);

		Reducer<Integer, Boolean> anyMatch2 = Reducers.anyMatch(i -> i % 2 == 0);
		Boolean result2 = anyMatch2.reduce(Lists.newArrayList(1, 2, 3));
		Assert.assertEquals(result2, true);

		Boolean result3 = anyMatch2.reduce(Lists.newArrayList(1, 3, 5));
		Assert.assertEquals(result3, false);
	}

	@Test
	public void collect() {
		Reducer<Integer, List<Integer>> collect = Reducers.collect(i -> i % 2 == 0);
		List<Integer> result = collect.reduce(Lists.newArrayList(1, 2, 3));

		Assert.assertEquals(result, Lists.newArrayList(2));
	}

	@Test
	public void firstOf() {
		Reducer<Integer, Integer> first = Reducers.first(i -> i % 2 == 0);
		Integer result = first.reduce(Lists.newArrayList(1, 2, 3));

		Integer expectResult = 2;
		Assert.assertEquals(result, expectResult);
	}

	@Test
	public void flatList() {
		Reducer<List<Integer>, List<Integer>> flatList = Reducers.flatList(Objects::nonNull);
		List<Integer> array1 = Lists.newArrayList(1, 2, 3);
		List<Integer> array2 = null;
		List<Integer> array3 = Lists.newArrayList(3, 4, 5);
		List<Integer> result = flatList.reduce(Lists.newArrayList(array1, array2, array3));

		Assert.assertEquals(result, Lists.newArrayList(1, 2, 3, 3, 4, 5));
	}

	@Test
	public void flatMap() {
		Reducer<Map<String, String>, Map<String, String>> flatMap = Reducers.flatMap(Objects::nonNull);

		Map<String, String> map1 = ImmutableMap.of("data1", "a");
		Map<String, String> map2 = null;
		Map<String, String> map3 = ImmutableMap.of("data2", "b");
		Map<String, String> result = flatMap.reduce(Lists.newArrayList(map1, map2, map3));

		Assert.assertEquals(result, ImmutableMap.of("data1", "a", "data2", "b"));
	}

	@Test
	public void flatSet() {
		Reducer<List<Integer>, Set<Integer>> flatSet = Reducers.flatSet(Objects::nonNull);
		List<Integer> array1 = Lists.newArrayList(1, 2, 3);
		List<Integer> array2 = null;
		List<Integer> array3 = Lists.newArrayList(3, 4, 5);
		Set<Integer> result = flatSet.reduce(Lists.newArrayList(array1, array2, array3));

		Assert.assertEquals(result, Sets.newHashSet(1, 2, 3, 4, 5));
	}
}
