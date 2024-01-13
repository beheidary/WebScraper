package com.example.demo.dto.InDto;

import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertiseInDto implements Serializable {

    private String title;
    private String id;
    private String slug;
}