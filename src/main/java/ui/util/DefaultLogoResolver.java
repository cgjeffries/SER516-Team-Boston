package ui.util;

import javafx.scene.image.Image;
import taiga.model.query.project.Project;
import taiga.util.MurMurHash3;

import java.util.stream.IntStream;

/**
 * Utility class to resolve default logos from projects and users (used whenever they have not set a
 * profile picture themselves). Adapted from taiga-front. project logos:
 * <a href="https://github.com/kaleidos-ventures/taiga-front/blob/main/app/modules/services/project-logo.service.coffee">project-logo.service.coffee</a>
 * user logos:
 * <a href="https://github.com/kaleidos-ventures/taiga-front/blob/main/app/modules/services/avatar.service.coffee">avatar.service.coffee</a>
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
     * @param id   the project's id
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
     * @return the resource path for the default user logo image
     */
    public static String getDefaultUserLogo(String gravatarId) {
        long idx = MurMurHash3.hash(gravatarId, HASH_SEED) % DEFAULT_USER_LOGOS.length;
        return DEFAULT_USER_RESOURCE_PATH + DEFAULT_USER_LOGOS[(int) idx];
    }

    /**
     * Get project logo as an image
     *
     * @param project The project to get the logo from
     * @return An Image containing the project logo
     */
    public static Image getProjectLogoImage(Project project) {
        Image local = new Image(getDefaultProjectLogo(project.getSlug(), project.getId()));
        try {
            Image remoteImage = new Image(project.getLogoSmallUrl());
            if (!remoteImage.isError()) {
                return remoteImage;
            }
            return local;
        } catch (NullPointerException e) {
            return local;
        }
    }

    /**
     * Get project logo as an image with a given width and height
     *
     * @param project The project to get the logo from
     * @param width   The width to set the image to
     * @param height  The height to set the image to
     * @return An Image containing the project logo
     */
    public static Image getProjectLogoImage(Project project, int width, int height) {
        Image local = new Image(getDefaultProjectLogo(project.getSlug(), project.getId()), width, height, false, false);
        try {
            Image remoteImage = new Image(project.getLogoSmallUrl(), width, height, false, false);
            if (!remoteImage.isError()) {
                return remoteImage;
            }
            return local;
        } catch (NullPointerException e) {
            return local;
        }
    }
}