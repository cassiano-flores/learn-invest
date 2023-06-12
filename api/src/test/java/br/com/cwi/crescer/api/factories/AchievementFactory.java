package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Achievement;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class AchievementFactory {

    public static Achievement get() {

        Achievement achievement = new Achievement();

        achievement.setId(getRandomLong());
        achievement.setName("Test Achievement");
        achievement.setDescription("Test");
        achievement.setIcon("icon.png");

        return achievement;
    }
}
