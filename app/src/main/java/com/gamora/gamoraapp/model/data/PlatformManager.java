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
        initPlatforms();
    }

    private static void initPlatforms()
    {
        platformsStrings.append(1, "PC");
        platformsStrings.append(2, "Playstation 4");
        platformsStrings.append(3, "XBOX One");
        platformsStrings.append(4, "Nintendo Switch");
    }

    public static PlatformManager getInstance() {
        return manager;
    }

    public String getPlatform(int id){
        return platformsStrings.get(id);
    }

    public SparseArray<String> getPlatformsStrings() {
        return platformsStrings;
    }
}
