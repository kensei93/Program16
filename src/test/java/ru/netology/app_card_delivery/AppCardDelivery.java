package ru.netology.app_card_delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;


class AppCardDelivery {
    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
    String date;
    String month;
    String day;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 100);
        date =  new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());
        month = monthNames[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
        day = Integer.toString(calendar.get(Calendar.DATE));
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Должен забронировать встречу при вводе валидных данных")
    void shouldRegisterCardDelivery() {
        $("span[data-test-id='city'] input").setValue("Пе");
        $$("div.popup__content div").find(exactText("Пермь")).click();
        $("span[data-test-id='date'] button").click();

        while (!$("div.calendar__name").getText().equals(month)) {
            $$("div.calendar__arrow.calendar__arrow_direction_right").get(1).click();
        }

        $$("table.calendar__layout td").find(text(day)).click();
        $("span[data-test-id='name'] input").setValue("Пупкин Василий");
        $("span[data-test-id='phone'] input").setValue("+79000000000");
        $("label[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("div.notification__content").waitUntil(text("Встреча успешно забронирована на " + date),
                15000);
    }

}
