package com.azimo.tool.task.provider;

import com.azimo.tool.firebase.FirebaseServiceManager;
import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.publisher.AndroidPublisherReviewsService;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.task.interfaces.Provider;
import com.azimo.tool.utils.Apps;


/**
 * Created by F1sherKK on 27/01/17.
 */
public class UnreportedReviewsProvider implements Provider<ReviewCollection> {

    private AndroidPublisherReviewsService publisherReviewsService;
    private FirebaseServiceManager firebaseIssueServiceManager;

    public UnreportedReviewsProvider(AndroidPublisherReviewsService publisherReviewsService,
                                     FirebaseServiceManager firebaseIssueServiceManager) {
        this.publisherReviewsService = publisherReviewsService;
        this.firebaseIssueServiceManager = firebaseIssueServiceManager;
    }

    @Override
    public ReviewCollection fetch(Apps app) throws Exception {
        ReviewCollection reviews = publisherReviewsService.getReviews(app);
        ReportedReviewsCollection firebaseReportedReviews = firebaseIssueServiceManager.getReportedReviews(app.getPackageName());

        ReviewCollection result = new ReviewCollection();
        for (AppReview review : reviews) {
            if (!firebaseReportedReviews.containsReviewId(review.getReviewId())) {
                result.add(review);
            }
        }

        return result;
    }
}
