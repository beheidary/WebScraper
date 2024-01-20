package com.example.demo.dto.OutDto;

import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertiseOutDto implements Serializable {

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    private String slug;
}