package ir.deltasink.feagen.common.utils;

import lombok.val;

import java.text.MessageFormat;


/**
 * This class formats string using specific pattern with arguments.
 * <p>
 * Note: The only pattern that is supported is using empty placeholders. It dynamically detects them and replace them with input arguments.
 * </p>
 * This is the light version of log4j instead of importing log4j dependency.
 */
public class FormattedMessage {
    private final String messagePattern;
    private final Object[] args;
    private final MessageFormat messageFormat;

    /**
     * Instantiates a new Formatted message.
     *
     * @param messagePattern message pattern
     * @param args           arguments to be placed in pattern
     */
    public FormattedMessage(String messagePattern, Object... args){
        this.messagePattern = messagePattern;
        this.args = args;
        messageFormat = getMessageFormat();
    }

    /**
     * Parses the input message pattern and extract its placeholders.
     * @return  returns a MessageFormat
     */
    private MessageFormat getMessageFormat(){
//        if (messagePattern == null || messagePattern.isEmpty())
//            return new MessageFormat("");

        val sb = new StringBuilder();
        int placeholderIndex = 0;
        for (int i=0; i<messagePattern.length()-1; i++){
            // It's {}
            if (messagePattern.charAt(i) == '{' && messagePattern.charAt(i+1) == '}'){
                sb.append("{").append(placeholderIndex).append("}");
                i+=1;
                placeholderIndex++;
            }
            else if (messagePattern.charAt(i) == '{')
                sb.append("'{");
            else
                sb.append(messagePattern.charAt(i));
        }

        if (!messagePattern.endsWith("{}"))
            sb.append(messagePattern.charAt(messagePattern.length()-1));

        return new MessageFormat(sb.toString().replace("'", "''"));
    }

    /**
     * Formats input message with input arguments.
     * @return formatted message
     */
    public String getFormattedMessage(){
        return messageFormat.format(args);
    }
}
