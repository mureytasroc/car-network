public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(final Message message) throws EncodeException {
        return Json.createObjectBuilder()
                       .add("message", message.getContent())
                       .add("sender", message.getSender())
                       .add("received", "")
                       .build().toString();
    }

}