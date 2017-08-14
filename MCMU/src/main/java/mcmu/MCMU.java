package mcmu;

import com.google.gson.GsonBuilder;
import mcmu.JsonTypeAdapters.*;
import mcmu.containers.typed_structures.TypedData;
import mcmu.downloader.ModLoader;
import mcmu.containers.*;
import mcmu.downloader.loaders.*;

import java.io.*;
import java.util.*;

import static mcmu.Statics.*;
public class MCMU {
    public static ConfigFile cnf;
    private ArrayList<Loader> Loaders = new ArrayList<>();
    public boolean rerun = true;
    public MCMU() {
        initializeGson();
        rerun = run();
        if(rerun) {
            run();
        }
        LoadMods();
    }
    public boolean run() {
        LoadConfig();
        return LoadIndexes();
    }
    public static void main(String[] args) {
        new MCMU();
    }
    private void initializeGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Sided.class, new EnumTypeAdapter());
        builder.registerTypeAdapter(CompatOverride.class, new EnumTypeAdapter());
        builder.registerTypeAdapter(TypedData.class, new TypedDataAdapter());
        Json = builder.create();
    }
    private void LoadConfig() {
        try {
            BufferedReader cfile = new BufferedReader(new FileReader("mod-repo.json"));
            cnf = Json.fromJson(cfile, ConfigFile.class);
            Side = cnf.Side.getValue();
            System.out.println("Side: " + Side);
        } catch (FileNotFoundException FNF) {
            System.out.println("unable to read mod-repo.json, not loading remote files");
        }
    }
    private boolean LoadIndexes() {
        if(cnf.URL != null) {
            DLURL = cnf.URL.getValue();
            Loaders.add(new IdxLoader(DLURL));
            if(Restart) {
                Loaders.clear();
                return true;
            }
        }
        if(cnf.localIDXs != null) {
            LoadLocalIndexes(cnf.localIDXs.getValue());
        }
        if(cnf.URLs != null) {
            LoadRemoteIndexes(cnf.URLs.getValue());
        }
        return false;
    }
    private void LoadLocalIndexes(ArrayList<String> indexes) {
        for(String index : indexes) {
            Loaders.add(new LocalIdxLoader(index));
        }
    }
    private void LoadRemoteIndexes(ArrayList<String> indexes) {
        for(String index : indexes) {
            Loaders.add(new IdxLoader(index));
        }
    }
    private void LoadMods() {
        System.out.println("Loading mods from locations in mod-repo.json");
        for (Loader files : Loaders) {
            new ModLoader(files);
        }
    }
}