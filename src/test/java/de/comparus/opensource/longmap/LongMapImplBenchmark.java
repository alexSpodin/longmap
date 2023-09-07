package de.comparus.opensource.longmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class LongMapImplBenchmark {

    private LongMap<String> longMap;
    private Map<Long, String> hashMap;

    @Setup
    public void setup() {
        longMap = new LongMapImpl<>(String.class);
        hashMap = new HashMap<>();
        for (long i = 0; i < 1000; i++) {
            longMap.put(i, "Value" + i);
            hashMap.put(i, "Value" + i);
        }
    }

    @Benchmark
    public void putLongMapBenchmark() {
        longMap.put(1001, "Value1001");
    }

    @Benchmark
    public void putHashMapBenchmark() {
        hashMap.put(1001L, "Value1001");
    }

    @Benchmark
    public void getLongMapBenchmark() {
        longMap.get(500);
    }

    @Benchmark
    public void getHashMapBenchmark() {
        hashMap.get(500L);
    }

    @Benchmark
    public void containsKeyLongMapBenchmark() {
        longMap.containsKey(500);
    }

    @Benchmark
    public void containsKeyHashMapBenchmark() {
        hashMap.containsKey(500L);
    }

    @Benchmark
    public void containsValueLongMapBenchmark() {
        longMap.containsValue("Value500");
    }

    @Benchmark
    public void containsValueHashMapBenchmark() {
        hashMap.containsValue("Value500");
    }

    @Benchmark
    public void removeLongMapBenchmark() {
        longMap.remove(500);
    }

    @Benchmark
    public void removeHashMapBenchmark() {
        hashMap.remove(500L);
    }

    @Benchmark
    public void clearLongMapBenchmark() {
        longMap.clear();
    }

    @Benchmark
    public void clearHashMapBenchmark() {
        hashMap.clear();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(LongMapImplBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
