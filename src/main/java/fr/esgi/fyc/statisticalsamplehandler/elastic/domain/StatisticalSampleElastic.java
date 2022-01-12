package fr.esgi.fyc.statisticalsamplehandler.elastic.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@ApiModel("StatisticalSampleElastic")
public class StatisticalSampleElastic {

    private final String nomMesure;
    private final Float valeur;
    private final String unite;
    private final String dateFormatElastic;

    @JsonCreator
    public StatisticalSampleElastic(StatisticalSample statisticalSample) {
        this.nomMesure = statisticalSample.getMeasurementName();
        this.valeur = statisticalSample.getValue();
        this.unite = statisticalSample.getUnit();
        this.dateFormatElastic = statisticalSample.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
