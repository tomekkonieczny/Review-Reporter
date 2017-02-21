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

    public static final long MAX_REVIEWS = 500;

    private AndroidPublisher.Reviews reviews;
    private ReviewMapper reviewMapper;

    public AndroidPublisherReviewsService(AndroidPublisher.Reviews reviews,
                                          ReviewMapper reviewMapper) {
        this.reviews = reviews;
        this.reviewMapper = reviewMapper;
    }

    public ReviewCollection getReviews(Apps app) throws IOException {
        ReviewCollection reviewCollection = new ReviewCollection();

        ReviewsListResponse response = execute(null, app.getPackageName());
        List<AppReview> appReviewList = reviewMapper.toAppReviewList(response.getReviews());
        if (response.getTokenPagination() != null) {
            ReviewsListResponse response2 = execute(response.getTokenPagination().getNextPageToken(), app.getPackageName());
            appReviewList.addAll(reviewMapper.toAppReviewList(response.getReviews()));

            if (response2.getTokenPagination() != null) {
                ReviewsListResponse response3 = execute(response2.getTokenPagination().getNextPageToken(), app.getPackageName());
                appReviewList.addAll(reviewMapper.toAppReviewList(response2.getReviews()));

                if (response3.getTokenPagination() != null) {
                    ReviewsListResponse response4 = execute(response3.getTokenPagination().getNextPageToken(), app.getPackageName());
                    appReviewList.addAll(reviewMapper.toAppReviewList(response4.getReviews()));
                }
            }
        }
        
        reviewCollection.addAll(appReviewList);

        return reviewCollection;
    }

    private ReviewsListResponse execute(String nextPageToken, String packageName) throws IOException {
        AndroidPublisher.Reviews.List responseList = reviews
                .list(packageName)
                .setMaxResults(MAX_REVIEWS);

        if (nextPageToken != null) {
            responseList.setToken(nextPageToken);
        }

        ReviewsListResponse response = responseList.execute();

        return response;
    }
}
