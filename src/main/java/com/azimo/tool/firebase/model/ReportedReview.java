package com.azimo.tool.firebase.model;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class ReportedReview {

    private String reportedReviewId;
    private long reportedReviewTime;
    private int reportedRating;
    private String reportedAppVersion;

    public ReportedReview() {
    }

    public String getReportedReviewId() {
        return reportedReviewId;
    }

    public void setReportedReviewId(String reportedReviewId) {
        this.reportedReviewId = reportedReviewId;
    }

    public long getReportedReviewTime() {
        return reportedReviewTime;
    }

    public void setReportedReviewTime(long reportedReviewTime) {
        this.reportedReviewTime = reportedReviewTime;
    }

    @Override
    public String toString() {
        return "ReportedReview{" +
            "reportedReviewId='" + reportedReviewId + '\'' +
            ", reportedReviewTime=" + reportedReviewTime +
            '}';
    }

    public int getReportedRating() {
        return reportedRating;
    }

    public void setReportedRating(int reportedRating) {
        this.reportedRating = reportedRating;
    }

    public String getReportedAppVersion() {
        return reportedAppVersion;
    }

    public void setReportedAppVersion(String reportedAppVersion) {
        this.reportedAppVersion = reportedAppVersion;
    }
}
