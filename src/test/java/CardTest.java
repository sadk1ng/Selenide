import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.beans.PropertyEditor;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class CardTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
    }

    @Test
    void shouldValidDataTest() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='notification']").shouldHave(Condition.text("Встреча успешно забронирована на " + requireDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldInvalidCityTest1() {
        $("[placeholder='Город']").setValue("");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInvalidCityTest2() {
        $("[placeholder='Город']").setValue("Moscow");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldInvalidDateTest1() {
        $("[placeholder='Город']").setValue("Воронеж");
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldInvalidDateTest2() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldDateMoreThan3DaysTest() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='notification']").shouldHave(Condition.text("Встреча успешно забронирована на " + requireDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldInvalidNameSurnameTest1() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInvalidNameSurnameTest2() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Ivanov Ivan");
        $("[name='phone']").setValue("+79207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldInvalidPhoneTest1() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("89207001020");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldInvalidPhoneTest2() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+7920700102");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldInvalidPhoneTest3() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+792070010201");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEmptyCheckbox() {
        $("[placeholder='Город']").setValue("Воронеж");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Иванов-Петров Иван");
        $("[name='phone']").setValue("+79207001020");
        $(".button__text").click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}