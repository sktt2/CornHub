package com.cornhub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class that represents a pool of threads for growing radii of background circle shapes.
 */
public class BackgroundCircleUpdaterPool {

    final ExecutorService pool;

    public BackgroundCircleUpdaterPool() {
        final int cpuCores = Math.max(Runtime.getRuntime().availableProcessors() - 1, 1);
        pool = Executors.newFixedThreadPool(cpuCores);
    }

    public void submit(final Runnable task) {
        pool.submit(task);
    }
}
