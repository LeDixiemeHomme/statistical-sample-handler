package fr.esgi.fyc.statisticalsamplehandler.elastic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@ApiModel("StatisticalSample")
public class StatisticalSample {

    @NonNull
    private final String cdCar;
    @JsonCreator
    public StatisticalSample(@NonNull String cdCar) {
        this.cdCar = cdCar;
    }
}
