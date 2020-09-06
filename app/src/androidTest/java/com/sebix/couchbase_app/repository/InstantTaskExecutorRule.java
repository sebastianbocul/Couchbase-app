package com.sebix.couchbase_app.repository;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

//public class InstantTaskExecutorRule implements AfterEachCallback, BeforeEachCallback {
//    @Override
//    public void afterEach(ExtensionContext context) throws Exception {
//        ArchTaskExecutor.getInstance().setDelegate(null);
//    }
//
//    @Override
//    public void beforeEach(ExtensionContext context) throws Exception {
//        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
//            @Override
//            public void executeOnDiskIO(@NonNull Runnable runnable) {
//                runnable.run();
//            }
//
//            @Override
//            public void postToMainThread(@NonNull Runnable runnable) {
//                runnable.run();
//            }
//
//            @Override
//            public boolean isMainThread() {
//                return true;
//            }
//        });
//    }
//}

public class InstantTaskExecutorRule extends TestWatcher {
    @Override
    protected void starting(Description description) {
        super.starting(description);
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override
            public void executeOnDiskIO(Runnable runnable) {
                runnable.run();
            }

            @Override
            public void postToMainThread(Runnable runnable) {
                runnable.run();
            }

            @Override
            public boolean isMainThread() {
                return true;
            }
        });
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        ArchTaskExecutor.getInstance().setDelegate(null);
    }
}


