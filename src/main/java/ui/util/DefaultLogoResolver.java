package ui.util;

import taiga.util.MurMurHash3;

import java.util.stream.IntStream;

/**
 * Utility class to resolve default logos from projects and users (used whenever they have not set a
 * profile picture themselves). Adapted from taiga-front. project logos:
 * https://github.com/kaleidos-ventures/taiga-front/blob/main/app/modules/services/project-logo.service.coffee
 * user logos:
 * https://github.com/kaleidos-ventures/taiga-front/blob/main/app/modules/services/avatar.service.coffee
 */
public class DefaultLogoResolver {

    private static final String DEFAULT_PROJECT_RESOURCE_PATH = "/images/project-defaults/";
    private static final String DEFAULT_USER_RESOURCE_PATH = "/images/user-defaults/";

    private static final String[] DEFAULT_PROJECT_LOGOS =
            IntStream.range(0, 25)
                    .mapToObj(i -> "project-default-" + i + ".png")
                    .toArray(String[]::new);

    private static final String[] DEFAULT_USER_LOGOS =
            IntStream.range(0, 25)
                    .mapToObj(i -> "user-default-" + i + ".png")
                    .toArray(String[]::new);

    private static final int HASH_SEED = 42;

    /**
     * Resolves a default project logo given a project slug and id
     *
     * @param slug the project's slug
     * @param id the project's id
     * @return the resource path for the default project logo image
     */
    public static String getDefaultProjectLogo(String slug, int id) {
        long idx = MurMurHash3.hash(slug + "-" + id, HASH_SEED) % DEFAULT_PROJECT_LOGOS.length;
        return DEFAULT_PROJECT_RESOURCE_PATH + DEFAULT_PROJECT_LOGOS[(int) idx];
    }

    /**
     * Resolves a default project logo given a project slug and id
     *
     * @param gravatarId the gravatar id of the user
     * @return the resource path for the default project logo image
     */
    public static String getDefaultUserLogo(String gravatarId) {
        long idx = MurMurHash3.hash(gravatarId, HASH_SEED) % DEFAULT_USER_LOGOS.length;
        return DEFAULT_USER_RESOURCE_PATH + DEFAULT_USER_LOGOS[(int) idx];
    }
}