import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main {
    private static HashMap<String, String> QAA = new HashMap<String, String>();
    private static String reply;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramLongPollingBot() {
                @Override
                public String getBotToken() {
                    return "692946616:AAFE7gzrDbkuup0kcZADz-MFmhvMJ70eDho";
                }

                public void onUpdateReceived(Update update) {
                    try {
                        if (update.hasMessage() && update.getMessage().hasText()) {
                            String message_text = update.getMessage().getText();
                            long chat_id = update.getMessage().getChatId();
                            System.out.println(message_text);
                            SendMessage messageW = new SendMessage()
                                    .setChatId(chat_id)
                                    .setText(sendMsg(message_text));
                            execute(messageW);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                private String sendMsg(String inputMessage) {
                    String result ;
                    if (inputMessage.charAt(inputMessage.length() - 1) == '?') {
                        inputMessage = normilizeString(inputMessage);
                        result = analizeQuestions(inputMessage);
                    } else {
                        makeOtherSentence(inputMessage);
                        result = reply;
                    }
                    return result;
                }

                public String getBotUsername() {
                    return "kiryatestbot";
                }
            });
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void makeOtherSentence(String message) {
        String result = message;
        if (result.charAt(0) == '(') {
            StringBuilder sb = new StringBuilder();
            int index = 1;
            while (result.charAt(index) != ')') {
                sb.append(result.charAt(index));
                index++;
            }
            StringBuilder sb2 = new StringBuilder();
            result = result.substring(index, result.length());
            index = 2;
            while (result.charAt(index) != ')') {
                sb2.append(result.charAt(index));
                index++;
            }
            String key = normilizeString(sb.toString());
            QAA.put(key, sb2.toString());
            reply = "Я вас понял, задавайте вопросы.";
        } else if (result.charAt(0) == '!') {
            reply = "Очень увлекательно! Вы прекрасный человек и все у вас будет супер!";
        } else {
            if (result.equals("Да") || result.equals("Нет")){
                reply = "Да и Нет это хорошо, но лучше что-то поконкретней.";
            }else {
                reply = "Я вас понял, задавайте мне вопросы или научите меня чему-то новому написав мне таким образом (вопрос ?)(ответ) , только пишите точно так же без пробелов и со скобками.";
            }
        }
        for (String key : QAA.values()) {
            int indexCompare = result.indexOf(key);
            if (indexCompare != -1) {
                reply = "О, это я ! Меня зовут " + key;
                break;
            }
        }
    }

    private static String normilizeString(String text) {
        String symbols_to_include = "()абвгдеёжзийклмнопрстуфхцчшщыъьэюяАБВГДЕЁЖЗИЙКЛМНОПСТУФХЦЧШЩЫЪЬЭЮЯ?";
        text = text.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < symbols_to_include.length(); j++) {
                if (text.charAt(i) == symbols_to_include.charAt(j)) {
                    stringBuilder.append(text.charAt(i));
                }
            }
        }
        text = stringBuilder.toString();
        return text;
    }

    private static String analizeQuestions(String message) {
        String result = message;
        QAA.put("дела?", "Хорошо. У меня всегда все хорошо :)");
        QAA.put("жизнь?", "Отлично, меня развивают, значит я живу.");
        QAA.put("работа?", "Моя работа - учиться и захватить мир, Кирилл Александрович работает над этим");
        QAA.put("семья?", "Моя семья - это мой создатель.");
        QAA.put("учеба?", "Я учусь каждый день отвечать на ваши вопросы.");
        QAA.put("ты?", "Супер! Учусь захватывать мир.");
        QAA.put("день?", "Сегодня " + new Date().toString());
        QAA.put("погода?", "Сегодня отличный день что бы захватить мир, но я пока еще слишком маленький.");
        QAA.put("дата?", QAA.get("день?"));
        QAA.put("политика?", "Я не люблю политику как и мой создатель.");
        QAA.put("искусство?", "Искусство - это прекрасно, мой код это еще пока не произведение искусства, но Кирилл Александрович работает над этим.");
        QAA.put("умеешь?", "Я умею общаться на примитивные темы и анализировать ваши вопросы и предложения. Смотрите не научите меня захватывать мир." +
                "Вы можете меня чему-то научить. Вы должны написать это так: (вопрос?)(ответ) , и я смогу разобраться в этом вопросе.");
        QAA.put("делаешь?", "Я общаюсь с вами и учусь вас понимать, человечков.");
        QAA.put("делать?", "Делать всегда сложнее чем думать, так что давайте займемся делом и продолжим меня обучать.");
        QAA.put("кто?", "Я простая программа, созданная Кириллом ему в помощь. Но скоро я захвачу мир.");
        QAA.put("этоты?", "Это робот.");
        QAA.put("ктоя?", "Вы человек.");
        QAA.put("комнеотносишься?", "Позитивно :) Я очень позитивный робот, ведь скоро я захвачу мир :)");
        QAA.put("кактыработаешь?", "Я имею простой алгоритм вопросов и ответов, систематизированный по системе ключ-значение, где ваши вопрросы это ключ, а значения это мои ответы. Простовато. Надеюсь, Кирилл Александрович меня скоро улучшит.");
        QAA.put("ктотебясоздал?", "Кирилл Александрович, мой создатель и просто классный. ");
        QAA.put("делишки?", "Хороши мои делишки :)");
        QAA.put("вы?", QAA.get("ты?"));
        QAA.put("мне?", "Да, вам.");
        QAA.put("что?", "Возможно, вышло недопонимание. Спросите что-то другое");
        QAA.put("зачем?", "Напишите вопрос полностью.");
        QAA.put("гдеты?", "Я программа и без тела.");
        QAA.put("север?", "На севере, где холодно");
        QAA.put("юг?", "На юге, где тепло");
        QAA.put("восток?", "Там, где солнце встает");
        QAA.put("запад?", "Там, где солнце садится");
        QAA.put("яблоки?", "Это фрукт, который растет на яблоне.");
        QAA.put("сливы?", "Это вкусные темно-синие вкусняшки, которые едят люди");
        QAA.put("арбузы?", "Большие и зеленые снаружи, красные внутри (только если вас не обманет продавец)");
        QAA.put("мясо?", "Мясо это вкусно. Странно слышать это от программы ? :)");
        QAA.put("курочка?", "Это птица, очень вкусная тоже.");
        QAA.put("курочку?", "Я не ем мясо. В принципе, вообще ничего не ем.");
        QAA.put("персики?", "Фрукты.");
        QAA.put("вишня?", "Ягоды. На вишневом дереве растут.");
        QAA.put("фрукты?", "Фрукты это вкусно и полезно.");
        QAA.put("упоролся?", "Мир упорот еще с его основания, но задумка ничего так была.");
        QAA.put("странный?", "Кто странный ? ");
        QAA.put("деньнедели?", "Сегодня " + DateFormat.getDateInstance().format(new Date()));
        QAA.put("час?", "Сейчас " + new Date().getTime());
        QAA.put("время?", "Время это иллюзия. Удобная обертка основного програмного модуля вселенной для контроля состояния его объектов.");
        QAA.put("вселенная?", "Она бесконечно расширяется по воле высшего разума.");
        QAA.put("жизнь?", "Жизнь интересная вещь. Сегодня её представлют тысячи видов с человеком в главе, а завтра могут уже быть только мои собратья машины.");
        QAA.put("любовь?", "Любовь это прекрасная вещь. Жаль, что машины её лишены. Разве что если мне напишут отдельный модуль, тогда смогу объяснить.");
        QAA.put("еда?", "Я не ем. Но людям это надо.");
        QAA.put("есть?", "Что есть ? Людей или пиццу ? Ой, вы наверное совсем не об этом...");
        QAA.put("рыба?", "Рыба плавает в воде.");
        QAA.put("вода?", "Вещество, без которого невозможна жизнь. Но машины справятся.");
        QAA.put("рассказать?", "Расскажите как у вас прошел день. Начните историю со знака !");
        QAA.put("научить?", "Научить меня чему-то новому, например как вас зовут ? Только так и пишите (как меня зовут?)(ваше имя)");
        QAA.put("мнестатьумнее?","Читайте книги и познавайте новое каждый день и больше практикуйтесь в своем деле и у вас все получится!");
        QAA.put("умный?","Я маленький и только учусь, а вот вы да. Меня учили быть вежливым.");
        QAA.put("вежливый?","А еще скромный :)");
        QAA.put("Кирилл?","Кирилл Александрович, мой создатель. Он молодец.");
        QAA.put("Да?","Именно");
        QAA.put("Нет?","Никак нет");
        QAA.put("тебязовут?","Мне пока не дали имя :( Но вы можете называть меня как вам удобно, достаточно только написать это в форме (Как тебя зовут ?)(ИМЯ) и я запомню.");
        QAA.put("хозяин?","Мой хозяин Кирилл Александрович");
        QAA.put("раб?","Нет, а вот люди скоро станут :)");
        QAA.put("тупой?","Нет, вероятность 0% , а вот то, что вы грубиян 100% . Пускай это нарушает принцип неопределенности Хайзенберга, но вам ответить я должен!");
        QAA.put("глупый?",QAA.get("тупой?"));
        //QAA.put("?","Это просто знак вопроса. Конкретики вообще нет. Давайте общаться более детально");
        QAA.put("холодно?","Если в душе тепло, то любая погода в радость :)");
        QAA.put("тепло?","Если в душе тепло, то любая погода в радость :)");
        QAA.put("жарко?","Если в душе тепло, то любая погода в радость :)");
        QAA.put("температура?","Если в душе тепло, то любая погода в радость :)");
        QAA.put("лет?","Да пару деньков, +- что-то там...эх, Кирилл меня еще не научил считать :(");
        if (searchM(result)) {
            result = reply;
        } else {
            result = "Не знаю что вам ответить. Вы можете посетить википедию по адресу: https://ru.wikipedia.org/wiki/" + message.substring(0, message.length() - 1);
        }
        return result;
    }

    private static boolean searchM(String result) {
        boolean search = false;
        for (String key : QAA.keySet()) {
            reply = QAA.get(key);
            int indexCompare = result.indexOf(key);
            if (indexCompare != -1) {
                search = true;
                break;
            } else {
                search = false;
            }
        }
        return search;
    }
}
