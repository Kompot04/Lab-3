package com.company;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.company.Participant.conditionByName;
import static java.util.stream.Collectors.*;

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
   static Comparator<Participant> conditionByName= (w,e)->w.getName().compareTo(e.getName());
   static Comparator<Participant> conditionByRegion= (w,e)->w.getRegion().compareTo(e.getRegion());
   static Comparator<Participant> conditionByIncome= (w,e)->w.totalIncome()- (e.totalIncome());

     Participant(String name, String region) {
         this.name = name;
         this.region = region;
     }


    public String getName() {
        return name;
    }
    public String getRegion() {
        return region;
    }


public void addIncome(LocalDate date, int income){
     if(incomeReg.containsKey(date)){
         int previousValue = incomeReg.get(date).intValue();
         incomeReg.put(date, previousValue+income);
     }
     else {
         incomeReg.put(date, income);
     }

}

public int incomeReport(LocalDate date1, LocalDate date2){
     int result=0;
     for(LocalDate temp:incomeReg.keySet()){
         if(temp.isAfter(date1)&& temp.isBefore(date2)||(temp.isAfter(date2)&& temp.isBefore(date1))){
             int tempIncome = incomeReg.get(temp);
             result+=tempIncome;
         }
     }
     System.out.println(result);
     return result;

}


    public int totalIncome(){
        int result=0;

        for(LocalDate temp:incomeReg.keySet()){

            int tempIncome = incomeReg.get(temp);
            result+=tempIncome;

        }
        System.out.println("Загальний доход: " + result);


        return result;
    }


    public void addVote(String who, String forWhom) {
     if(vote.containsKey(who)){
         System.out.println("This participant already voted!");
     }
        vote.put(who, forWhom);
    }






 public void show(){
     System.out.println("Name: "+this.name+ " Region: "+ this.region);
 }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Participant participant = (Participant) o;

        if (getName() != participant.getName()) {
            return false;
        }
        if (getRegion() != participant.getRegion()) {
            return false;
        }
        return true;

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
if(participantList.contains(participant)){
    System.out.println("This participant already exists!");
   }
       return participantList.add(participant);

    }
public boolean deleteParticipant(Participant participant){
        return participantList.remove(participant);

}
    List<Participant> searchParticipant(Predicate<Participant> condition) {
        List<Participant> result = new ArrayList<>();

        for (Participant temp : participantList) {
            if (condition.test(temp) == true) {
                result.add(temp);

            }
        }
        System.out.println(result);

        return result;

    }

public void sort(Comparator<Participant>  condition){
        participantList.sort(condition);



}


    public void show(){
        System.out.println("Name of Regional Department: " + this.name);
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
            if(listDepart.contains(reg)){
                System.out.println("Already exists!");
            }
            return listDepart.add(reg);

        }

boolean addToCongress(String nameCon, LocalDate date, int kvota){
            if(congressList.contains(new Congress(nameCon, date, kvota))){
                System.out.println("This Congress already exists!");
    }
            return congressList.add(new Congress(nameCon, date, kvota));
}

        public void show(){
            System.out.println("Name of political force: " + this.name);
        }

    }

    class Congress{

        String nameCon;
        LocalDate date;
        int kvota;

        Congress(String nameCon, LocalDate date, int kvota){
            this.nameCon=nameCon;
            this.date=date;
            this.kvota =kvota;

        }

        List<Participant> partList= new ArrayList<>();
         List<Participant> managerDep=new ArrayList<>();

        boolean addToCongress(Participant part){
            if(partList.contains(part)){
                System.out.println("This Participant already exists!");
            }
            return partList.add(part);

        }
        boolean addToManagerDep (Participant part){
            if(managerDep.contains(part)){
                System.out.println("This Manager of Department already exists!");
            }
            return  managerDep.add(part);
        }


        public void show(){
            System.out.println("Name of Congress: " + this.nameCon + "Date: " +this.date+ "Kvota: "+this.kvota);
        }
    }






    public class Main {
    public static void main(String[] args) {

        Participant participant1= new Participant("Sidorenko Ivan","Mykolaiv");
        Participant participant2= new Participant("Kravchenko Marina","Odessa");
        Participant participant3= new Participant(" Ivanenko Maria","Mykolaiv");
        Participant participant4= new Participant("Kvitko Michael","Odessa");

        RegionalDep rd1 = new RegionalDep("Odessa");
        RegionalDep rd2 = new RegionalDep("Mykolaiv");
        rd1.addParticipant(participant2);
        rd1.addParticipant(participant4);
        rd2.addParticipant(participant1);
        rd2.addParticipant(participant3);


        participant1.addIncome(LocalDate.of(2021,3,15), 16000);
        participant1.addIncome(LocalDate.of(2021,4,15), 12001);
        participant1.addIncome(LocalDate.of(2021,5,15), 12000);
        System.out.println("------------------------------------------------");
        System.out.println("Total Income for Sidorenko Ivan:");
        participant1.totalIncome();
        System.out.println("------------------------------------------------");

        System.out.println("Income 10.04.2021-20.05.2021:");
        participant1.incomeReport(LocalDate.of(2021,5,20),LocalDate.of(2021,4,10));

        participant1.addVote("Sidorenko Ivan","Kravchenko Marina");

        participant2.addIncome(LocalDate.of(2021,3,16), 15000);
        participant2.addIncome(LocalDate.of(2021,4,16), 15000);
        System.out.println("------------------------------------------------");
        System.out.println("Total Income for Kravchenko Marina:");
        participant1.totalIncome();
        System.out.println("------------------------------------------------");

        participant1.addIncome(LocalDate.of(2021,3,10), 12000);
        participant1.addIncome(LocalDate.of(2021,4,10), 15000);
        System.out.println("------------------------------------------------");
        System.out.println("Total Income for Ivanenko Maria:");
        participant1.totalIncome();
        System.out.println("------------------------------------------------");

        participant1.addIncome(LocalDate.of(2021,3,9), 10000);
        participant1.addIncome(LocalDate.of(2021,4,9), 10000);
        System.out.println("------------------------------------------------");
        System.out.println("Total Income for Kvitko Michael:");
        participant1.totalIncome();
        System.out.println("------------------------------------------------");

        Political political = new Political("Green Part");
        political.addRegionDepar(rd1);



        rd2.searchParticipant(w->w.name.equals("Ivanenko Maria"));
        rd2.sort(conditionByName);







    }
}
