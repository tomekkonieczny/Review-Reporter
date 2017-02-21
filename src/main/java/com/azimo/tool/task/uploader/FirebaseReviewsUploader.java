package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.task.interfaces.Uploader;
import com.azimo.tool.utils.Apps;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class FirebaseReviewsUploader implements Uploader<ReportedReviewsCollection, DefaultFirebaseResponse> {

    private FirebaseServiceManager firebaseServiceManager;

    public FirebaseReviewsUploader(FirebaseServiceManager firebaseServiceManager) {
        this.firebaseServiceManager = firebaseServiceManager;
    }

    @Override
    public DefaultFirebaseResponse upload(ReportedReviewsCollection reportedToSlackReviews, Apps app) {
        String countryCode = app.getPackageName().substring(0, 2);
        return firebaseServiceManager.updateReportedReviews(countryCode, reportedToSlackReviews);
    }
}
