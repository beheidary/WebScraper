package com.example.demo.controller;

        import com.example.demo.dto.OutDto.ExpertiseOutDto;
        import com.example.demo.service.CityService;
        import com.example.demo.service.ExpertiseService;
        import com.fasterxml.jackson.core.JsonProcessingException;
        import lombok.RequiredArgsConstructor;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.context.request.async.DeferredResult;

        import java.util.List;


@RestController()
@RequestMapping("/Expertise")
@RequiredArgsConstructor
public class ExpertiseControlleer {

    private final ExpertiseService expertiseService;

    @PostMapping(value = "/expertise-catch")
    public @ResponseBody DeferredResult<ResponseEntity<?>> expertiseCatch() throws JsonProcessingException, InterruptedException {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        List<ExpertiseOutDto> Expertises = expertiseService.catchExpertise();

        result.setResult(ResponseEntity.status(HttpStatus.CREATED).body(Expertises));

        return result;
    }
}
