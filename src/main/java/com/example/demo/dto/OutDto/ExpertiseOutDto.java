package com.example.demo.dto.OutDto;

import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertiseOutDto implements Serializable {

    private String title;
    private String id;
    private String slug;
}