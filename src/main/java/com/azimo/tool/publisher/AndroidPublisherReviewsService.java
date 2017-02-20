package com.azimo.tool.publisher;

import com.azimo.tool.publisher.collection.ReviewCollection;
import com.azimo.tool.publisher.mapper.ReviewMapper;
import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.utils.Apps;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.ReviewsListResponse;

import java.io.IOException;
import java.util.List;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class AndroidPublisherReviewsService {

    public static final int MAX_REVIEWS = 500;

    private AndroidPublisher.Reviews reviews;
    private ReviewMapper reviewMapper;

    public AndroidPublisherReviewsService(AndroidPublisher.Reviews reviews,
                                          ReviewMapper reviewMapper) {
        this.reviews = reviews;
        this.reviewMapper = reviewMapper;
    }

    public ReviewCollection getReviews(int maxResults, Apps app) throws IOException {
        ReviewCollection reviewCollection = new ReviewCollection();

        ReviewsListResponse response = reviews
            .list(app.getPackageName())
            .setMaxResults((long) maxResults)
            .execute();

        List<AppReview> appReviewList = reviewMapper.toAppReviewList(response.getReviews());
        reviewCollection.addAll(appReviewList);

        return reviewCollection;
    }
}
