package com.azimo.tool.publisher.collection;

import com.azimo.tool.publisher.model.AppReview;

import java.util.ArrayList;

/**
 * Created by F1sherKK on 10/01/17.
 */
public class ReviewCollection extends ArrayList<AppReview> {

    public ReviewCollection sortAscendingByCreatedTime() {
        ReviewCollection filteredReviewCollection = new ReviewCollection();
        filteredReviewCollection.addAll(this);

        filteredReviewCollection.sort((o1, o2) -> {
            long o1CreationTime = o1.getNewestComment().getLastModified().getSeconds();
            long o2CreationTime = o2.getNewestComment().getLastModified().getSeconds();
            if (o1CreationTime > o2CreationTime) {
                return 1;
            } else {
                return -1;
            }
        });
        return filteredReviewCollection;
    }
}
