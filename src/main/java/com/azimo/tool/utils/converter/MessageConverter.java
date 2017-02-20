package com.azimo.tool.utils.converter;

import com.azimo.tool.firebase.collection.ReportedReviewsCollection;
import com.azimo.tool.firebase.model.ReportedReview;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.utils.ColorFormatter;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class MessageConverter {

    private ColorFormatter colorFormatter;

    public MessageConverter(ColorFormatter colorFormatter) {
        this.colorFormatter = colorFormatter;
    }

    public SlackMessage slackMessageFromAppReview(ReportedReviewsCollection reviews) {

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

        String mainText = "Reviews report from Google Play Store";
        String ratingAttachmentText = "Situation: %s";

        SlackMessage slackMessage = new SlackMessage();
        slackMessage.mrkdwn = true;
        slackMessage.text = mainText;

        SlackMessage.Attachment messageAttachment = generateMessage("Overall", allVersionsRatingSum, reviews.size());
        SlackMessage.Attachment newestVersionAttachment = generateMessage("4.2.X", newestVersionRatingSum, newestVersionCount);
        SlackMessage.Attachment attachment_4_1 = generateMessage("4.1.X", sum_4_1, count_4_1);

        SlackMessage.Attachment ratingAttachment = new SlackMessage.Attachment();
        ratingAttachment.color = ColorFormatter.RATING_SECTION;
        ratingAttachment.text = String.format(ratingAttachmentText, addStars(allVersionsRatingSum, reviews.size()));

        SlackMessage.Attachment[] attachmentsArray = new SlackMessage.Attachment[4];
        attachmentsArray[0] = messageAttachment;
        attachmentsArray[1] = attachment_4_1;
        attachmentsArray[2] = newestVersionAttachment;
        attachmentsArray[3] = ratingAttachment;

        slackMessage.attachments = attachmentsArray;

        return slackMessage;
    }

    private SlackMessage.Attachment generateMessage(String message, int ratingSum, int count) {
        float averageRating = (float) ratingSum / count;
        int starRatingVal = Math.round(averageRating);
        String s = String.valueOf(averageRating);
        String substring = s.length() > 4 ? s.substring(0, 5) : s;
        message = message + ": " + substring
                + " ("
                + count
                + " reviews)";

        SlackMessage.Attachment messageAttachment = new SlackMessage.Attachment();
        messageAttachment.color = colorFormatter.getColorFromStarRating(starRatingVal);
        messageAttachment.text = message;

        return messageAttachment;
    }

    private String addStars(int sum, int count) {
        float averageRating = (float) sum / count;
        int starRatingVal = Math.round(averageRating);
        final String slack_star_emoji = ":star:";
        String starsString = "";
        for (int i = 0; i < starRatingVal; i++) {
            starsString += slack_star_emoji;
        }

        return starsString;
    }

}
