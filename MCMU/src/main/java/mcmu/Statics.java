package mcmu;

import com.google.gson.Gson;
import mcmu.api.Sided;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bradl on 1/12/2017.
 */
public class Statics {
    public static Gson Json;
    public static Sided Side;
    public static ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static boolean PluginsRunning = true;
    public static IMCMU self;
}
