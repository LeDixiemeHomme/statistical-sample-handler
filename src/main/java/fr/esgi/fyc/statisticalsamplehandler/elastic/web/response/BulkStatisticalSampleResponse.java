package fr.esgi.fyc.statisticalsamplehandler.elastic.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BulkStatisticalSampleResponse {
    private final String buildFailureMessage;
    private final long ingestTookInMillis;
    private final boolean hasFailure;

    public BulkStatisticalSampleResponse(String buildFailureMessage, long ingestTookInMillis, boolean hasFailure) {
        this.buildFailureMessage = buildFailureMessage;
        this.ingestTookInMillis = ingestTookInMillis;
        this.hasFailure = hasFailure;
    }
}
