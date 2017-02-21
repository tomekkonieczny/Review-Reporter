package com.azimo.tool.firebase;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.mapper.ReportedReviewMapper;
import com.azimo.tool.firebase.model.ReportedReviewsList;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.firebase.response.GetReportedReviewsResponse;

import java.io.IOException;

/**
 * Created by F1sherKK on 17/01/17.
 */
public class FirebaseServiceManager {

    private final FirebaseService firebaseService;
    private final ReportedReviewMapper reportedReviewMapper;

    public FirebaseServiceManager(FirebaseService firebaseService,
                                  ReportedReviewMapper reportedReviewMapper) {
        this.firebaseService = firebaseService;
        this.reportedReviewMapper = reportedReviewMapper;
    }

    public DefaultFirebaseResponse updateReportedReviews(String countryCode, ReportedReviewsCollection collection) {
        ReportedReviewsList reportedReviewsList = reportedReviewMapper.reportedReviewsCollectionToList(collection);
        return firebaseService.updateReportedReviews(countryCode, reportedReviewsList).toBlocking().first();
    }

    public ReportedReviewsCollection getReportedReviews(String packageName) throws IOException {
        String countryCode = packageName.substring(0, 2);
        GetReportedReviewsResponse response = firebaseService.getReportedReviews(countryCode).toBlocking().first();
        return reportedReviewMapper.getReportedReviewsResponseToCollection(response);
    }
}
