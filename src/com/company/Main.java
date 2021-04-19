package com.company;

import java.time.LocalDate;
import java.util.*;

//Створіть наступну модель: є політична сила, яка може приймати до своїх лав громадян країни. Політична сила може проводити
// з’їзди своїх членів та обирати свої керівні органи. Повинна бути можливість подивитися, за кого голосував кожний учасник.
// або хто голосував за конкретну людину в керуючому органі.  Кожен активіст може пошукати детальну інформацію по різним критеріям
// серед всього складу однопартійців, крім того є можливість декларувати помісячно свої доходи та дивитися доходи однопартійців за
// любий період. Повинна бути можливість формувати списки учасників з’їздів, це роблять регіональні партійні структури. Таким чином
// регіональний орган обирає з своїх лав учасників з’їзді. Повинна бути можливість формування загальних списків учасників з’їздів з можливістю
// сортування за регіонами, доходами, та по алфавіту.

class Participant{
 String name;
 String region;
 Map<LocalDate, Integer> incomeReg= new HashMap<>();
    Map<String, String> vote = new HashMap<>();
 Participant(String name, String region){
     this.name=name;
     this.region=region;

 }


public void addIncome(LocalDate date, int income){
    incomeReg.put(date, income);
}


    public void addVote(String who, String forWhom) {
        vote.put(who, forWhom);
    }




public void show(){
     System.out.println("name:" + this.name + " region:" + this.region + "income:" + this.incomeReg);
}


}


class RegionalDep {
    String name;

    List<Participant> participantList = new ArrayList<>();


    RegionalDep(String name) {
        this.name = name;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public boolean addParticipant(Participant participant) {
        return participantList.add(participant);
    }


   List<Participant> searchParticipant(Predicate<Participant> condition) {
        List<Participant> result = new ArrayList<>();

        for (Participant temp : participantList) {
            if (condition.test(temp) == true) {
                result.add(temp);

            }
        }

        return result;


    }



    public void show(List<Participant> participantList) {
        for (Participant participant : participantList) {
            System.out.println("name: " + participant.name + "region: " + participant.region + "income:" + participant.incomeReg);
        }


    }


    class Political {
        String name;
        List<RegionalDep> listDepart = new ArrayList<>();
       List<Congress > congressList = new ArrayList<>();

        Political(String name) {
            this.name = name;
        }


        public boolean addRegionDepar(RegionalDep reg) {
            return listDepart.add(reg);

        }

boolean addToCongress(String name, LocalDate date, int kvota){
            return congressList.add(new Congress(name, date, kvota));
}





    }

    class Congress{

        String name;
        LocalDate date;
        int kvota;

        Congress(String name, LocalDate date, int kvota){
            this.name=name;
            this.date=date;
            this.kvota =kvota;

        }

        List<Participant> partList= new ArrayList<>();
         List<Participant> managerDep=new ArrayList<>();

        boolean addToCongress(Participant part){
            return partList.add(part);

        }
        boolean addToManagerDep (Participant part){
            return  managerDep.add(part);
        }



    }

}


    public class Main {
    public static void main(String[] args) {


    }
}
