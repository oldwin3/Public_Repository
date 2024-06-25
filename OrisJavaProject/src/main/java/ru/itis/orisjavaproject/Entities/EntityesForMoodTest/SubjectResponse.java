package ru.itis.orisjavaproject.Entities.EntityesForMoodTest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public
class SubjectResponse {
    private String key;
    private String name;
    private String subject_type;
    private Integer work_count;
    private List<BookForParsing> works;

}