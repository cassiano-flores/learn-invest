package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Achievement;

public class UserAchievementFactory {

    public static Achievement get(){

        Achievement achievement = new Achievement();

        achievement.setId(SimpleFactory.getRandomLong());
        achievement.setName("Novo Elon");
        achievement.setDescription("Atingiu os pré-requisitos para se tornar o novo Musk");
        achievement.setIcon("image.png");

        return achievement;
    }
}
