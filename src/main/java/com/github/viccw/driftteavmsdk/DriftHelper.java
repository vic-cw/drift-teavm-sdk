package com.github.viccw.driftteavmsdk;

import java.util.Map;

public class DriftHelper {

    public static class UserDetailNames {
        public static final String PHONE_NUMBER = "phone";
    }

    public void identifyUser(String userId,
            Map<String, String> userDetails) {
        runWhenDriftIsLoadedAndReady(
                () -> DriftHandle.getInstance()
                    .identify(
                            userId,
                            JavaScriptOperabilityHelper
                            .stringMapToJavaScriptObject(
                                    userDetails)));
    }

    public void openChat() {
        runWhenDriftIsLoadedAndReady(
                () -> DriftHandle.getInstance()
                    .getApi().openChat());
    }

    private void runWhenDriftIsLoadedAndReady(Runnable task) {
        runWhenDriftIsLoaded(
                () -> runWhenDriftIsReady(
                        () -> {
                            if (task != null) {
                                task.run();
                            }
                        }));
    }

    private void runWhenDriftIsLoaded(Runnable task) {
        new Thread(() -> {
            while (!JavaScriptOperabilityHelper
                    .globalVariableIsDefined("drift")) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex) {
                    return;
                }
            }
            if (task != null) {
                task.run();
            }
        }).start();
    }

    private void runWhenDriftIsReady(Runnable task) {
        if (task == null) {
            return;
        }
        DriftHandle driftHandle = DriftHandle.getInstance();
        driftHandle.on(
                DriftHandle.EventNames.READY,
                task::run);
    }
}
