import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;


public abstract class TelegramLongPollingBot extends DefaultAbsSender implements LongPollingBot {
    public TelegramLongPollingBot() {
        this(ApiContext.getInstance(DefaultBotOptions.class));
    }

    public TelegramLongPollingBot(DefaultBotOptions options) {
        super(options);
    }


    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(this);
    }

    public void onClosing() {
        exe.shutdown();
    }
}
