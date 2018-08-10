package com.gamora.gamoraapp.model.data;

import android.util.SparseArray;

import java.util.HashSet;

public class PlatformManager {
    private static final PlatformManager manager = new PlatformManager();
    private static SparseArray<HashSet<User>> userPlatforms;
    private static SparseArray<String> platformsStrings;

    private PlatformManager() {
        platformsStrings = new SparseArray<>();
        userPlatforms = new SparseArray<>();
        for (int i = 1; i <= platformsStrings.size(); i++) {
            userPlatforms.append(i, new HashSet<User>());
        }
    }

    private static void initPlatforms()
    {
        SparseArray<String> platforms = new SparseArray<>();
        platforms.append(1, "PC");
        platforms.append(2, "Playstation 4");
        platforms.append(3, "XBOX One");
        platforms.append(4, "Nintendo Switch");
    }

    public static PlatformManager getInstance() {
        return manager;
    }

    public String getPlatform(int id){
        return platformsStrings.get(id);
    }
}
