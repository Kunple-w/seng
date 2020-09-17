package com.github.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangyongxu
 */
public class SimpleSengExecutor implements SengExecutor {
    private ExecutorService service = Executors.newFixedThreadPool(10);

    @Override
    public void execute(SengJob sengJob) {
        SengContext sengContext = SengContext.getSengContext();
        service.execute(new Worker(sengContext, sengJob));
    }

    private static class Worker implements Runnable {
        private final SengContext sengContext;

        private final SengJob sengJob;

        public Worker(SengContext sengContext, SengJob sengJob) {
            this.sengContext = sengContext;
            this.sengJob = sengJob;
        }


        @Override
        public void run() {
            sengJob.execute(sengContext);
        }
    }
}
