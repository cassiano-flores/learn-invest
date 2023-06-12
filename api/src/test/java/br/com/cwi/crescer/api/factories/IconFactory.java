package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Icon;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class IconFactory {

    public static Icon get() {
        Icon icon = new Icon();

        icon.setId(getRandomLong());
        icon.setName("Test Icon");
        icon.setPrice(getRandomLong());

        return icon;
    }
}
