package com.azimo.tool.utils.converter;

import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.UserComment;

/**
 * Created by F1sherKK on 26/01/17.
 */
public class ReviewConverter {

    public ReportedReview reportedReviewFromAppReview(AppReview review) {
        ReportedReview reportedReview = new ReportedReview();
        reportedReview.setReportedReviewId(review.getReviewId());
        UserComment newestComment = review.getNewestComment();
        if (newestComment != null) {
            reportedReview.setReportedReviewTime(newestComment.getLastModified().getSeconds() * 1000);
            reportedReview.setReportedRating(newestComment.getStarRating());
            reportedReview.setReportedAppVersion(newestComment.getAppVersionName());
        }
        return reportedReview;
    }
}
