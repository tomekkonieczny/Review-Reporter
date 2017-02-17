package com.azimo.tool.utils.converter;

import com.azimo.tool.publisher.model.AppReview;
import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.utils.ColorFormatter;
import com.google.api.services.androidpublisher.model.UserComment;

/**
 * Created by F1sherKK on 25/01/17.
 */
public class MessageConverter {

    private static final String GOOGLE_ICON_URL = "http://upthetree.com/wp-content/uploads/2013/01/GooglePlay-Icon.png";

    private ColorFormatter colorFormatter;

    public MessageConverter(ColorFormatter colorFormatter) {
        this.colorFormatter = colorFormatter;
    }

    public SlackMessage slackMessageFromAppReview(AppReview review) {
        String title = "";
        String message = "";
        String fullMessage;
        int starRatingVal = -1;

        UserComment comment = review.getNewestComment();
        if (comment != null) {
            starRatingVal = comment.getStarRating();

            final String newLineCharacter = "\t";
            fullMessage = comment.getText();
            if (fullMessage.contains(newLineCharacter)) {
                int titleIndex = fullMessage.indexOf(newLineCharacter);
                title = fullMessage.substring(0, titleIndex);
                message = fullMessage.substring(titleIndex, fullMessage.length()).replaceAll(newLineCharacter, "");
            } else {
                title = "";
                message = fullMessage;
            }
        }

        String mainText = "Reviews report from Google Play Store";
        String ratingAttachmentText = "Situation: %s";

        SlackMessage slackMessage = new SlackMessage();
        slackMessage.mrkdwn = true;
        slackMessage.text = mainText;

        SlackMessage.Attachment messageAttachment = new SlackMessage.Attachment();
        messageAttachment.color = colorFormatter.getColorFromStarRating(starRatingVal);
        messageAttachment.thumb_url = GOOGLE_ICON_URL;
        if (!title.equals("")) {
            messageAttachment.title = title;
        }
        messageAttachment.text = message;

        SlackMessage.Attachment ratingAttachment = new SlackMessage.Attachment();
        ratingAttachment.color = ColorFormatter.RATING_SECTION;
        ratingAttachment.text = String.format(ratingAttachmentText, addStars(starRatingVal));

        SlackMessage.Attachment[] attachmentsArray = new SlackMessage.Attachment[3];
        attachmentsArray[0] = messageAttachment;
        attachmentsArray[1] = ratingAttachment;

        slackMessage.attachments = attachmentsArray;

        return slackMessage;
    }

    private String addStars(int starRatingVal) {
        final int maxStars = 5;
        final String slack_star_emoji = ":star:";
        final String slack_small_square = ":white_small_square:";
        String starsString = "";
        for (int i = 0; i < starRatingVal; i++) {
            starsString += slack_star_emoji;
        }
        for (int j = 0; j < maxStars - starRatingVal; j++) {
            starsString += slack_small_square;
        }
        return starsString;
    }

}
