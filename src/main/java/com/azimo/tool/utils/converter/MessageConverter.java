package com.azimo.tool.utils.converter;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.utils.Apps;
import com.azimo.tool.utils.ColorFormatter;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class MessageConverter {

    private ColorFormatter colorFormatter;
    private float averageOverall = 0;

    public MessageConverter(ColorFormatter colorFormatter) {
        this.colorFormatter = colorFormatter;
    }

    public SlackMessage slackMessageFromAppReview(ReportedReviewsCollection reviews, Apps app) {

        int allVersionsRatingSum = 0;

        int newestVersionCount = 0;
        int newestVersionRatingSum = 0;

        int count_4_1 = 0;
        int sum_4_1 = 0;

        for (ReportedReview review : reviews) {
            allVersionsRatingSum += review.getReportedRating();

            String reportedAppVersion = review.getReportedAppVersion();
            if (reportedAppVersion != null) {
                if (reportedAppVersion.startsWith("4.2")) {
                    newestVersionCount++;
                    newestVersionRatingSum += review.getReportedRating();
                } else if (reportedAppVersion.startsWith("4.1")) {
                    count_4_1++;
                    sum_4_1 += review.getReportedRating();
                }
            }

        }

        SlackMessage slackMessage = new SlackMessage();
        slackMessage.mrkdwn = true;
        slackMessage.text = app.getAppName();

        SlackMessage.Attachment messageAttachment = generateMessage("Overall", allVersionsRatingSum, reviews.size());
        SlackMessage.Attachment newestVersionAttachment = generateMessage("4.2.X", newestVersionRatingSum, newestVersionCount);
        SlackMessage.Attachment attachment_4_1 = generateMessage("4.1.X", sum_4_1, count_4_1);

        SlackMessage.Attachment[] attachmentsArray = new SlackMessage.Attachment[3];
        attachmentsArray[0] = messageAttachment;
        attachmentsArray[1] = attachment_4_1;
        attachmentsArray[2] = newestVersionAttachment;

        slackMessage.attachments = attachmentsArray;

        return slackMessage;
    }

    private SlackMessage.Attachment generateMessage(String message, int ratingSum, int count) {
        float averageRating = (float) ratingSum / count;
        int starRatingVal = Math.round(averageRating);

        String finalMessage = "";
        if ("Overall".equals(message)) {
            finalMessage += ":chart_with_upwards_trend:  ";
            averageOverall = averageRating;
        } else if (averageOverall > 0) {
            if (averageRating > averageOverall) {
                finalMessage += ":arrow_up: ";
            } else if (averageRating < averageOverall) {
                finalMessage += ":arrow_down: ";
            }
        }

        String s = String.valueOf(averageRating);
        String substring = s.length() > 4 ? s.substring(0, 5) : s;
        finalMessage = finalMessage + " " + message + ": " + substring
                + " ("
                + count
                + " reviews)";


        SlackMessage.Attachment messageAttachment = new SlackMessage.Attachment();
        messageAttachment.color = colorFormatter.getColorFromStarRating(starRatingVal);
        messageAttachment.text = finalMessage;

        return messageAttachment;
    }

}
