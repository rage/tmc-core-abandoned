package fi.helsinki.cs.tmc.client.core;

import fi.helsinki.cs.tmc.client.core.clientspecific.UIInvoker;
import fi.helsinki.cs.tmc.client.core.domain.Settings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Core {

    private static UIInvoker uiInvoker;
    private static Settings settings;

    private Core() { }

    public static void initialize(final UIInvoker uiInvoker, final Settings settings) {

        Core.uiInvoker = uiInvoker;
        Core.settings = settings;
    }

    public static UIInvoker getUIInvoker() {

        return Core.uiInvoker;
    }

    public static Settings getSettings() {

        return Core.settings;
    }

    public static ExecutorService getExecutorService() {

        return Executors.newSingleThreadExecutor();
    }
}
