package com.azimo.tool.firebase;

import com.azimo.tool.firebase.model.ReportedReviewsList;
import com.azimo.tool.firebase.response.DefaultFirebaseResponse;
import com.azimo.tool.firebase.response.GetReportedReviewsResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by F1sherKK on 17/01/17.
 */
public interface FirebaseService {

    @PATCH("reportedReviewCollection_{id}.json")
    Observable<DefaultFirebaseResponse> updateReportedReviews(
            @Path("id") String countryCode,
            @Body ReportedReviewsList reportedReviewsList
    );

    @GET("reportedReviewCollection_{id}.json")
    Observable<GetReportedReviewsResponse> getReportedReviews(@Path("id") String countryCode);
}
