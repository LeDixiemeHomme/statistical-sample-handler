package fr.esgi.fyc.statisticalsamplehandler.elastic.customException;


import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.StatisticalSample;

public class MissingElasticResponseException extends Exception {
    public MissingElasticResponseException(StatisticalSample sample) {
        super("No elastic response were provided for this sample : " + sample);
    }
}
