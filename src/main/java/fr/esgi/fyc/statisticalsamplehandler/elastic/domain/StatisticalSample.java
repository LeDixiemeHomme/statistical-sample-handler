package fr.esgi.fyc.statisticalsamplehandler.elastic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
@Data
@ApiModel("StatisticalSample")
public class StatisticalSample {

    @NonNull
    private final String measurementName;
    @NonNull
    private final Float value;
    @NonNull
    private final String unit;
    @NonNull
    private final LocalDateTime localDateTime;

    @JsonCreator
    public StatisticalSample(@NonNull String measurementName, @NonNull Float value, @NonNull String unit, @NonNull LocalDateTime localDateTime) {
        this.measurementName = measurementName;
        this.value = value;
        this.unit = unit;
        this.localDateTime = localDateTime;
    }
}
