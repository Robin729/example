package com.kk.example.effective_java;

import javax.management.relation.RoleUnresolved;

public class Cleaner {

    private Cleaner() {}

    public static Cleaner create() {
        return new Cleaner();
    }

    public Cleanable register(Object object, Runnable runnable) {
        return Cleanable.create(object, runnable);
    }

    static class Cleanable {
        private final Object object;
        private final Runnable runnable;

        private Cleanable(Object object, Runnable runnable) {
            this.object = object;
            this.runnable = runnable;
        }

        public static Cleanable create(Object object, Runnable runnable) {
            return new Cleanable(object, runnable);
        }

        public void clean() {
            runnable.run();
        }
    }

}
