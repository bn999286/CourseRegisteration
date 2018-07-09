package com.example.group12.courseregisteration;

import java.util.LinkedList;

public class ProfessorList {

    LinkedList<Professor> list;
    public ProfessorList(){
        list = new LinkedList<>();
    }

    //add professor
    public void add(Professor prof){
        list.add(prof);
    }

    //get professor at index
    public Professor get(int i){
        return list.get(i);
    }

    //clear list
    public void clear(){
        list.clear();
    }

    //return true if professor's name exists int the list
    public boolean Prof_list_exist(String prof_name){

        LinkedList<Integer> temp = new LinkedList<>();
        for(int i =0; i<list.size(); i++) {
            if (prof_name.equals(list.get(i).getProf_name()))
                temp.add(1);
            else
                temp.add(0);
        }

        if(temp.contains(1)){
            return true;
        }
        else{
            return false;
        }
    }


    //return the index of professor
    public int index(String prof_name){

        int index =0;
        for(int i =0; i<list.size(); i++){
            if (prof_name.equals(list.get(i).getProf_name())){
                index=i;
            }
        }
        return index;

    }


    //return 1 if professor's name exists int the list
    public int Prof_list_exist_int (String prof_name){

        LinkedList<Integer> temp = new LinkedList<>();

        for(int i =0; i<list.size(); i++){
            if (prof_name.equals(list.get(i).getProf_name()))
                temp.add(1);
            else
                temp.add(0);
        }

        if(temp.contains(1)){
            return 1;
        }
        else{
            return 0;
        }
    }

}
