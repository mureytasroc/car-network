public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(final String textMessage) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        message.setContent(jsonObject.getString("message"));
        message.setSender(jsonObject.getString("sender"));
        message.setReceived(new Date());
        return message;
    }

}