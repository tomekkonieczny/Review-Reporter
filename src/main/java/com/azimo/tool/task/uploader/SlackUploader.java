package com.azimo.tool.task.uploader;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.slack.SlackServiceManager;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.slack.response.SlackPostMessageResponse;
import com.azimo.tool.task.interfaces.Uploader;
import com.azimo.tool.utils.converter.MessageConverter;
import com.azimo.tool.utils.converter.ReviewConverter;

/**
 * Created by F1sherKK on 27/01/17.
 */
public class SlackUploader implements Uploader<ReportedReviewsCollection, Boolean> {

    private MessageConverter messageConverter;
    private ReviewConverter reviewConverter;
    private SlackServiceManager slackServiceManager;

    public SlackUploader(MessageConverter messageConverter,
                         ReviewConverter reviewConverter,
                         SlackServiceManager slackServiceManager) {
        this.messageConverter = messageConverter;
        this.reviewConverter = reviewConverter;
        this.slackServiceManager = slackServiceManager;
    }

    @Override
    public Boolean upload(ReportedReviewsCollection unreportedReviews) {
        SlackMessage slackMessage = messageConverter.slackMessageFromAppReview(unreportedReviews);
        SlackPostMessageResponse response = slackServiceManager.sendMessage(slackMessage);

        return response.wasSuccess();
    }

    public ReportedReviewsCollection convert(ReviewCollection unreportedReviews) {
        ReportedReviewsCollection reportedReviewsCollection = new ReportedReviewsCollection();
        for (AppReview unreportedReview : unreportedReviews.sortAscendingByCreatedTime()) {
            reportedReviewsCollection.add(reviewConverter.reportedReviewFromAppReview(unreportedReview));
        }
        return reportedReviewsCollection;
    }
}
