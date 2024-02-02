package settings.appmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Project {
    @SerializedName("slug")
    @Expose
    private final String slug;

    @SerializedName("id")
    @Expose
    private final Integer id;

    public Project(String slug, Integer id) {
        this.slug = slug;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(slug, project.slug) &&
                Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, id);
    }
}
